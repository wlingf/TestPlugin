package cc.jianke.pluginmodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


/**
 * @Author: wlf
 * @CreateDate: 2022/3/11 13:32
 * @Description: 插件Activity的基类，插件中的所有Activity，都要继承它
 */
public abstract class PluginBaseActivity extends AppCompatActivity implements IPlugin {
    private static String TAG = "PluginBaseActivity";

    protected Activity proxy;

    protected String mPluginKey;

    // 这里基本上都在重写原本Activity的函数，因为 要兼容“插件单独测试” 和 "集成到宿主整体测试",所以要进行情况区分
    // 默认是“插件单独测试
    private int mPluginFrom = IPlugin.FROM_INTERNAL;

    @Override
    public void attach(@NonNull Activity activity) {
        proxy = activity;
    }

    @Override
    public void bindPluginKey(@NonNull String pluginKey) {
        mPluginKey = pluginKey;
    }

    @Override
    public void onCreate(@NonNull Bundle saveInstanceState) {
        if (saveInstanceState != null) {
            mPluginFrom = saveInstanceState.getInt(PluginConst.TAG_FROM);
        }
        if (mPluginFrom == IPlugin.FROM_INTERNAL){
            super.onCreate(saveInstanceState);
            proxy = this;
        }
    }

    @Override
    public void onStart() {
        if (mPluginFrom == IPlugin.FROM_INTERNAL) {
            super.onStart();
        } else {
            Log.e(TAG, "宿主启动：onStart()");
        }
    }

    @Override
    public void onResume() {
        if (mPluginFrom == IPlugin.FROM_INTERNAL) {
            super.onResume();
        } else {
            Log.e(TAG, "宿主启动：onResume()");
        }
    }

    @Override
    public void onRestart() {
        if (mPluginFrom == IPlugin.FROM_INTERNAL) {
            super.onRestart();
        } else {
            Log.e(TAG, "宿主启动：onRestart()");
        }
    }

    @Override
    public void onPause() {
        if (mPluginFrom == IPlugin.FROM_INTERNAL) {
            super.onPause();
        } else {
            Log.e(TAG, "宿主启动：onPause()");
        }
    }

    @Override
    public void onStop() {
        if (mPluginFrom == IPlugin.FROM_INTERNAL) {
            super.onStop();
        } else {
            Log.e(TAG, "宿主启动：onStop()");
        }
    }

    @Override
    public void onDestroy() {
        if (mPluginFrom == IPlugin.FROM_INTERNAL) {
            super.onDestroy();
        } else {
            Log.e(TAG, "宿主启动：onDestroy()");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent intent) {
        if (mPluginFrom == IPlugin.FROM_INTERNAL) {
            super.onActivityResult(requestCode, resultCode, intent);
        } else {
            Log.e(TAG, "宿主启动：onActivityResult()");
        }
    }

    /**************************** 下面是几个生命周期之外的重写函数   ************************************/

    @Override
    public void setContentView(int layoutResID) {
        if (mPluginFrom == IPlugin.FROM_INTERNAL) {
            super.setContentView(layoutResID);
        } else {
            proxy.setContentView(layoutResID);
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (mPluginFrom == IPlugin.FROM_INTERNAL) {
            return super.findViewById(id);
        }else{
            return proxy.findViewById(id);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (mPluginFrom == IPlugin.FROM_INTERNAL) {
            super.startActivity(intent);
        }else{
            // 如果是集成模式下，插件内的跳转，控制权 仍然是在宿主上下文里面，所以--!
            // 先跳到代理Activity，由代理Activity展示真正的Activity内容
            PluginManager.Companion.getInstance().toActivity(proxy, mPluginKey, intent.getComponent().getClassName(),
                    intent.getExtras() == null ? new Bundle() : intent.getExtras());
        }
    }
}
