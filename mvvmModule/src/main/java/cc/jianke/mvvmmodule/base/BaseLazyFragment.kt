package cc.jianke.mvvmmodule.base

import android.util.Log

/**
 * @Author: wlf
 * @CreateDate: 2022/4/13 15:40
 * @Description: 懒加载Fragment
 */
abstract class BaseLazyFragment: BaseFragment() {

    /**
     * 记录是否懒加载过
     */
    private var isLazyLoad = false

    override fun onResume() {
        super.onResume()
        if (isLazy()){
            if (!isLazyLoad){
                isLazyLoad = true
                initLazy()
            }
        }else{
            initLazy()
        }
    }

    /**
     * 是否开启懒加载(默认开启)
     * @return
     */
    open fun isLazy(): Boolean = true

    private fun initLazy(){
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