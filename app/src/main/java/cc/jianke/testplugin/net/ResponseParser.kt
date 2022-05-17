package cc.jianke.testplugin.net

import cc.jianke.testplugin.wanandroid.enum.LoginEnum
import cc.jianke.testplugin.wanandroid.event.LoginEvent
import cc.jianke.testplugin.wanandroid.utils.ARouterPath
import cc.jianke.testplugin.wanandroid.utils.UserUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.jeremyliao.liveeventbus.LiveEventBus
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
@Parser(name = "Response", wrappers = [BaseListResponse::class])
open class ResponseParser<T>: TypeParser<T> {

    //以下两个构造方法是必须的
    protected constructor() : super()
    constructor(type: Type) : super(type)

    override fun onParse(response: Response): T {
        val data: BaseResponse<T> = response.convertTo(BaseResponse::class, *types)
        //获取data字段
        var t = data.data
        if (t == null && types[0] == String::class.java) {
            /*
             * 考虑到有些时候服务端会返回：{"errorCode":0,"errorMsg":"关注成功"}  类似没有data的数据
             * 此时code正确，但是data字段为空，直接返回data的话，会报空指针错误，
             * 所以，判断泛型为String类型时，重新赋值，并确保赋值不为null
             */
            @Suppress("UNCHECKED_CAST")
            t = data.errorMsg as T
        }
        //未登录
        if (data.errorCode == -1001) {
            UserUtil.setLoginStatus(false)
            LiveEventBus.get(LoginEvent::class.java).post(LoginEvent(LoginEnum.LOGIN_OUT))
            ARouter.getInstance().build(ARouterPath.LOGIN_ACTIVITY).navigation()
            throw ParseException("${data.errorCode}", data.errorMsg, response)
        }
        //code不等于200，说明数据不正确，抛出异常
        if (data.errorCode != 0 || t == null) {
            throw ParseException("${data.errorCode}", data.errorMsg, response)
        }
        return t
    }
}