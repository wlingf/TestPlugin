package cc.jianke.testplugin.wanandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import cc.jianke.testplugin.net.Api
import cc.jianke.testplugin.net.BaseListResponse
import cc.jianke.testplugin.wanandroid.entity.ArticleEntity
import kotlinx.coroutines.launch

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 14:12
 * @Description:
 */
class CollectViewModel: BaseViewModel() {

    private val _collectLiveData = MutableLiveData<MutableList<ArticleEntity>>()
    val collectLiveData: LiveData<MutableList<ArticleEntity>>
        get() = _collectLiveData

    private val _collectionLiveData = MutableLiveData<MutableMap<String, Int>>()
    val collectionLiveData: LiveData<MutableMap<String, Int>>
        get() = _collectionLiveData

    fun getCollectList(page: Int) {
        viewModelScope.launch {
            Api.get<BaseListResponse<ArticleEntity>>("lg/collect/list/$page/json"){
                _collectLiveData.postValue(it.datas)
            }
        }
    }

    fun unCollection(id: Int, originId: Int, position: Int) {
        viewModelScope.launch {
            val params: MutableMap<String, Any> = mutableMapOf()
            params["originId"] = originId
            Api.postForm<String>("lg/uncollect/$id/json", params){
                val map: MutableMap<String, Int> = mutableMapOf()
                map["id"] = originId
                map["position"] = position
                _collectionLiveData.postValue(map)
            }
        }
    }
}