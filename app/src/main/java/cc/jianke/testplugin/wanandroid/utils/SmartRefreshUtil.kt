package cc.jianke.testplugin.wanandroid.utils

import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 * @Author: wlf
 * @CreateDate: 2022/4/13 17:30
 * @Description:
 */
object SmartRefreshUtil {

    const val NORMAL = 0
    const val REFRESH = 1
    const val LOAD_MORE = 2
    const val PAGE_SIZE = 20

    fun <T> setSmartRefreshStatus(refreshLayout: SmartRefreshLayout,
                              adapter: BaseQuickAdapter<T, *>,
                              data: MutableList<T>,
                              status: Int,
                              pageSize: Int = PAGE_SIZE) {
        when(status) {
            NORMAL -> {
                adapter.setNewInstance(data)
            }
            REFRESH -> {
                adapter.setNewInstance(data)
                refreshLayout.finishRefresh()
            }
            LOAD_MORE -> {
                adapter.addData(data)
                refreshLayout.finishLoadMore()
            }
        }
        val isCanLoadMore = data.isNullOrEmpty() || data.size < pageSize
//        refreshLayout.setEnableLoadMore(!isCanLoadMore)
        refreshLayout.setNoMoreData(isCanLoadMore)
    }
}