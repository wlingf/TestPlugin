package cc.jianke.testplugin.wanandroid.activity

import android.content.Context
import android.content.Intent
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import cc.jianke.mvvmmodule.databinding.BaseDataBindingActivity
import cc.jianke.testplugin.R
import cc.jianke.testplugin.databinding.ActivityWebBinding

/**
 * @Author: wlf
 * @CreateDate: 2022/4/13 17:59
 * @Description:
 */
class WebActivity: BaseDataBindingActivity<ActivityWebBinding>(R.layout.activity_web) {

    companion object {

        const val URL = "url"

        fun toWeb(context: Context, url: String) {
            val intent = Intent(context, WebActivity::class.java)
            intent.putExtra(URL, url)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        mViewBind.webView.webViewClient = webViewClient
        mViewBind.webView.webChromeClient = webChromeClient
    }

    override fun initEvent() {
    }

    override fun initData() {
        intent?.let {
            val url = it.getStringExtra(URL)
            if (!url.isNullOrEmpty()){
                mViewBind.webView.loadUrl(url)
            }
        }
    }

    private val webViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view!!.loadUrl(url!!)
            return true
        }
    }

    private val webChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            Log.d("TSSS", "$newProgress")
        }
    }
}