package cc.jianke.testplugin.wanandroid.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import cc.jianke.mvvmmodule.mvvm.databinding.BaseDataBindingMvvmFragment
import cc.jianke.testplugin.R
import cc.jianke.testplugin.databinding.FragmentHomeBinding
import cc.jianke.testplugin.wanandroid.adapter.HomeFragmentAdapter
import cc.jianke.testplugin.wanandroid.entity.BannerEntity
import cc.jianke.testplugin.wanandroid.utils.SmartRefreshUtil
import cc.jianke.testplugin.wanandroid.viewmodel.HomeFragmentVideModel
import com.bumptech.glide.Glide
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator

/**
 * @Author: wlf
 * @CreateDate: 2022/4/13 15:07
 * @Description:
 */
class HomeFragment: BaseDataBindingMvvmFragment<FragmentHomeBinding, HomeFragmentVideModel>(R.layout.fragment_home), OnRefreshListener, OnLoadMoreListener {

    private lateinit var mAdapter: HomeFragmentAdapter
    private var page = 0
    private var refreshStatus = SmartRefreshUtil.NORMAL

    override fun initView() {
        mBindView.banner.addBannerLifecycleObserver(this).indicator = CircleIndicator(mContext)
        mBindView.recycleView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = HomeFragmentAdapter()
        mAdapter.setOnItemClickListener { adapter, view, position ->

        }
        mBindView.recycleView.adapter = mAdapter
        mBindView.smartLayout.setOnLoadMoreListener(this)
        mBindView.smartLayout.setOnRefreshListener(this)
    }

    override fun initEvent() {
        mViewModel.bannerEntityLiveData.observe(this) {
            mBindView.banner.setAdapter(object : BannerImageAdapter<BannerEntity>(it) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerEntity?,
                    position: Int,
                    size: Int
                ) {
                    Glide.with(mContext).load(data!!.imagePath).into(holder!!.imageView)
                }
            })
        }
        mViewModel.articleListEntityLiveData.observe(this){
            SmartRefreshUtil.setSmartRefreshStatus(
                mBindView.smartLayout,
                mAdapter,
                it.datas,
                refreshStatus)
        }
    }

    override fun initData() {
        mViewModel.getBanner()
        mViewModel.getList(page)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        refreshStatus = SmartRefreshUtil.REFRESH
        page = 0
        mViewModel.getList(page)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        refreshStatus = SmartRefreshUtil.LOAD_MORE
        page++
        mViewModel.getList(page)
    }
}