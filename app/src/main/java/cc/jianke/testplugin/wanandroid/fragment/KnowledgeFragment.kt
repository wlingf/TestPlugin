package cc.jianke.testplugin.wanandroid.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import cc.jianke.mvvmmodule.mvvm.databinding.BaseDataBindingMvvmFragment
import cc.jianke.testplugin.R
import cc.jianke.testplugin.databinding.FragmentKnowledgeBinding
import cc.jianke.testplugin.wanandroid.adapter.KnowledgeFragmentAdapter
import cc.jianke.testplugin.wanandroid.viewmodel.KnowledgeFragmentViewModel

/**
 * @Author: wlf
 * @CreateDate: 2022/4/13 15:20
 * @Description:
 */
class KnowledgeFragment: BaseDataBindingMvvmFragment<FragmentKnowledgeBinding, KnowledgeFragmentViewModel>(
    R.layout.fragment_knowledge) {

    private lateinit var mAdapter: KnowledgeFragmentAdapter

    override fun initView() {
        mViewBind.recycleView.layoutManager = LinearLayoutManager(mContext)
        mAdapter = KnowledgeFragmentAdapter()
        mAdapter.setOnItemClickListener { adapter, view, position ->

        }
        mViewBind.recycleView.adapter = mAdapter
    }

    override fun initEvent() {
        mViewModel.knowledgeListEntityLiveData.observe(this){
            mAdapter.setNewInstance(it)
        }
    }

    override fun initData() {
        mViewModel.getKnowledgeList()
    }
}