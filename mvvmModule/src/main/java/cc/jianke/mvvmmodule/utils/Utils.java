package cc.jianke.mvvmmodule.utils;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cc.jianke.mvvmmodule.mvvm.BaseViewModel;

/**
 * @Author: wlf
 * @CreateDate: 2022/4/12 13:56
 * @Description:
 */
public class Utils {

    public static BaseViewModel getViewModel(ViewModelStoreOwner owner) {
        BaseViewModel viewModel = null;
        //当前对象超类的Type
        ParameterizedType type = (ParameterizedType) owner.getClass().getGenericSuperclass();
        //ParameterizedType表示参数化的类型
        if (type != null && type instanceof ParameterizedType) {
            //返回此类型实际类型参数的Type对象数组
            Type[] actualTypeArguments = type.getActualTypeArguments();
            for (int i = 0; i < actualTypeArguments.length; i++) {
                Class cls = (Class) actualTypeArguments[i];
                if (cls.getSuperclass().toString().equals(BaseViewModel.class.toString())) {
                    viewModel = (BaseViewModel) new ViewModelProvider(owner, new ViewModelProvider.NewInstanceFactory()).get((Class) actualTypeArguments[i]);
                }
            }
        }
        if (viewModel == null) {
            throw new NullPointerException("viewModel is null, please create viewModel");
        }
        return viewModel;
    }
}
