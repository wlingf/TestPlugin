package cc.jianke.testplugin.net

import android.util.Log
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

    suspend fun post(url: String, params: MutableMap<String, Any> = mutableMapOf()) {

    }

    suspend fun get(url: String,
                    params: MutableMap<String, Any> = mutableMapOf(),
                    cls: Class<*> = BaseEntity::class.java,
                    fail: String.() -> Unit,
                    success: BaseEntity.() -> Unit
    ) {
        RxHttp.get(url)
            .toFlow<BaseResponse<ArticleListEntity>>()
            .catch {
                it.message?.let { msg -> fail.invoke(msg) }
            }
            .collect {
                it.data?.let { entity -> success.invoke(entity) }
            }
    }
}