package cc.jianke.mvvmmodule.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

/**
 * @Author: wlf
 * @CreateDate: 2022/4/12 11:53
 * @Description: Fragment 基类
 */
abstract class BaseFragment: Fragment() {

    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    fun show(f: Fragment, frameContainerId: Int, hide: Fragment? = null) {
        childFragmentManager.beginTransaction().apply {
            if (f.isAdded) {
                show(f)
                setMaxLifecycle(f, Lifecycle.State.RESUMED)
            } else {
                add(frameContainerId, f)
            }
            hide?.let {
                if (it.isAdded) {
                    hide(it)
                    setMaxLifecycle(it, Lifecycle.State.STARTED)
                }
            }
            commitAllowingStateLoss()
        }
    }
}