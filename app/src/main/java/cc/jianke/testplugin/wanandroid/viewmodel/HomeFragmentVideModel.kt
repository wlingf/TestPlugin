package cc.jianke.testplugin.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import cc.jianke.testplugin.net.Api
import cc.jianke.testplugin.net.BaseResponse
import cc.jianke.testplugin.wanandroid.entity.ArticleListEntity
import cc.jianke.testplugin.wanandroid.entity.BannerEntity
import kotlinx.coroutines.launch

/**
 * @Author: wlf
 * @CreateDate: 2022/4/13 15:20
 * @Description:
 */
class HomeFragmentVideModel: BaseViewModel() {

    val bannerEntityLiveData = MutableLiveData<MutableList<BannerEntity>>()

    val articleListEntityLiveData = MutableLiveData<ArticleListEntity>()

    fun getBanner() {
        viewModelScope.launch {
            Api.get<BaseResponse<MutableList<BannerEntity>>>("banner/json") {
                bannerEntityLiveData.postValue(it.data)
            }
        }
    }

    fun getList(page: Int = 0) {
        viewModelScope.launch {
            Api.get<BaseResponse<ArticleListEntity>>("article/list/$page/json") {
                articleListEntityLiveData.postValue(it.data)
            }
        }
    }

    fun combine(page: Int = 0){
        viewModelScope.launch {

        }
    }
}