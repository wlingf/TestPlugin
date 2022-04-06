package cc.jianke.testplugin.net

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import rxhttp.toFlow
import rxhttp.wrapper.param.RxHttp


/**
 * @Author: wlf
 * @CreateDate: 2022/4/6 11:51
 * @Description:
 */
object Api {

    /**
     * postJson
     * @param url 地址
     * @param success 成功回调
     */
    suspend inline fun <reified T> postJson(url: String,
                                        crossinline success: (t: T) -> Unit) {
        postJson(url, mutableMapOf(), {}, success)
    }

    /**
     * postJson
     * @param url 地址
     * @param params 参数
     * @param success 成功回调
     */
    suspend inline fun <reified T> postJson(url: String,
                                        params: MutableMap<String, Any>,
                                        crossinline success: (t: T) -> Unit) {
        postJson(url, params, {}, success)
    }

    /**
     * postJson
     * @param url 地址
     * @param params 参数
     * @param fail 错误回调
     * @param success 成功回调
     */
    suspend inline fun <reified T> postJson(url: String,
                                        params: MutableMap<String, Any>,
                                        crossinline fail: (msg: String) -> Unit,
                                        crossinline success: (t: T) -> Unit) {
        RxHttp.postJson(url)
            .addAll(params)
            .toFlow<T>()
            .catch {
                it.message?.let { msg -> fail.invoke(msg) }
            }
            .collect {
                success.invoke(it)
            }
    }

    /**
     * get
     * @param url 地址
     * @param success 成功回调
     */
    suspend inline fun <reified T> get(url: String, crossinline success: (t: T) -> Unit) {
        get(url, {}, success)
    }

    /**
     * get
     * @param url 地址
     * @param fail 错误回调
     * @param success 成功回调
     */
    suspend inline fun <reified T> get(url: String,
                                       crossinline fail: (msg: String) -> Unit,
                                       crossinline success: (t: T) -> Unit) {
        RxHttp.get(url)
            .toFlow<T>()
            .catch {
                it.message?.let { msg -> fail.invoke(msg) }
            }
            .collect {
                success.invoke(it)
            }
    }
}