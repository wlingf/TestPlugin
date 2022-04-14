package cc.jianke.testplugin.wanandroid.event

import cc.jianke.testplugin.wanandroid.enum.LoginEnum
import com.jeremyliao.liveeventbus.core.LiveEvent

/**
 * @Author: wlf
 * @CreateDate: 2022/4/14 17:44
 * @Description:
 */
class LoginEvent constructor(val loginEnum: LoginEnum): LiveEvent{
}