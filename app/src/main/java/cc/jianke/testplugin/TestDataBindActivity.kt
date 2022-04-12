package cc.jianke.testplugin

import android.util.Log
import cc.jianke.mvvmmodule.databinding.BaseDataBindingMvvmActivity
import cc.jianke.testplugin.databinding.ActivityTestDatabindingBinding

/**
 * @Author: wlf
 * @CreateDate: 2022/4/12 10:52
 * @Description:
 */
class TestDataBindActivity: BaseDataBindingMvvmActivity<ActivityTestDatabindingBinding, SecondModel>(R.layout.activity_test_databinding) {

    override fun initView() {
    }

    override fun initEvent() {
        mViewModel.articleListEntityLiveData.observe(this) {
            var s = ""
            it.datas.forEach { entity ->
                Log.e("TAG", entity.title)
                s += "${entity.title}\n"
            }
            mBindView.example = s
        }
    }

    override fun initData() {
        mViewModel.request()
    }
}