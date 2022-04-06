package cc.jianke.pluginmodule

import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @Author: wlf
 * @CreateDate: 2022/3/14 14:43
 * @Description:
 */
class PluginEventManager private constructor(){

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = PluginEventManager()
    }

    fun postAcrossApp(event: PluginMessageEvent){
        LiveEventBus.get<PluginMessageEvent>(PluginEventConst.PLUGIN_EVENT)
            .postAcrossApp(event)
    }
}