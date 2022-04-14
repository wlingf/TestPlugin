package cc.jianke.testplugin.wanandroid.entity

import cc.jianke.testplugin.net.BaseEntity
import com.google.gson.annotations.SerializedName

/**
 * @Author: wlf
 * @CreateDate: 2022/4/2 16:56
 * @Description:
 */
class ArticleEntity: BaseEntity() {

    var apkLink = ""

    var author = ""

    var link = ""

    var title = ""

    var id = 0

    var niceDate = ""

    @SerializedName("collect")
    var isCollection = false
}