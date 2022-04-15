package cc.jianke.testplugin.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import cc.jianke.testplugin.net.Api
import cc.jianke.testplugin.net.BaseListResponse
import cc.jianke.testplugin.wanandroid.entity.CoinEntity
import cc.jianke.testplugin.wanandroid.utils.SmartRefreshUtil
import kotlinx.coroutines.launch

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 9:49
 * @Description:
 */
class CoinViewModel: BaseViewModel() {

    val coinLiveData = MutableLiveData<MutableList<CoinEntity>>()

    fun getCoinList(page: Int, isCoinList: Boolean = true) {
        viewModelScope.launch {
            Api.get<BaseListResponse<CoinEntity>>(if (isCoinList) "coin/rank/$page/json" else "lg/coin/list/$page/json"){
                if (!isCoinList) {
                    it.datas?.forEach { entity ->
                        entity.itemType = CoinEntity.TYPE_RECORD
                    }
                }
                coinLiveData.postValue(it.datas)
            }
        }
    }
}