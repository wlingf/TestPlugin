package cc.jianke.testplugin

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import cc.jianke.pluginmodule.IPluginMessageEvent
import cc.jianke.pluginmodule.PluginEventConst
import cc.jianke.pluginmodule.PluginManager
import cc.jianke.pluginmodule.PluginMessageEvent
import cc.jianke.testplugin.net.ArticleListEntity
import cc.jianke.testplugin.net.BaseResponse
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import rxhttp.toDownload
import rxhttp.toFlow
import rxhttp.wrapper.param.RxHttp
import java.util.*


open class MainActivity : BaseActivity() {

    private val TAG = "MainActivity"
    private var PLUGIN_1 = "/sdcard/app-release.apk"
    private var PLUGIN_2 = "/sdcard/jianzhike-app-release.apk"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //https://pan.baidu.com/s/15ihJas2LWJcKNEASVvUclA?pwd=if8e
        findViewById<Button>(R.id.jump_plugin).setOnClickListener {
            PluginManager.instance.toActivity(this,
                PLUGIN_1,
                PluginManager.instance.getPluginInfo(PLUGIN_1)?.packageInfo!!.activities[0].name)
        }

        findViewById<Button>(R.id.load_plugin).setOnClickListener {
            PluginManager.instance.loadPluginApk("/sdcard/app-release.apk")
            Toast.makeText(this, "加载插件apk成功", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.jump_plugin_data).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("key", "来自宿主的参数值")
            PluginManager.instance.toActivity(this,
                PLUGIN_1,
                PluginManager.instance.getPluginInfo(PLUGIN_1)?.packageInfo!!.activities[0].name, bundle)
        }

        findViewById<Button>(R.id.share_local_data).setOnClickListener {
            SPUtils.getInstance().put("key", "由宿主在本地写入缓存数据")
            PluginManager.instance.toActivity(this,
                PLUGIN_1,
                PluginManager.instance.getPluginInfo(PLUGIN_1)?.packageInfo!!.activities[0].name)
        }

        findViewById<Button>(R.id.get_plugin_local_data).setOnClickListener {
            Log.e(TAG, "宿主获取插件写入本地缓存数据：${SPUtils.getInstance().getString("key1")}")
            Toast.makeText(this, "宿主获取插件写入本地缓存数据：${SPUtils.getInstance().getString("key1")}", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.jump_other_activity).setOnClickListener {
            PluginManager.instance.toActivity(this,
                PLUGIN_1,
                "cc.jianke.testplugintwo.SecondActivity")
        }

        XXPermissions.with(this)
            .permission(Permission.Group.STORAGE)
            .request { permissions, all ->
                if(all) {
                    val str = this.getExternalFilesDir("")?.absolutePath
                    Log.e(TAG, "路径：$str")
                }
            }

        LiveEventBus.get<PluginMessageEvent>(PluginEventConst.PLUGIN_EVENT)
            .observe(this,  {
                if (it is IPluginMessageEvent){
                    Log.e(TAG, "宿主获取插件发送事件： ${it.value}")
                    Toast.makeText(this, "宿主获取插件发送事件： ${it.value}", Toast.LENGTH_SHORT).show()
                    if (it.value == "openPluginThree"){
                        PluginManager.instance.loadPluginApk("/sdcard/jianzhike-app-release.apk")
                        Toast.makeText(this, "加载插件apk成功", Toast.LENGTH_SHORT).show()

                        val handler = Handler()
                        handler.postDelayed({
                            PluginManager.instance.toActivity(this,
                                PLUGIN_2,
                                PluginManager.instance.getPluginInfo(PLUGIN_2)?.packageInfo!!.activities[0].name)
                        }, 2000)
                    }
                }
            })

        findViewById<Button>(R.id.down_load_plugin_apk).setOnClickListener {
            GlobalScope.launch {
                downloadApk("https://pan.baidu.com/s/1dTnhxD7GRBoMsV9nD6y8gQ?pwd=24x1", "test_plugin_one.apk")
            }
        }

        findViewById<Button>(R.id.down_load_plugin_apk2).setOnClickListener {
            GlobalScope.launch {
                downloadApk("https://pan.baidu.com/s/1litFu2HbQaxfTVgIrjMzUw?pwd=6brn", "test_plugin_two.apk")
            }
        }

        testNet()
    }

    private suspend fun downloadApk(url: String, fileName: String) {
        val factory = Android10DownloadFactory(this, fileName)
        val uri = RxHttp.get(url)
            .toDownload(factory){
                Log.e(TAG, "下载详情--当前进度：${it.progress}")
            }
            .await()
        Log.e(TAG, "已下载完成：${uri.path} -- $uri")
    }

    private fun testNet() {
        ActivityUtils.startActivity(TestDataBindActivity::class.java)
    }
}