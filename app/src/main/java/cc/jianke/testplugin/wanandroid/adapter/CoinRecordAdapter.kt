package cc.jianke.testplugin.wanandroid.adapter

import cc.jianke.testplugin.R
import cc.jianke.testplugin.wanandroid.entity.CoinEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 10:47
 * @Description:
 */
class CoinRecordAdapter: BaseQuickAdapter<CoinEntity, BaseViewHolder>(R.layout.item_coin_record_adapter) {

    override fun convert(holder: BaseViewHolder, item: CoinEntity) {
        holder.setText(R.id.tv_coin_reason, item.reason)
            .setText(R.id.tv_coin_desc, item.desc)
            .setText(R.id.tv_coin_count, "${item.coinCount}")
    }
}