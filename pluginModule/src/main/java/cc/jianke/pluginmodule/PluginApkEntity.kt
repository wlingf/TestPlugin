package cc.jianke.pluginmodule

import android.content.pm.PackageInfo
import android.content.res.Resources
import dalvik.system.DexClassLoader

/**
 * @Author: wlf
 * @CreateDate: 2022/3/14 16:13
 * @Description:
 */
class PluginApkEntity {

    var packageInfo: PackageInfo? = null

    var dexClassLoader: DexClassLoader? = null

    var resources: Resources? = null
}