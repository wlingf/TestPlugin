package cc.jianke.testplugin.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import cc.jianke.testplugin.net.Api
import cc.jianke.testplugin.wanandroid.entity.AccountInfoEntity
import kotlinx.coroutines.launch

/**
 * @Author: wlf
 * @CreateDate: 2022/4/14 17:10
 * @Description:
 */
class MyFragmentViewModel: BaseViewModel() {

    val accountInfoLiveData = MutableLiveData<AccountInfoEntity>()

    fun getAccountInfo() {
        viewModelScope.launch {
            Api.get<AccountInfoEntity>("user/lg/userinfo/json"){
                accountInfoLiveData.postValue(it)
            }
        }
    }
}