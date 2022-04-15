package cc.jianke.testplugin.wanandroid.adapter

import cc.jianke.testplugin.R
import cc.jianke.testplugin.wanandroid.entity.ArticleEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 14:15
 * @Description:
 */
class CollectAdapter: BaseQuickAdapter<ArticleEntity, BaseViewHolder>(R.layout.item_home_adapter) {

    override fun convert(holder: BaseViewHolder, item: ArticleEntity) {
        holder.setText(R.id.tv_title, item.title)
            .setText(R.id.tv_time, item.desc)
            .setImageResource(R.id.iv_collection, R.mipmap.icon_collection)
    }
}