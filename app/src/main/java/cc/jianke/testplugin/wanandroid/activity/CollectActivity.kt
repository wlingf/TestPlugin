package cc.jianke.testplugin.wanandroid.activity

import androidx.recyclerview.widget.LinearLayoutManager
import cc.jianke.mvvmmodule.mvvm.databinding.BaseDataBindingMvvmActivity
import cc.jianke.testplugin.R
import cc.jianke.testplugin.databinding.ActivityCollectBinding
import cc.jianke.testplugin.wanandroid.adapter.CollectAdapter
import cc.jianke.testplugin.wanandroid.enum.LoginEnum
import cc.jianke.testplugin.wanandroid.event.CollectEvent
import cc.jianke.testplugin.wanandroid.event.LoginEvent
import cc.jianke.testplugin.wanandroid.utils.SmartRefreshUtil
import cc.jianke.testplugin.wanandroid.viewmodel.CollectViewModel
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 14:16
 * @Description:
 */
class CollectActivity: BaseDataBindingMvvmActivity<ActivityCollectBinding, CollectViewModel>(R.layout.activity_collect),
    OnRefreshListener, OnLoadMoreListener {

    private var page = 0
    private var refreshStatus = SmartRefreshUtil.NORMAL
    private lateinit var mAdapter: CollectAdapter

    override fun initView() {

        mViewBind.recycleView.layoutManager = LinearLayoutManager(this)
        mAdapter = CollectAdapter()
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val entity = mAdapter.data[position]
            WebActivity.toWeb(this, entity.link)
        }
        mAdapter.addChildClickViewIds(R.id.iv_collection)
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val entity = mAdapter.data[position]
            mViewModel.unCollection(entity.id, entity.originId, position)
        }
        mViewBind.recycleView.adapter = mAdapter

        mViewBind.smartLayout.setOnRefreshListener(this)
        mViewBind.smartLayout.setOnLoadMoreListener(this)
    }

    override fun initEvent() {
        mViewModel.collectLiveData.observe(this) {
            SmartRefreshUtil.setSmartRefreshStatus(mViewBind.smartLayout, mAdapter, it, refreshStatus)
        }
        mViewModel.collectionLiveData.observe(this){
            it["id"]?.let { id ->
                LiveEventBus.get(CollectEvent::class.java).post(CollectEvent(id))
            }
            it["position"]?.let { position ->
                mAdapter.removeAt(position)
            }
        }
    }

    override fun initData() {
        onRefresh(mViewBind.smartLayout)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 0
        refreshStatus = SmartRefreshUtil.REFRESH
        mViewModel.getCollectList(page)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page ++
        refreshStatus = SmartRefreshUtil.LOAD_MORE
        mViewModel.getCollectList(page)
    }
}