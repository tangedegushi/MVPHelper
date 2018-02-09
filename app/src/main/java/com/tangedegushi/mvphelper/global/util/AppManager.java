package com.tangedegushi.mvphelper.global.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by zq on 2017/8/19.
 */

public class AppManager {

    private static Stack<Activity> appStack;
    private static AppManager manager;

    public static AppManager newInstance(){
        if (manager == null){
            synchronized (AppManager.class){
                if (manager == null){
                    appStack = new Stack<>();
                    manager = new AppManager();
                }
            }
        }
        return manager;
    }

    /**
     * @param activity 添加Activity到堆栈
     */
    public void addActivity(Activity activity){
        appStack.add(activity);
    }

    /**
     * @return 获取当前Activity
     */
    public Activity currentActivity(){
        return appStack.lastElement();
    }

    /**
     * 结束当前Activity
     */
    public void finishCurrentActivity(){
        appStack.pop().finish();
    }

    /**
     * @param activity 结束指定activity
     */
    public void finishActivity(Activity activity){
        if (activity != null){
            if (appStack.remove(activity)){
                activity.finish();
            }
        }
    }

    public void finishActivity(Class<?> cls){
        for (Activity activity : appStack) {
            if (activity.getClass().equals(cls)){
                finishActivity(activity);
                return;
            }
        }
    }

    public void finishAllActivity(){
        for (Activity activity : appStack) {
            activity.finish();
        }
        appStack.clear();
    }

    public void appExit(Context context){
        try {
            finishAllActivity();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        }catch (Exception e){

        }
    }
}
