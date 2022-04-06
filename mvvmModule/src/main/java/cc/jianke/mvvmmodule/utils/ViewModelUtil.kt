package cc.jianke.mvvmmodule.utils

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import cc.jianke.mvvmmodule.mvvm.BaseViewModel
import java.lang.NullPointerException
import java.lang.reflect.ParameterizedType

/**
 * @Author: wlf
 * @CreateDate: 2022/4/6 11:21
 * @Description:
 */
object ViewModelUtil {

    /**
     * 获取ViewModel
     * @param owner
     * @return
     */
    @Deprecated("废弃，勿使用")
    fun getViewModel(owner: ViewModelStoreOwner): BaseViewModel {
        var viewModel: BaseViewModel? = null
        //当前对象超类的Type
        val type = javaClass.genericSuperclass
        //ParameterizedType表示参数化的类型
        if (type != null && type is ParameterizedType) {
            //返回此类型实际类型参数的Type对象数组
            val actualTypeArguments = type.actualTypeArguments
            val tClass = actualTypeArguments[1]
            viewModel = ViewModelProvider(owner)[tClass as Class<BaseViewModel>]
        }
        if (viewModel == null) {
            throw NullPointerException("viewModel is null, please create viewModel")
        }
        return viewModel
    }
}