package cc.jianke.testplugin.wanandroid.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle

/**
 * @Author: wlf
 * @CreateDate: 2022/4/13 16:10
 * @Description:
 */
class FragmentChangeManager constructor(mFragmentManager: FragmentManager, mContainerViewId: Int, mFragments: MutableList<Fragment>) {

    private var mFragmentManager: FragmentManager = mFragmentManager
    private var mContainerViewId: Int = mContainerViewId
    private var mFragments: MutableList<Fragment> = mFragments
    private var mCurrentTab: Int = 0

    init {
        initFragments()
    }

    private fun initFragments() {
        for (fragment in mFragments) {
            mFragmentManager.beginTransaction().add(mContainerViewId, fragment).hide(fragment)
                .commit()
        }
        setFragments(0)
    }

    open fun setFragments(index: Int) {
        for (i in mFragments.indices) {
            val ft = mFragmentManager.beginTransaction()
            val fragment = mFragments[i]
            if (i == index) {
                ft.show(fragment)
                ft.setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
            } else {
                ft.hide(fragment)
                ft.setMaxLifecycle(fragment, Lifecycle.State.STARTED)
            }
            ft.commit()
        }
        mCurrentTab = index
    }

}