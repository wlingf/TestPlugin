package cc.jianke.testplugin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import cc.jianke.testplugin.net.Api
import cc.jianke.testplugin.net.BaseResponse
import cc.jianke.testplugin.wanandroid.entity.ArticleListEntity
import kotlinx.coroutines.launch

/**
 * @Author: wlf
 * @CreateDate: 2022/4/6 10:37
 * @Description:
 */
class SecondModel: BaseViewModel() {

    val articleListEntityLiveData = MutableLiveData<ArticleListEntity>()

    fun request() {
//        viewModelScope.launch {
//            RxHttp.get("article/list/0/json")
//                .toFlow<BaseResponse<ArticleListEntity>>()
//                .catch {
//                    Log.e("TAG", "${it.message}")
//                }
//                .collect {
//                    articleListEntityLiveData.postValue(it.data)
//                }
//        }

        viewModelScope.launch {
            Api.get<BaseResponse<ArticleListEntity>>("article/list/0/json") {
                articleListEntityLiveData.postValue(it.data)
            }
        }
    }
}