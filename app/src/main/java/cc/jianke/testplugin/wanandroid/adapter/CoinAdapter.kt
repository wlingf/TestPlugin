package cc.jianke.testplugin.wanandroid.adapter

import cc.jianke.testplugin.R
import cc.jianke.testplugin.wanandroid.entity.CoinEntity
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 10:02
 * @Description:
 */
class CoinAdapter: BaseMultiItemQuickAdapter<CoinEntity, BaseViewHolder>() {

    init {
        addItemType(CoinEntity.TYPE_LIST, R.layout.item_coin_adapter)
        addItemType(CoinEntity.TYPE_RECORD, R.layout.item_coin_record_adapter)
    }

    override fun convert(holder: BaseViewHolder, item: CoinEntity) {
        when(holder.itemViewType) {
            CoinEntity.TYPE_LIST -> {
                holder.setText(R.id.tv_user_name, item.username)
                    .setText(R.id.tv_coin, "${item.coinCount}")
            }
            CoinEntity.TYPE_RECORD ->{
                holder.setText(R.id.tv_coin_reason, item.reason)
                    .setText(R.id.tv_coin_desc, item.desc)
                    .setText(R.id.tv_coin_count, "${item.coinCount}")
            }
        }
    }
}