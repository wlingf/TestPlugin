package cc.jianke.pluginmodule

import android.content.Context
import android.content.Intent
import dalvik.system.DexClassLoader
import android.content.pm.PackageInfo
import android.content.res.AssetManager
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import java.io.File
import java.lang.Exception
import java.lang.RuntimeException
import java.lang.reflect.Method


/**
 * @Author: wlf
 * @CreateDate: 2022/3/11 12:00
 * @Description: 插件管理
 */
class PluginManager private constructor() {

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = PluginManager()
    }


    private lateinit var mContext: Context
    //包信息
    private lateinit var packageInfo: PackageInfo
    //类加载器
    private lateinit var dexClassLoader: DexClassLoader
    //资源包
    private lateinit var resources: Resources
    //
    private var pluginApkMap = mutableMapOf<String, PluginApkEntity>()

    fun init(context: Context) {
        //要用application 因为这是单例，直接用Activity对象作为上下文会导致内存泄漏
        mContext = context.applicationContext
    }

    /**
     * 从插件apk中读出我们所需要的信息
     * @param apkPath
     */
    fun loadPluginApk(apkPath: String) {
        //先拿到包信息
        //只拿Activity
        packageInfo = mContext.packageManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES) as PackageInfo
        //如果apkPath是传的错的，那就拿不到包信息了，下面的代码也就不用执行
        if (packageInfo == null) throw RuntimeException("插件加载失败")
        //类加载器，DexClassLoader专门负责外部dex的类
        val outFile: File = mContext.getDir("odex", Context.MODE_PRIVATE)
        dexClassLoader = DexClassLoader(apkPath, outFile.absolutePath, null, mContext.classLoader)
        //创建AssetManager，然后创建Resources
        try {
            val assetManager = AssetManager::class.java.newInstance()
            val method: Method = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            method.invoke(assetManager, apkPath)
            resources = Resources(
                assetManager,
                mContext.resources.displayMetrics,
                mContext.resources.configuration
            )
            val entity = PluginApkEntity()
            entity.packageInfo = packageInfo
            entity.dexClassLoader = dexClassLoader
            entity.resources = resources
            pluginApkMap[apkPath] = entity
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //把这3个玩意公开出去
//    fun getPackageInfo(): PackageInfo? {
//        return packageInfo
//    }
//
//    fun getDexClassLoader(): DexClassLoader? {
//        return dexClassLoader
//    }
//
//    fun getResources(): Resources? {
//        return resources
//    }

    fun getAllPluginInfo(): Map<String, PluginApkEntity> {
        return pluginApkMap
    }

    fun getPluginInfo(key: String): PluginApkEntity? {
        return pluginApkMap[key]
    }

    /**
     * 既然无论是宿主启动插件的Activity，还是插件内部的跳转都要使用ProxyActivity作为代理，
     * 何不写一个公共方法以供调用呢？
     *
     * @param context
     * @param realActivityClassName
     * @param bundle
     */
    fun toActivity(context: Context, pluginKey: String, realActivityClassName: String?, bundle: Bundle = Bundle()) {
        if (realActivityClassName.isNullOrEmpty()) return
        val intent = Intent(context, ProxyActivity::class.java)
        intent.putExtra("bundle", bundle ?: Bundle())
        intent.putExtra(PluginConst.TAG_CLASS_NAME, realActivityClassName)
        intent.putExtra(PluginConst.TAG_PLUGIN_KEY, pluginKey)
        context.startActivity(intent)
    }
}