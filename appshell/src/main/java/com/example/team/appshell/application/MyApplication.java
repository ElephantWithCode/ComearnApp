package com.example.team.appshell.application;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.team.commonlibrary.base.BaseApplication;

/**
 * Created by Ellly on 2018/3/19.
 * @author Ellly
 */

public class MyApplication extends BaseApplication {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init( this ); // 尽可能早，推荐在Application中初始化
    }
    /**
     * 获取context
     */
    public static Context getGlobalContext() {
        return mContext;
    }


}
