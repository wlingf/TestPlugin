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

    lateinit var mBindView: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBindView = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mBindView.root
    }

    override fun onDestroy() {
        mBindView.unbind()
        super.onDestroy()
    }


}