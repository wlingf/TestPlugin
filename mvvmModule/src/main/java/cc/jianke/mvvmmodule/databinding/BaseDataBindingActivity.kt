package cc.jianke.mvvmmodule.databinding

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @Author: wlf
 * @CreateDate: 2022/4/12 10:39
 * @Description: DataBinding Activity 基类
 */
abstract class BaseDataBindingActivity<DB: ViewDataBinding>(@LayoutRes private val layoutId: Int): AppCompatActivity() {

    lateinit var mBindView: DB

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