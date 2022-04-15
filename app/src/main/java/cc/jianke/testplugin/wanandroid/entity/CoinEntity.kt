package cc.jianke.testplugin.wanandroid.entity

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @Author: wlf
 * @CreateDate: 2022/4/15 9:45
 * @Description:
 */
data class CoinEntity(val coinCount: Long,
                      val username: String,
                      val level: Long,
                      val userId: Long,
                      val rank: String,
                      val reason: String,
                      val desc: String,
                      override var itemType: Int = TYPE_LIST): MultiItemEntity {

                          companion object {
                              const val TYPE_LIST = 0
                              const val TYPE_RECORD = 1
                          }
                      }
