package cc.jianke.testplugin

import android.util.Log
import cc.jianke.mvvmmodule.mvvm.BaseMvvmActivity
import cc.jianke.testplugin.databinding.ActivitySecondBinding
import cc.jianke.testplugin.fragment.TestFragment

/**
 * @Author: wlf
 * @CreateDate: 2022/3/14 10:50
 * @Description:
 */
class SecondActivity: BaseMvvmActivity<ActivitySecondBinding, SecondModel>() {

    override fun initView() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, TestFragment())
        fragmentTransaction.commit()
    }

    override fun initEvent() {
        mViewModel.articleListEntityLiveData.observe(this) {
            var s = ""
            it.datas.forEach { entity ->
                Log.e("TAG", entity.title)
                s += "${entity.title}\n"
            }
            mViewBinding.textView.text = s
        }
    }

    override fun initData() {
        mViewModel.request()
    }
}