package cc.jianke.testplugin.wanandroid.utils

import cc.jianke.testplugin.net.Url
import com.blankj.utilcode.util.SPUtils
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull

import rxhttp.RxHttpPlugins

import rxhttp.wrapper.cookie.ICookieJar




/**
 * @Author: wlf
 * @CreateDate: 2022/4/14 13:57
 * @Description:
 */
object UserUtil {

    private const val LOGIN_STATUS = "login_status"

    fun isLogin(): Boolean {
        return SPUtils.getInstance().getBoolean(LOGIN_STATUS)
    }

    fun setLoginStatus(isLogin: Boolean) {
        SPUtils.getInstance().put(LOGIN_STATUS, isLogin)
    }

    fun loginOut() {
        setLoginStatus(false)
        //删除cookie
        val iCookieJar = RxHttpPlugins.getOkHttpClient().cookieJar as ICookieJar
        val httpUrl = Url.BASE_URL.toHttpUrlOrNull()
        iCookieJar.removeCookie(httpUrl)
    }
}