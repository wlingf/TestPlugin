package cc.jianke.mvvmmodule.mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ViewBindingUtil

/**
 * @Author: wlf
 * @CreateDate: 2022/4/6 10:03
 * @Description: Activity基类
 */
abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {

    lateinit var mViewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        setContentView(mViewBinding.root)
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
}