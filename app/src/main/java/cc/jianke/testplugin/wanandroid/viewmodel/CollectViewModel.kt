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
 * @CreateDate: 2022/4/15 14:12
 * @Description:
 */
class CollectViewModel: BaseViewModel() {

    val collectLiveData = MutableLiveData<MutableList<ArticleEntity>>()
    val collectionLiveData = MutableLiveData<MutableMap<String, Int>>()

    fun getCollectList(page: Int) {
        viewModelScope.launch {
            Api.get<BaseListResponse<ArticleEntity>>("lg/collect/list/$page/json"){
                collectLiveData.postValue(it.datas)
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
                collectionLiveData.postValue(map)
            }
        }
    }
}