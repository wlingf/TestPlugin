package cc.jianke.testplugin.wanandroid.activity

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import cc.jianke.mvvmmodule.mvvm.databinding.BaseDataBindingMvvmActivity
import cc.jianke.testplugin.R
import cc.jianke.testplugin.databinding.ActivityCoinBinding
import cc.jianke.testplugin.wanandroid.adapter.CoinAdapter
import cc.jianke.testplugin.wanandroid.utils.SmartRefreshUtil
import cc.jianke.testplugin.wanandroid.view.TitleBarView
import cc.jianke.testplugin.wanandroid.viewmodel.CoinViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 10:03
 * @Description:
 */
class CoinActivity: BaseDataBindingMvvmActivity<ActivityCoinBinding, CoinViewModel>(R.layout.activity_coin), OnRefreshListener, OnLoadMoreListener {

    private var page = 1
    private var refreshStatus = SmartRefreshUtil.NORMAL
    private lateinit var mAdapter: CoinAdapter
    private var isCoinList = true

    companion object {

        fun toCoinActivity(context: Context, isCoinList: Boolean = true) {
            val intent = Intent(context, CoinActivity::class.java)
            intent.putExtra("isCoinList", isCoinList)
            ActivityUtils.startActivity(intent)
        }
    }

    override fun initView() {
        isCoinList = intent.getBooleanExtra("isCoinList", true)

        mViewBind.recycleView.layoutManager = LinearLayoutManager(this)
        mAdapter = CoinAdapter()
        mViewBind.recycleView.adapter = mAdapter

        mViewBind.smartLayout.setOnRefreshListener(this)
        mViewBind.smartLayout.setOnLoadMoreListener(this)

        mViewBind.viewTitleBar.setTitle(if (isCoinList) "积分排行" else "积分记录")
        mViewBind.viewTitleBar.setVisibleEndText(!isCoinList)
        mViewBind.viewTitleBar.setOnClickListener(object : TitleBarView.OnTitleBarViewListener {
            override fun onEndTextClick() {
                toCoinActivity(this@CoinActivity)
            }
        })
    }

    override fun initEvent() {
        mViewModel.coinLiveData.observe(this) {
            SmartRefreshUtil.setSmartRefreshStatus(mViewBind.smartLayout, mAdapter, it, refreshStatus)
        }
    }

    override fun initData() {
        onRefresh(mViewBind.smartLayout)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 1
        refreshStatus = SmartRefreshUtil.REFRESH
        mViewModel.getCoinList(page, isCoinList)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page ++
        refreshStatus = SmartRefreshUtil.LOAD_MORE
        mViewModel.getCoinList(page, isCoinList)
    }
}