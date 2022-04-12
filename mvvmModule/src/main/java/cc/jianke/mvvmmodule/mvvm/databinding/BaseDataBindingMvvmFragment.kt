package cc.jianke.mvvmmodule.mvvm.databinding

import androidx.databinding.ViewDataBinding
import cc.jianke.mvvmmodule.databinding.BaseDataBindingFragment
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import cc.jianke.mvvmmodule.utils.ViewModelUtil
/**
 * @Author: wlf
 * @CreateDate: 2022/4/12 11:40
 * @Description: MVVM框架 DataBinding Fragment 基类
 */
abstract class BaseDataBindingMvvmFragment<DB: ViewDataBinding, VM: BaseViewModel>(layoutId: Int): BaseDataBindingFragment<DB>(layoutId) {

    lateinit var mViewModel: VM

    override fun initViewBefore() {
        super.initViewBefore()
        mViewModel = getViewModel()
    }

    /**
     * 获取ViewModel 子类可以复写，自行初始化
     */
    protected open fun getViewModel(): VM = ViewModelUtil.getViewModel(activity!!, this) as VM
}