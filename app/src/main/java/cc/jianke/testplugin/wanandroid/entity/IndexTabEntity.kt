package cc.jianke.testplugin.wanandroid.entity

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * @Author: wlf
 * @CreateDate: 2022/4/13 15:18
 * @Description:
 */
data class IndexTabEntity(val title: String, val selectIcon: Int, val unSelectIcon: Int): CustomTabEntity {

    override fun getTabTitle(): String = title

    override fun getTabSelectedIcon(): Int = selectIcon

    override fun getTabUnselectedIcon(): Int = unSelectIcon
}