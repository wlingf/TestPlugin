package cc.jianke.testplugin.net

import cc.jianke.testplugin.wanandroid.utils.toast
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import rxhttp.toFlow
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse


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
    suspend inline fun <reified T> postForm(url: String,
                                            crossinline success: (t: T) -> Unit) {
        postForm(url, mutableMapOf(), null, success)
    }

    /**
     * postJson
     * @param url 地址
     * @param params 参数
     * @param success 成功回调
     */
    suspend inline fun <reified T> postForm(url: String,
                                            params: MutableMap<String, Any>,
                                            crossinline success: (t: T) -> Unit) {
        postForm(url, params, null, success)
    }

    /**
     * postJson
     * @param url 地址
     * @param params 参数
     * @param fail 错误回调
     * @param success 成功回调
     */
    suspend inline fun <reified T> postForm(
        url: String,
        params: MutableMap<String, Any>,
        noinline fail: ((msg: String) -> Unit?)?,
        crossinline success: (t: T) -> Unit) {
        RxHttp.postForm(url)
            .addAll(params)
            .toFlowResponse<T>()
            .catch {
                it.message?.let { msg ->
                    if (fail == null) {
                        msg.toast()
                    }else{
                        fail.invoke(msg)
                    }
                }
            }
            .collect {
                success.invoke(it)
            }
    }

    /**
     * postJson
     * @param url 地址
     * @param success 成功回调
     */
    suspend inline fun <reified T> postJson(url: String,
                                        crossinline success: (t: T) -> Unit) {
        postJson(url, mutableMapOf(), null, success)
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
        postJson(url, params, null, success)
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
                                        noinline fail: ((msg: String) -> Unit?)?,
                                        crossinline success: (t: T) -> Unit) {
        RxHttp.postJson(url)
            .addAll(params)
            .toFlowResponse<T>()
            .catch {
                it.message?.let { msg ->
                    if (fail == null){
                        msg.toast()
                    }else{
                        fail?.invoke(msg)
                    }
                }
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
        get(url, null, success)
    }

    /**
     * get
     * @param url 地址
     * @param fail 错误回调
     * @param success 成功回调
     */
    suspend inline fun <reified T> get(url: String,
                                       noinline fail: ((msg: String) -> Unit?)?,
                                       crossinline success: (t: T) -> Unit) {
        RxHttp.get(url)
            .toFlowResponse<T>()
            .catch {
                it.message?.let { msg ->
                    if (fail == null){
                        msg.toast()
                    }else{
                        fail?.invoke(msg)
                    }
                }
            }
            .collect {
                success.invoke(it)
            }
    }
}