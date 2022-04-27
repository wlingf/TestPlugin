package cc.jianke.testplugin.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import cc.jianke.testplugin.net.Api
import cc.jianke.testplugin.net.BaseListResponse
import cc.jianke.testplugin.wanandroid.entity.ArticleEntity
import kotlinx.coroutines.launch

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 15:34
 * @Description:
 */
class KnowledgeDetailFragmentViewModel: BaseViewModel() {

    private val _articleListEntityLiveData = MutableLiveData<MutableList<ArticleEntity>>()
    val articleListEntityLiveData: MutableLiveData<MutableList<ArticleEntity>>
        get() = _articleListEntityLiveData

    private val _collectionLiveData = MutableLiveData<MutableMap<String, Any>>()
    val collectionLiveData: MutableLiveData<MutableMap<String, Any>>
        get() = _collectionLiveData

    fun getList(page: Int, cid: Int) {
        viewModelScope.launch {
            Api.get<BaseListResponse<ArticleEntity>>("article/list/$page/json?cid=$cid") {
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
}