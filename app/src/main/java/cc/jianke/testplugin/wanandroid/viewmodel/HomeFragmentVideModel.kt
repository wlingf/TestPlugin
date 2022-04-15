package cc.jianke.testplugin.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import cc.jianke.testplugin.net.Api
import cc.jianke.testplugin.net.BaseListResponse
import cc.jianke.testplugin.net.BaseResponse
import cc.jianke.testplugin.wanandroid.entity.ArticleEntity
import cc.jianke.testplugin.wanandroid.entity.ArticleListEntity
import cc.jianke.testplugin.wanandroid.entity.BannerEntity
import cc.jianke.testplugin.wanandroid.utils.SmartRefreshUtil
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * @Author: wlf
 * @CreateDate: 2022/4/13 15:20
 * @Description:
 */
class HomeFragmentVideModel: BaseViewModel() {

    val bannerEntityLiveData = MutableLiveData<MutableList<BannerEntity>>()

    val articleListEntityLiveData = MutableLiveData<MutableList<ArticleEntity>>()

    val collectionLiveData = MutableLiveData<MutableMap<String, Any>>()

    fun getBanner() {
        viewModelScope.launch {
            Api.get<MutableList<BannerEntity>>("banner/json") {
                bannerEntityLiveData.postValue(it)
            }
        }
    }

    fun getList(page: Int = 0) {
        viewModelScope.launch {
            Api.get<BaseListResponse<ArticleEntity>>("article/list/$page/json") {
                articleListEntityLiveData.postValue(it.datas)
            }
        }
    }

    fun collection(id: Int) {
        viewModelScope.launch {
            Api.postForm<String>("lg/collect/$id/json"){
                val map = mapOf<String, Any>("id" to id, "collect" to true)
                collectionLiveData.postValue(map as MutableMap<String, Any>)
            }
        }
    }

    fun unCollection(id: Int) {
        viewModelScope.launch {
            Api.postForm<String>("lg/uncollect_originId/$id/json"){
                val map = mapOf<String, Any>("id" to id, "collect" to false)
                collectionLiveData.postValue(map as MutableMap<String, Any>)
            }
        }
    }

    fun combine(page: Int = 0){
        viewModelScope.launch {

        }
    }
}