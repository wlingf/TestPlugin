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

    private val _registerLiveData = MutableLiveData<RegisterEntity>()
    val registerLiveData: MutableLiveData<RegisterEntity>
        get() = _registerLiveData

    private val _loginLiveData = MutableLiveData<RegisterEntity>()
    val loginLiveData: MutableLiveData<RegisterEntity>
        get() = _loginLiveData

    fun register(account: String, password: String) {
        viewModelScope.launch {
            val params: MutableMap<String, Any> = mutableMapOf()
            params["password"] = password
            params["repassword"] = password
            params["username"] = account
            Api.postForm<RegisterEntity>("user/register", params) {
                _registerLiveData.postValue(it)
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
                LiveEventBus.get(LoginEvent::class.java).post(LoginEvent(LoginEnum.LOGIN))
                _loginLiveData.postValue(it)
            }
        }
    }
}