package cc.jianke.pluginmodule

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 * @Author: wlf
 * @CreateDate: 2022/3/11 11:40
 * @Description: 插件Activity的接口规范
 */
interface IPlugin {

    companion object {
        //插件单独测试时的内部跳转
        const val FROM_INTERNAL = 0
        //宿主执行的跳转逻辑
        const val FROM_EXTERNAL = 1
    }

    /**
     * 给插件Activity指定上下文
     * @param activity
     */
    fun attach(activity: Activity)

    fun bindPluginKey(pluginKey: String)

    /*************************************************************************************
    * 以下全都是Activity生命周期函数
    * 插件Activity本身 在被用作"插件"的时候不具备生命周期，由宿主里面的代理Activity类代为管理
    *************************************************************************************/

    fun onCreate(saveInstanceState: Bundle)

    fun onStart()

    fun onResume()

    fun onRestart()

    fun onPause()

    fun onStop()

    fun onDestroy()

    fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent)

}