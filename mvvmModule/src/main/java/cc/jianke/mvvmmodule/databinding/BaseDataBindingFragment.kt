package cc.jianke.mvvmmodule.databinding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import cc.jianke.mvvmmodule.base.BaseFragment
import cc.jianke.mvvmmodule.base.BaseLazyFragment

/**
 * @Author: wlf
 * @CreateDate: 2022/4/12 11:37
 * @Description: DataBinding Fragment 基类
 */
abstract class BaseDataBindingFragment<VB: ViewDataBinding>(@LayoutRes private val layoutId: Int): BaseLazyFragment() {

    lateinit var mViewBind: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBind = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mViewBind.root
    }

    override fun onDestroy() {
        mViewBind.unbind()
        super.onDestroy()
    }


}