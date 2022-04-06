package cc.jianke.testplugin.net

import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.convertTo
import java.lang.reflect.Type

/**
 * @Author: wlf
 * @CreateDate: 2022/4/2 16:48
 * @Description:
 */
@Parser(name = "Response")
open class ResponseParser<T>: TypeParser<T> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    override fun onParse(response: Response): T {
        val data: BaseResponse<T> = response.convertTo(BaseResponse::class, *types)
        //获取data字段
        val t = data.data
        //code不等于200，说明数据不正确，抛出异常
        if (data.errorCode != 0 || t == null) {
            throw ParseException(data.errorCode.toString(), data.errorMessage, response)
        }
        return t
    }
}