package com.example.team.commonlibrary.base;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;

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
    }
    /**
     * 获取context
     */
    public static Context getGlobalContext() {
        return mContext;
    }


}

