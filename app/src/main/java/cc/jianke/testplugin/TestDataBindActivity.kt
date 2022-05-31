package cc.jianke.testplugin

import android.util.Log
import cc.jianke.mvvmmodule.mvvm.databinding.BaseDataBindingMvvmActivity
import cc.jianke.testplugin.databinding.ActivityTestDatabindingBinding
import com.plattysoft.leonids.ParticleSystem
import com.plattysoft.leonids.modifiers.ScaleModifier

/**
 * @Author: wlf
 * @CreateDate: 2022/4/12 10:52
 * @Description:
 */
class TestDataBindActivity: BaseDataBindingMvvmActivity<ActivityTestDatabindingBinding, SecondModel>(R.layout.activity_test_databinding) {

    override fun initView() {
        mViewBind.btnAnim.setOnClickListener {
            //使用说明http://www.javashuo.com/article/p-qorssnbv-nn.html
            ParticleSystem(this, 7, R.mipmap.ic_1, 3000)
                .setSpeedModuleAndAngleRange(0.2f, 0.5f, 220, 320)
                .setRotationSpeed(20f)
                .setFadeOut(3000)
                .addModifier(ScaleModifier(1f, 0.3f, 1500, 3000))
                .setAcceleration(0f, 90)
                .oneShot(mViewBind.btnAnim, 2)
        }
    }

    override fun initEvent() {
        mViewModel.articleListEntityLiveData.observe(this) {
            var s = ""
            it.datas.forEach { entity ->
                Log.e("TAG", entity.title)
                s += "${entity.title}\n"
            }
            mViewBind.example = s
        }
    }

    override fun initData() {
//        mViewModel.request()
    }
}