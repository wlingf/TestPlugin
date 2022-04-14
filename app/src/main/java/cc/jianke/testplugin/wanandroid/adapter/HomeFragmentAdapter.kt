package cc.jianke.testplugin.wanandroid.adapter

import cc.jianke.testplugin.R
import cc.jianke.testplugin.wanandroid.entity.ArticleEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: wlf
 * @CreateDate: 2022/4/13 16:58
 * @Description:
 */
class HomeFragmentAdapter: BaseQuickAdapter<ArticleEntity, BaseViewHolder>(R.layout.item_home_adapter) {

    override fun convert(holder: BaseViewHolder, item: ArticleEntity) {
        holder.setText(R.id.tv_title, item.title)
            .setText(R.id.tv_time, item.niceDate)
            .setImageResource(R.id.iv_collection, if (item.isCollection) R.mipmap.icon_collection else R.mipmap.icon_un_collection)
    }
}