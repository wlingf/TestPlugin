package cc.jianke.mvvmmodule.databinding

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import java.lang.NullPointerException
import java.lang.reflect.ParameterizedType

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
    protected open fun getViewModel(): VM {
        var viewModel: VM? = null
        //当前对象超类的Type
        val type = javaClass.genericSuperclass
        //ParameterizedType表示参数化的类型
        if (type != null && type is ParameterizedType) {
            //返回此类型实际类型参数的Type对象数组
            val actualTypeArguments = type.actualTypeArguments
            val tClass = actualTypeArguments[1]
            viewModel = ViewModelProvider(this)[tClass as Class<VM>]
        }
        if (viewModel == null) {
            throw NullPointerException("viewModel is null, please create viewModel")
        }
        return viewModel
    }
}