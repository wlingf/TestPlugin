package cc.jianke.mvvmmodule.databinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import cc.jianke.mvvmmodule.base.BaseFragment

/**
 * @Author: wlf
 * @CreateDate: 2022/4/12 11:37
 * @Description: DataBinding Fragment 基类
 */
abstract class BaseDataBindingFragment<DB: ViewDataBinding>(@LayoutRes private val layoutId: Int): BaseFragment() {

    lateinit var mBindView: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBindView = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mBindView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewBefore()
        initView()
        initEvent()
        initData()

    }

    override fun onDestroy() {
        mBindView.unbind()
        super.onDestroy()
    }

    protected abstract fun initView()

    protected abstract fun initEvent()

    protected abstract fun initData()

    open fun initViewBefore() {

    }
}