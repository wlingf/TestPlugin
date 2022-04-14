package cc.jianke.testplugin.wanandroid.adapter

import cc.jianke.testplugin.R
import cc.jianke.testplugin.wanandroid.entity.MyAdapterEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: wlf
 * @CreateDate: 2022/4/14 17:20
 * @Description:
 */
class MyFragmentAdapter: BaseQuickAdapter<MyAdapterEntity, BaseViewHolder>(R.layout.item_my_adapter) {

    override fun convert(holder: BaseViewHolder, item: MyAdapterEntity) {
        holder.setText(R.id.tv_title, item.title)
            .setText(R.id.tv_desc, item.desc)
            .setGone(R.id.tv_desc, item.desc.isNullOrEmpty())
    }
}