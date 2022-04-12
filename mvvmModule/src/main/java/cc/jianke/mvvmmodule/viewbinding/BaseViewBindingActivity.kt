package cc.jianke.mvvmmodule.viewbinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import cc.jianke.mvvmmodule.base.BaseActivity
import com.dylanc.viewbinding.base.ViewBindingUtil

/**
 * @Author: wlf
 * @CreateDate: 2022/4/6 10:03
 * @Description: ViewBinding Activity基类
 */
abstract class BaseViewBindingActivity<VB: ViewBinding>: BaseActivity() {

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