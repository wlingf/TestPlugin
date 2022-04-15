package cc.jianke.testplugin.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import cc.jianke.testplugin.net.Api
import cc.jianke.testplugin.wanandroid.entity.KnowledgeListEntity
import kotlinx.coroutines.launch

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 15:12
 * @Description:
 */
class KnowledgeDetailViewModel: BaseViewModel() {

    val knowledgeListEntityLiveData = MutableLiveData<MutableList<KnowledgeListEntity>>()

    fun getKnowledgeList() {
        viewModelScope.launch {
            Api.get<MutableList<KnowledgeListEntity>>("tree/json"){
                knowledgeListEntityLiveData.postValue(it)
            }
        }
    }
}