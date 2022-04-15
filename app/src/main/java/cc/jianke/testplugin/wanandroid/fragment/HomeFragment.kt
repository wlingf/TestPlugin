package cc.jianke.testplugin.wanandroid.fragment

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cc.jianke.mvvmmodule.mvvm.databinding.BaseDataBindingMvvmFragment
import cc.jianke.testplugin.R
import cc.jianke.testplugin.databinding.FragmentHomeBinding
import cc.jianke.testplugin.wanandroid.activity.LoginActivity
import cc.jianke.testplugin.wanandroid.activity.WebActivity
import cc.jianke.testplugin.wanandroid.adapter.HomeFragmentAdapter
import cc.jianke.testplugin.wanandroid.entity.ArticleEntity
import cc.jianke.testplugin.wanandroid.entity.BannerEntity
import cc.jianke.testplugin.wanandroid.enum.LoginEnum
import cc.jianke.testplugin.wanandroid.event.CollectEvent
import cc.jianke.testplugin.wanandroid.event.LoginEvent
import cc.jianke.testplugin.wanandroid.utils.SmartRefreshUtil
import cc.jianke.testplugin.wanandroid.utils.UserUtil
import cc.jianke.testplugin.wanandroid.viewmodel.HomeFragmentVideModel
import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.Glide
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener

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

        initEventBus()

        mViewBind.banner.addBannerLifecycleObserver(this)
            .indicator = CircleIndicator(mContext)

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

        mViewBind.smartLayout.setOnLoadMoreListener(this)
        mViewBind.smartLayout.setOnRefreshListener(this)
    }

    override fun initEvent() {
        mViewModel.bannerEntityLiveData.observe(this) {
            mViewBind.banner.setAdapter(object : BannerImageAdapter<BannerEntity>(it) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerEntity?,
                    position: Int,
                    size: Int
                ) {
                    Glide.with(mContext).load(data!!.imagePath).into(holder!!.imageView)
                }
            })
                .setOnBannerListener { data, position ->
                    val entity = data as BannerEntity
                    WebActivity.toWeb(mContext, entity.url)
                }
        }
        mViewModel.articleListEntityLiveData.observe(this){
            SmartRefreshUtil.setSmartRefreshStatus(
                mViewBind.smartLayout,
                mAdapter,
                it,
                refreshStatus)
        }
        mViewModel.collectionLiveData.observe(this){
            mAdapter.data.forEachIndexed { index, articleEntity ->
                if (articleEntity.id == it["id"]) {
                    articleEntity.isCollection = it["collect"] as Boolean
                    mAdapter.notifyItemChanged(index)
                }
            }
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

    private fun initEventBus() {
        LiveEventBus.get(LoginEvent::class.java)
            .observe(this, {
                onRefresh(mViewBind.smartLayout)
            })
        LiveEventBus.get(CollectEvent::class.java)
            .observe(this, {
                mAdapter.data.forEach { entity ->
                    if (it.id == entity.id){
                        entity.isCollection = false
                        mAdapter.notifyDataSetChanged()
                    }
                }
            })
    }
}