package cc.jianke.mvvmmodule.mvvm

import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.NullPointerException
import java.lang.reflect.ParameterizedType

/**
 * @Author: wlf
 * @CreateDate: 2022/4/6 11:18
 * @Description: MVVM框架Fragment基类
 */
abstract class BaseMvvmFragment<VB: ViewBinding, VM: BaseViewModel>: BaseFragment<VB>() {

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