package cc.jianke.testplugin

import android.app.Application
import android.content.Context
import cc.jianke.pluginmodule.PluginManager
import okhttp3.OkHttpClient
import rxhttp.RxHttpPlugins
import java.util.concurrent.TimeUnit
import com.scwang.smart.refresh.footer.ClassicsFooter

import com.scwang.smart.refresh.layout.api.RefreshLayout

import com.scwang.smart.refresh.layout.api.RefreshFooter

import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator

import com.scwang.smart.refresh.layout.SmartRefreshLayout

import com.scwang.smart.refresh.header.ClassicsHeader

import com.scwang.smart.refresh.layout.api.RefreshHeader

import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator




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
            .build())
            .setDebug(BuildConfig.DEBUG)
            .setOnParamAssembly {
                it.add("packageName", BuildConfig.APPLICATION_ID)
            }
    }

    companion object {
        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout -> ClassicsHeader(context) }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> ClassicsFooter(context) }
        }
    }
}