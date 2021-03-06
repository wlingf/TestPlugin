package cc.jianke.testplugin.wanandroid.entity

/**
 * @Author: wlf
 * @CreateDate: 2022/4/2 16:56
 * @Description:
 */
data class ArticleListEntity(val curPage: Int = 0) {

    var datas: MutableList<ArticleEntity> = mutableListOf()

    var offset = 0

    var over = false

    var pageCount = 0

    var size = 0

    var total = 0
}