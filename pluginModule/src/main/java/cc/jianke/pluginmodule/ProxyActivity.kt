package cc.jianke.pluginmodule

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import java.lang.Exception

/**
 * @Author: wlf
 * @CreateDate: 2022/3/11 13:01
 * @Description: 代理Activity
 *               作用：接收来自宿主的跳转意图，并且拿到其中的参数
 *               这里只能继承Activity，而不是AppCompatActivity，否则会报“空指针”
 *               原因是，AppCompatActivity会调用上下文
 */
class ProxyActivity: Activity() {

    private val TAG = "ProxyActivity"

    //真正的Activity class name
    private var mRealActivityName = ""

    //
    private var mPluginKey = ""

    private lateinit var mIPlugin: IPlugin

    /***********************  由ProxyActivity代为管理真正Activity的生命周期   ********************************/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //宿主，将真正的跳转意图，放在了这个参数className中
        mRealActivityName = intent.getStringExtra(PluginConst.TAG_CLASS_NAME).toString()
        mPluginKey = intent.getStringExtra(PluginConst.TAG_PLUGIN_KEY).toString()
        try {
            //拿到realActivityName，接下来的工作，自然就是展示出真正的Activity
            //原则，反射创建RealActivity对象，但是，去拿这个它的class，只能用dexClassLoader
            val realActivityClz = PluginManager.instance.getPluginInfo(mPluginKey)?.dexClassLoader!!.loadClass(mRealActivityName)
            val obj = realActivityClz?.newInstance()
            if (obj is IPlugin){
                mIPlugin = obj
                val bundle = savedInstanceState ?: Bundle()
                bundle.putInt(PluginConst.TAG_FROM, IPlugin.FROM_EXTERNAL)
                bundle.putBundle("bundle", intent.extras!!.getBundle("bundle"))
                mIPlugin.attach(this)
                mIPlugin.bindPluginKey(mPluginKey)
                mIPlugin.onCreate(bundle)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onStart() {
        mIPlugin.onStart()
        super.onStart()
    }

    override fun onResume() {
        mIPlugin.onResume()
        super.onResume()
    }

    override fun onRestart() {
        mIPlugin.onRestart()
        super.onRestart()
    }

    override fun onPause() {
        mIPlugin.onPause()
        super.onPause()
    }

    override fun onStop() {
        mIPlugin.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        mIPlugin.onDestroy()
        super.onDestroy()
    }

    override fun getClassLoader(): ClassLoader {
        return PluginManager.instance.getPluginInfo(mPluginKey)?.dexClassLoader ?: super.getClassLoader()
    }

    override fun getResources(): Resources {
        return PluginManager.instance.getPluginInfo(mPluginKey)?.resources ?: super.getResources()
    }
}