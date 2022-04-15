package cc.jianke.testplugin.wanandroid.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cc.jianke.mvvmmodule.mvvm.databinding.BaseDataBindingMvvmFragment
import cc.jianke.testplugin.R
import cc.jianke.testplugin.databinding.FragmentMyBinding
import cc.jianke.testplugin.wanandroid.activity.CoinActivity
import cc.jianke.testplugin.wanandroid.activity.CollectActivity
import cc.jianke.testplugin.wanandroid.adapter.MyFragmentAdapter
import cc.jianke.testplugin.wanandroid.entity.MyAdapterEntity
import cc.jianke.testplugin.wanandroid.enum.LoginEnum
import cc.jianke.testplugin.wanandroid.enum.MyAdapterEnum
import cc.jianke.testplugin.wanandroid.event.LoginEvent
import cc.jianke.testplugin.wanandroid.utils.UserUtil
import cc.jianke.testplugin.wanandroid.viewmodel.MyFragmentViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lxj.xpopup.XPopup

/**
 * @Author: wlf
 * @CreateDate: 2022/4/14 17:11
 * @Description:
 */
class MyFragment: BaseDataBindingMvvmFragment<FragmentMyBinding, MyFragmentViewModel>(R.layout.fragment_my) {

    private lateinit var mAdapter: MyFragmentAdapter
    private val list: MutableList<MyAdapterEntity> = mutableListOf(MyAdapterEntity("我的积分", MyAdapterEnum.INTEGRAL),
        MyAdapterEntity("我的收藏", MyAdapterEnum.COLLECTION))

    override fun initView() {

        initEventBus()

        mViewBind.recycleView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = MyFragmentAdapter()
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val entity = adapter.data[position] as MyAdapterEntity
            onItemClick(entity.enum)
        }
        mViewBind.recycleView.adapter = mAdapter
        mAdapter.setNewInstance(list)

        mViewBind.btnLoginOut.setOnClickListener {
            XPopup.Builder(mContext)
                .asConfirm("温馨提示", "确定是否要退出登录") {
                    LiveEventBus.get(LoginEvent::class.java).post(LoginEvent(LoginEnum.LOGIN_OUT))
                }
                .show()
        }
    }

    override fun initEvent() {
        mViewModel.accountInfoLiveData.observe(this) {
            mAdapter.data.forEachIndexed { index, entity ->
                when(entity.enum) {
                    MyAdapterEnum.INTEGRAL -> {
                        entity.desc = "${it.coinInfo.coinCount}"
                        mAdapter.notifyItemChanged(index)
                    }
                }
            }
        }
    }

    override fun initData() {
        if (UserUtil.isLogin()){
            mViewBind.btnLoginOut.visibility = View.VISIBLE
            mViewModel.getAccountInfo()
        }
    }

    private fun initEventBus() {
        LiveEventBus.get(LoginEvent::class.java)
            .observe(this, {
                when(it.loginEnum) {
                    LoginEnum.LOGIN -> {
                        initData()
                    }
                    LoginEnum.LOGIN_OUT -> {
                        mViewBind.btnLoginOut.visibility = View.GONE
                        mAdapter.data.forEachIndexed { index, entity ->
                            when(entity.enum) {
                                MyAdapterEnum.INTEGRAL -> {
                                    entity.desc = ""
                                    mAdapter.notifyItemChanged(index)
                                }
                            }
                        }
                        UserUtil.loginOut()
                    }
                }
            })
    }

    private fun onItemClick(enum: MyAdapterEnum){
        if (!UserUtil.isLogin()){
            UserUtil.toLogin()
            return
        }
        when(enum){
            MyAdapterEnum.INTEGRAL -> {
                CoinActivity.toCoinActivity(mContext,false)
            }
            MyAdapterEnum.COLLECTION ->{
                ActivityUtils.startActivity(CollectActivity::class.java)
            }
        }
    }
}