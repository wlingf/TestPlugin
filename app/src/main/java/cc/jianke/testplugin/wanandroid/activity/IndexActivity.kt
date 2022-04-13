package cc.jianke.testplugin.wanandroid.activity

import android.util.Log
import androidx.fragment.app.Fragment
import cc.jianke.mvvmmodule.mvvm.databinding.BaseDataBindingMvvmActivity
import cc.jianke.testplugin.R
import cc.jianke.testplugin.databinding.ActivityIndexBinding
import cc.jianke.testplugin.wanandroid.entity.IndexTabEntity
import cc.jianke.testplugin.wanandroid.fragment.HomeFragment
import cc.jianke.testplugin.wanandroid.fragment.KnowledgeFragment
import cc.jianke.testplugin.wanandroid.utils.FragmentChangeManager
import cc.jianke.testplugin.wanandroid.viewmodel.MainViewModel
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import java.util.ArrayList

/**
 * @Author: wlf
 * @CreateDate: 2022/4/13 15:13
 * @Description:
 */
class IndexActivity: BaseDataBindingMvvmActivity<ActivityIndexBinding, MainViewModel>(R.layout.activity_index) {

    private val fragmentList: MutableList<Fragment> = mutableListOf(HomeFragment(), KnowledgeFragment())
    private val tabList: MutableList<CustomTabEntity> = mutableListOf(
        IndexTabEntity("首页", R.mipmap.icon_index_sel, R.mipmap.icon_index_noraml),
        IndexTabEntity("知识体系", R.mipmap.icon_knowledge_sel, R.mipmap.icon_knowledge_normal)
    )
    private lateinit var mFragmentChangeManager: FragmentChangeManager

    override fun initView() {
        mFragmentChangeManager = FragmentChangeManager(
            supportFragmentManager,
            R.id.fragment_container,
            fragmentList as ArrayList<Fragment>)
        mBindView.tabLayout.setTabData(
            tabList as ArrayList<CustomTabEntity>
        )
    }

    override fun initEvent() {
        mBindView.tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                mFragmentChangeManager.setFragments(position)
            }

            override fun onTabReselect(position: Int) {
            }

        })
    }

    override fun initData() {
    }
}