package cc.jianke.mvvmmodule.mvvm.viewbinding

import androidx.viewbinding.ViewBinding
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import cc.jianke.mvvmmodule.utils.ViewModelUtil
import cc.jianke.mvvmmodule.viewbinding.BaseViewBindingActivity

/**
 * @Author: wlf
 * @CreateDate: 2022/4/6 10:05
 * @Description: MVVM框架 ViewBinding Activity 基类
 */
abstract class BaseViewBindingMvvmActivity<VB: ViewBinding, VM: BaseViewModel>: BaseViewBindingActivity<VB>() {

    lateinit var mViewModel: VM

    override fun initViewBefore() {
        super.initViewBefore()
        mViewModel = getViewModel()
    }

    /**
     * 获取ViewModel 子类可以复写，自行初始化
     */
    protected open fun getViewModel(): VM = ViewModelUtil.getViewModel(this, this) as VM
}