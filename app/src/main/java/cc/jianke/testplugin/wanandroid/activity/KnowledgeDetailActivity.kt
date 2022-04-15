package cc.jianke.testplugin.wanandroid.activity

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import cc.jianke.mvvmmodule.mvvm.databinding.BaseDataBindingMvvmActivity
import cc.jianke.testplugin.R
import cc.jianke.testplugin.databinding.ActivityKnowledgeDetailBinding
import cc.jianke.testplugin.databinding.ItemTablayoutBinding
import cc.jianke.testplugin.wanandroid.entity.KnowledgeEntity
import cc.jianke.testplugin.wanandroid.fragment.KnowledgeDetailFragment
import cc.jianke.testplugin.wanandroid.viewmodel.KnowledgeDetailViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ColorUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 15:12
 * @Description:
 */
class KnowledgeDetailActivity: BaseDataBindingMvvmActivity<ActivityKnowledgeDetailBinding, KnowledgeDetailViewModel>(
    R.layout.activity_knowledge_detail) {

    companion object {

        fun toKnowledgeDetailActivity(context: Context, id: Int) {
            val intent = Intent(context, KnowledgeDetailActivity::class.java)
            intent.putExtra("id", id)
            ActivityUtils.startActivity(intent)
        }
    }

    override fun initView() {
        mViewBind.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    changeTabTextColor(tab.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun changeTabTextColor(position: Int) {
        for (i in 0 until mViewBind.tabLayout.tabCount) {
            val view = mViewBind.tabLayout.getTabAt(i)?.customView
            view?.let {
                val tv = view.findViewById<TextView>(R.id.tv_tab_title)
                tv.setTextColor(ColorUtils.getColor(if (position == i) R.color.color_primary else R.color.color_333333))
            }
        }

    }

    override fun initEvent() {
        mViewModel.knowledgeListEntityLiveData.observe(this){
            val id = intent.getIntExtra("id", 0)
            it.forEach { entity ->
                if (entity.id == id){
                    initTabLayout(entity.children)
                }
            }
        }
    }

    override fun initData() {
        mViewModel.getKnowledgeList()
    }

    private fun initTabLayout(children: MutableList<KnowledgeEntity>){
//        mViewBind.viewPager.offscreenPageLimit = children.size
        mViewBind.viewPager.adapter = ViewPagerAdapter(this, children)
        TabLayoutMediator(mViewBind.tabLayout, mViewBind.viewPager, true, true) {tab, position ->
            val binding = DataBindingUtil.inflate<ItemTablayoutBinding>(layoutInflater, R.layout.item_tablayout, null, false)
            binding.tvTabTitle.text = children[position].name
            tab.customView = binding.root
        }.attach()
    }

    class ViewPagerAdapter(fa: FragmentActivity, private val tabList: MutableList<KnowledgeEntity>): FragmentStateAdapter(fa) {

        override fun getItemCount(): Int {
            return tabList.size
        }

        override fun createFragment(position: Int): Fragment {
            return KnowledgeDetailFragment.newInstance(tabList[position].id)
        }

    }
}