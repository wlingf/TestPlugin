package cc.jianke.testplugin

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import cc.jianke.testplugin.net.ArticleListEntity
import cc.jianke.testplugin.net.BaseResponse
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import rxhttp.toFlow
import rxhttp.wrapper.param.RxHttp

/**
 * @Author: wlf
 * @CreateDate: 2022/4/6 10:37
 * @Description:
 */
class SecondModel: BaseViewModel() {

    val articleListEntityLiveData = MutableLiveData<ArticleListEntity>()

    fun request() {
        viewModelScope.launch {
            RxHttp.get("article/list/0/json")
                .toFlow<BaseResponse<ArticleListEntity>>()
                .catch {
                    Log.e("TAG", "${it.message}")
                }
                .collect {
                    articleListEntityLiveData.postValue(it.data)
                }
        }
    }
}