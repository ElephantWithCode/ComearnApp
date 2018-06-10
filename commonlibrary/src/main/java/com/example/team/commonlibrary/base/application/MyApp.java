package com.example.team.commonlibrary.base.application;

import android.app.ActivityManager;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.team.commonlibrary.base.application.BaseApplication;

import io.rong.imkit.RongIM;

/**
 * Created by Ellly on 2018/3/19.
 * @author Ellly
 */

public class MyApp extends BaseApplication {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        // 打印日志
        ARouter.openLog();
        // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.openDebug();
        // 尽可能早，推荐在Application中初始化
        ARouter.init( this );
        //保证只有是融云需要进程才进行初始化
//        if(getApplicationInfo().packageName.equals(MyApp.getCurProcessName(mContext))||"io.rong.push".equals(MyApp.getCurProcessName(mContext))){
            RongIM.init(this);
//        }
    }
    /**
     * 获取context
     */
    public static Context getGlobalContext() {
        return mContext;
    }
    /**
     * 获得当前进程的名字
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }

}

