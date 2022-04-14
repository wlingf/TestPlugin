package cc.jianke.testplugin

import android.app.Application
import cc.jianke.pluginmodule.PluginManager
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import java.util.concurrent.TimeUnit
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.header.ClassicsHeader
import rxhttp.wrapper.cookie.CookieStore
import java.io.File


/**
 * @Author: wlf
 * @CreateDate: 2022/3/11 13:42
 * @Description:
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        PluginManager.instance.init(this)

        RxHttpPlugins.init(OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .cookieJar(CookieStore(File(getExternalFilesDir(""), "WanAndroidCookie")))
            .build())
            .setDebug(BuildConfig.DEBUG)
//            .setOnParamAssembly {
//                it.add("packageName", BuildConfig.APPLICATION_ID)
//            }
    }

    companion object {
        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout -> ClassicsHeader(context) }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> ClassicsFooter(context) }
        }
    }
}