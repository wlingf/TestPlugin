package cc.jianke.testplugin.wanandroid.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import cc.jianke.mvvmmodule.mvvm.databinding.BaseDataBindingMvvmFragment
import cc.jianke.testplugin.R
import cc.jianke.testplugin.databinding.FragmentKnowledgeDetailBinding
import cc.jianke.testplugin.wanandroid.activity.WebActivity
import cc.jianke.testplugin.wanandroid.adapter.HomeFragmentAdapter
import cc.jianke.testplugin.wanandroid.event.CollectEvent
import cc.jianke.testplugin.wanandroid.utils.SmartRefreshUtil
import cc.jianke.testplugin.wanandroid.utils.UserUtil
import cc.jianke.testplugin.wanandroid.viewmodel.KnowledgeDetailFragmentViewModel
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 15:34
 * @Description:
 */
class KnowledgeDetailFragment: BaseDataBindingMvvmFragment<FragmentKnowledgeDetailBinding, KnowledgeDetailFragmentViewModel>(
    R.layout.fragment_knowledge_detail), OnRefreshListener, OnLoadMoreListener {

    private lateinit var mAdapter: HomeFragmentAdapter
    private var page = 0
    private var refreshStatus = SmartRefreshUtil.NORMAL
    private var cId = 0

    companion object {
        fun newInstance(id: Int): KnowledgeDetailFragment {
            val fragment = KnowledgeDetailFragment()
            val bundle = Bundle()
            bundle.putInt("id", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView() {
        mViewBind.recycleView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = HomeFragmentAdapter()
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val entity = mAdapter.data[position]
            WebActivity.toWeb(mContext, entity.link)
        }
        mAdapter.addChildClickViewIds(R.id.iv_collection)
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (!UserUtil.isLogin()){
                UserUtil.toLogin()
                return@setOnItemChildClickListener
            }
            val entity = mAdapter.data[position]
            if (entity.isCollection){
                mViewModel.unCollection(entity.id)
            }else{
                mViewModel.collection(entity.id)
            }
        }
        mViewBind.recycleView.adapter = mAdapter

        mViewBind.smartLayout.setOnRefreshListener(this)
        mViewBind.smartLayout.setOnLoadMoreListener(this)
    }

    override fun initEvent() {
        mViewModel.articleListEntityLiveData.observe(this) {
            SmartRefreshUtil.setSmartRefreshStatus(mViewBind.smartLayout, mAdapter, it, refreshStatus)
        }
        mViewModel.collectionLiveData.observe(this){
            LiveEventBus.get(CollectEvent::class.java).post(CollectEvent(it["id"] as Int, it["collect"] as Boolean))
            mAdapter.data.forEachIndexed { index, articleEntity ->
                if (articleEntity.id == it["id"]) {
                    articleEntity.isCollection = it["collect"] as Boolean
                    mAdapter.notifyItemChanged(index)
                }
            }
        }
    }

    override fun initData() {
        arguments?.let {
            cId = it.getInt("id")
            onRefresh(mViewBind.smartLayout)
        }
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        refreshStatus = SmartRefreshUtil.REFRESH
        mViewModel.getList(page, cId)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page ++
        refreshStatus = SmartRefreshUtil.LOAD_MORE
        mViewModel.getList(page, cId)
    }
}