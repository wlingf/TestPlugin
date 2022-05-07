package cc.jianke.testplugin.wanandroid.viewmodel

import android.util.Log
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
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onErrorResumeNext
import kotlinx.coroutines.launch
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * @Author: wlf
 * @CreateDate: 2022/4/13 15:20
 * @Description:
 */
class HomeFragmentVideModel: BaseViewModel() {

    private val _bannerEntityLiveData = MutableLiveData<MutableList<BannerEntity>>()
    val bannerEntityLiveData: MutableLiveData<MutableList<BannerEntity>>
        get() = _bannerEntityLiveData

    private val _articleListEntityLiveData = MutableLiveData<MutableList<ArticleEntity>>()
    val articleListEntityLiveData: MutableLiveData<MutableList<ArticleEntity>>
        get() = _articleListEntityLiveData

    private val _collectionLiveData = MutableLiveData<MutableMap<String, Any>>()
    val collectionLiveData: MutableLiveData<MutableMap<String, Any>>
        get() = _collectionLiveData

    fun getBanner() {
        viewModelScope.launch {
            Api.get<MutableList<BannerEntity>>("banner/json") {
                _bannerEntityLiveData.postValue(it)
            }
        }
    }

    fun getList(page: Int = 0) {
        viewModelScope.launch {
            Api.get<BaseListResponse<ArticleEntity>>("article/list/$page/json") {
                _articleListEntityLiveData.postValue(it.datas)
            }
        }
    }

    fun collection(id: Int) {
        viewModelScope.launch {
            Api.postForm<String>("lg/collect/$id/json"){
                val map = mapOf<String, Any>("id" to id, "collect" to true)
                _collectionLiveData.postValue(map as MutableMap<String, Any>)
            }
        }
    }

    fun unCollection(id: Int) {
        viewModelScope.launch {
            Api.postForm<String>("lg/uncollect_originId/$id/json"){
                val map = mapOf<String, Any>("id" to id, "collect" to false)
                _collectionLiveData.postValue(map as MutableMap<String, Any>)
            }
        }
    }

    fun combineRequest(page: Int = 0){
        viewModelScope.launch {
            val flowBanner = Api.get<MutableList<BannerEntity>>("banner/json")
                .catch {
                    emit(mutableListOf())
                }
            val flowArticleList = Api.get<BaseListResponse<ArticleEntity>>("article/list/$page/json")
                .catch {
                    val entity = BaseListResponse<ArticleEntity>()
                    entity.datas = mutableListOf()
                    emit(entity)
                }
            combine(flowBanner, flowArticleList){banner, article ->
                _bannerEntityLiveData.postValue(banner)
                _articleListEntityLiveData.postValue(article.datas)
            }.collect {  }
        }
    }
}