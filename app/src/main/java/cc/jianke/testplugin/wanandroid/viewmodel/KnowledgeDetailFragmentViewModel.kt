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

    val articleListEntityLiveData = MutableLiveData<MutableList<ArticleEntity>>()
    val collectionLiveData = MutableLiveData<MutableMap<String, Any>>()

    fun getList(page: Int, cid: Int) {
        viewModelScope.launch {
            Api.get<BaseListResponse<ArticleEntity>>("article/list/$page/json?cid=$cid") {
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
}