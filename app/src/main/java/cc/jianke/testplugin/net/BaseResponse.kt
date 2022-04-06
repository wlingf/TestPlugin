package cc.jianke.testplugin.net

/**
 * @Author: wlf
 * @CreateDate: 2022/4/2 16:44
 * @Description:
 */
class BaseResponse<T> {

    var errorCode = 0

    var errorMessage = ""

    var data: T? = null
}