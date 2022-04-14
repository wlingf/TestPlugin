package cc.jianke.testplugin.net

import com.google.gson.annotations.SerializedName

/**
 * @Author: wlf
 * @CreateDate: 2022/4/14 14:55
 * @Description:
 */
class BaseListResponse<T> {

    var errorCode = 0

    var errorMsg = ""

    @SerializedName(value = "datas", alternate = ["data"])
    var datas: MutableList<T>? = null
}