package com.tangedegushi.mvphelper.global;

import android.app.Application;
import android.os.Build;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tangedegushi.mvphelper.global.util.AppActivityLifecycleCallback;

/**
 * Created by zq on 2017/8/19.
 */

public class MyApplication extends Application {

    private static MyApplication application;
    private AppActivityLifecycleCallback lifeCallback;

    public static MyApplication getInstence(){return application;}

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Logger.addLogAdapter(new AndroidLogAdapter());
        //捕获异常处理
        CrashHandle.getInstance().init(this);
        //注册activity堆栈管理
        registerActivityLifecycleCallbacks();
    }

    private void registerActivityLifecycleCallbacks() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH && lifeCallback == null){
            lifeCallback = new AppActivityLifecycleCallback();
            registerActivityLifecycleCallbacks(lifeCallback);
        }
    }

    public void unRegisterActivityLifecycleCallbacks(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH && lifeCallback != null){
            unregisterActivityLifecycleCallbacks(lifeCallback);
        }
    }
}
