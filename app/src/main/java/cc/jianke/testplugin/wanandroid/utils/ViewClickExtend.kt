package cc.jianke.testplugin.wanandroid.utils

import android.view.View
import com.blankj.utilcode.util.ClickUtils

/**
 * @Author: wlf
 * @CreateDate: 2022/4/27 14:01
 * @Description: View 点击事件扩展
 */

/**
 * View 防抖点击
 * @param block 事件回调
 * @param duration 防抖时间
 */
fun View.setThrottleListener(block: () -> Unit, duration: Long = 1000){
    ClickUtils.applySingleDebouncing(this, duration){
        block.invoke()
    }
}

/**
 * View 防抖点击
 * @param clickListener 事件回调
 * @param duration 防抖时间
 */
fun View.setThrottleListener(clickListener: View.OnClickListener, duration: Long = 1000) {
    ClickUtils.applySingleDebouncing(this, duration, clickListener)
}