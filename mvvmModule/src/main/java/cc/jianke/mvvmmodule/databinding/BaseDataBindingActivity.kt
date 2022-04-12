package cc.jianke.mvvmmodule.databinding

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import cc.jianke.mvvmmodule.base.BaseActivity

/**
 * @Author: wlf
 * @CreateDate: 2022/4/12 10:39
 * @Description: DataBinding Activity 基类
 */
abstract class BaseDataBindingActivity<VB: ViewDataBinding>(@LayoutRes private val layoutId: Int): BaseActivity() {

    lateinit var mBindView: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBindView = DataBindingUtil.setContentView(this, layoutId)
        initViewBefore()
        initView()
        initEvent()
        initData()
    }

    protected abstract fun initView()

    protected abstract fun initEvent()

    protected abstract fun initData()

    open fun initViewBefore() {

    }

    override fun onDestroy() {
        mBindView.unbind()
        super.onDestroy()
    }

}