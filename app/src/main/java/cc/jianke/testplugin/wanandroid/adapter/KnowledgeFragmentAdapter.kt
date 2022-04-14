package cc.jianke.testplugin.wanandroid.adapter

import android.widget.TextView
import cc.jianke.testplugin.R
import cc.jianke.testplugin.wanandroid.entity.KnowledgeListEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: wlf
 * @CreateDate: 2022/4/14 11:04
 * @Description:
 */
class KnowledgeFragmentAdapter: BaseQuickAdapter<KnowledgeListEntity, BaseViewHolder>(R.layout.item_knowledge_adapter) {

    override fun convert(holder: BaseViewHolder, item: KnowledgeListEntity) {
        holder.setText(R.id.tv_title, item.name)
        val tvChildren = holder.getView<TextView>(R.id.tv_children_title)
        var childrenTitle = ""
        item.children.forEach {
            childrenTitle += "${it.name}   "
        }
        tvChildren.text = childrenTitle
    }
}