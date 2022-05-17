package cc.jianke.testplugin.wanandroid.activity

import cc.jianke.mvvmmodule.mvvm.databinding.BaseDataBindingMvvmActivity
import cc.jianke.testplugin.R
import cc.jianke.testplugin.databinding.ActivityLoginBinding
import cc.jianke.testplugin.wanandroid.utils.ARouterPath
import cc.jianke.testplugin.wanandroid.viewmodel.LoginViewModel
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils

/**
 * @Author: wlf
 * @CreateDate: 2022/4/14 13:41
 * @Description:
 */
@Route(path = ARouterPath.LOGIN_ACTIVITY)
class LoginActivity: BaseDataBindingMvvmActivity<ActivityLoginBinding, LoginViewModel>(R.layout.activity_login) {

    override fun initView() {
        mViewBind.btnLogin.setOnClickListener {
            login()
        }
        mViewBind.btnRegister.setOnClickListener {
            register()
        }
    }

    override fun initEvent() {
        mViewModel.loginLiveData.observe(this){
            finish()
        }
        mViewModel.registerLiveData.observe(this) {
            login()
        }
    }

    override fun initData() {
    }

    private fun login() {
        if (checkLoginInfo()){
            mViewModel.login(mViewBind.etAccount.text.toString(), mViewBind.etPassword.text.toString())
        }
    }

    private fun register() {
        if (checkLoginInfo()){
            mViewModel.register(mViewBind.etAccount.text.toString(), mViewBind.etPassword.text.toString())
        }
    }

    private fun checkLoginInfo(): Boolean {
        if (mViewBind.etAccount.text.isNullOrEmpty()){
            ToastUtils.showShort("请输入账号")
            return false
        }
        if (mViewBind.etPassword.text.isNullOrEmpty()){
            ToastUtils.showShort("请输入密码")
            return false
        }
        return true
    }
}