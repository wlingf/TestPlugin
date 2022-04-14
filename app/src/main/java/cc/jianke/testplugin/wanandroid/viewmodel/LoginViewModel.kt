package cc.jianke.testplugin.wanandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import cc.jianke.testplugin.net.Api
import cc.jianke.testplugin.wanandroid.entity.RegisterEntity
import cc.jianke.testplugin.wanandroid.enum.LoginEnum
import cc.jianke.testplugin.wanandroid.event.LoginEvent
import cc.jianke.testplugin.wanandroid.utils.UserUtil
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.coroutines.launch

/**
 * @Author: wlf
 * @CreateDate: 2022/4/14 13:41
 * @Description:
 */
class LoginViewModel: BaseViewModel() {

    val registerLiveData = MutableLiveData<RegisterEntity>()

    val loginLiveData = MutableLiveData<RegisterEntity>()

    fun register(account: String, password: String) {
        viewModelScope.launch {
            val params: MutableMap<String, Any> = mutableMapOf()
            params["password"] = password
            params["repassword"] = password
            params["username"] = account
            Api.postForm<RegisterEntity>("user/register", params) {
                registerLiveData.postValue(it)
            }
        }
    }

    fun login(account: String, password: String) {
        viewModelScope.launch {
            val params: MutableMap<String, Any> = mutableMapOf()
            params["password"] = password
            params["username"] = account
            Api.postForm<RegisterEntity>("user/login", params) {
                UserUtil.setLoginStatus(true)
                loginLiveData.postValue(it)
                LiveEventBus.get(LoginEvent::class.java).post(LoginEvent(LoginEnum.LOGIN))
            }
        }
    }
}