package cc.jianke.mvvmmodule.viewbinding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import cc.jianke.mvvmmodule.base.BaseFragment
import cc.jianke.mvvmmodule.base.BaseLazyFragment
import com.dylanc.viewbinding.base.ViewBindingUtil

/**
 * @Author: wlf
 * @CreateDate: 2022/4/6 11:12
 * @Description: ViewBinding Fragment基类
 */
abstract class BaseViewBindingFragment<VB: ViewBinding>: BaseLazyFragment() {

    var mViewBind: VB? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBind = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
        return mViewBind!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBind = null
    }
}