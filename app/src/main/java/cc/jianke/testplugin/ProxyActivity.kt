package cc.jianke.testplugin

import android.app.Activity
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dalvik.system.DexClassLoader
import java.lang.ClassCastException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * @Author: wlf
 * @CreateDate: 2022/3/10 10:52
 * @Description: 代理activity
 */
class ProxyActivity: Activity() {

    companion object {
        const val PLUGIN_DEX_PATH = "plugin.dex.path"
        const val PLUGIN_ACTIVITY_CLASS_NAME = "plugin.activity.class.name"
    }

    var mPluginActivity: Activity? = null
    var mPluginResources: Resources? = null
    var mPluginDexPath = ""
    var mPluginActivityClassName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.let {
            mPluginDexPath = it.getStringExtra(PLUGIN_DEX_PATH).toString()
            mPluginActivityClassName = it.getStringExtra(PLUGIN_ACTIVITY_CLASS_NAME).toString()

            if (mPluginDexPath.isNullOrEmpty() || mPluginActivityClassName.isNullOrEmpty()) {
                return
            }

            //加载插件apk资源
            loadPluginApkResources()

            //根据apk路径加载apk代码到DexClassLoader中
            val dexOutputDir = getDir("dex", Context.MODE_PRIVATE)
            val dexClassLoader = DexClassLoader(mPluginDexPath, dexOutputDir.absolutePath, null, ClassLoader.getSystemClassLoader())
            dexClassLoader?.let { classLoader ->
                try {
                    //从DexClassLoader中获得功能Activity Class对象并通过反射创建一个功能Activity实例
                    val pluginActivityClass = classLoader.loadClass(mPluginActivityClassName)
                    Log.e("pluginActivityClass", "$pluginActivityClass")
                    mPluginActivity = pluginActivityClass.newInstance() as Activity
                    Log.e("mPluginActivity", "$mPluginActivity")
                    //调用功能Activity的setProxyActivity方法，给其设置代理Activity
                    val setProxyActivityMethod = pluginActivityClass.getDeclaredMethod("setProxyActivity", Activity::class.java) as Method
                    setProxyActivityMethod.invoke(mPluginActivity, this)

//                    val onCreateMethod = pluginActivityClass.getDeclaredMethod("onCreate", Bundle::class.java) as Method
//                    onCreateMethod.isAccessible = true
//                    onCreateMethod.invoke(mPluginActivity, Bundle())
                }catch (e: ClassCastException) {
                    e.printStackTrace()
                }catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }catch (e: InstantiationException) {
                    e.printStackTrace()
                }catch (e: NoSuchMethodException){
                    e.printStackTrace()
                }catch (e: InvocationTargetException){
                    e.printStackTrace()
                }
            }

        }

        try {
            val onCreateMethod = Activity::class.java.getDeclaredMethod("onCreate", Bundle::class.java) as Method
            onCreateMethod.isAccessible = true
            onCreateMethod.invoke(mPluginActivity, savedInstanceState)
        }catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }catch (e: IllegalAccessException) {
            e.printStackTrace()
        }catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

    /**
     * 加载插件apk资源
     */
    private fun loadPluginApkResources() {
        try {
            //通过反射创建一个AssetManager对象
            val assetManager = AssetManager::class.java.newInstance()
            //获得AssetManager对象的addAssetPath方法
            val addAssetPathManager = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
            //调用AssetManager的addAssetPath方法，将apk的资源添加到AssetManager中管理
            addAssetPathManager.invoke(assetManager, mPluginDexPath)
            //根据AssetManager创建一个Resources对象
            mPluginResources = Resources(assetManager, super.getResources().displayMetrics, super.getResources().configuration)
        }catch (e: InstantiationException) {
            e.printStackTrace()
        }catch (e: IllegalAccessException) {
            e.printStackTrace()
        }catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

    /**
     * 重写ProxyActivity的getResources方法，让其返回插件Apk的资源对象
     */
    override fun getResources(): Resources {
        mPluginResources?.let {
            return it
        }
        return super.getResources()
    }
}