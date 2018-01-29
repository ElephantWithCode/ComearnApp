package com.example.team.comearnapp.test;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wenming.library.BackgroundUtil;

import java.util.ArrayList;

/**
 * Created by Ellly on 2018/1/24.
 */

public class PackageNameInListDetector {

    private static final String TAG = "DET";

    private boolean mBackMode = true;
    private ArrayList<String> mPackagesList;
    private Context mContext;
    private boolean mDetectMode = true;

    public PackageNameInListDetector(ArrayList<String> targetList){
        mPackagesList = targetList;
    }

    public PackageNameInListDetector(){}


    public void monitor(){
        if (mContext == null){
            throw new RuntimeException("mContext must exists. (the detector must be attached)");
        }
        if (!getAppStatus()){
            if (mDetectMode) {
                executeWithdraw();
            } else {

            }
        }
    }

    private void executeWithdraw() {
        if (mBackMode){
            turnToAppItself();
        }else {
            turnToDesktop();
        }
    }


    /**
     * 获得当前检测模式
     * @return 当前模式。
     */
    public boolean getMode(){
        return mDetectMode;
    }
    /**
     * 改变当前模式（即对应代码功能的回到桌面还是回到应用）
     */
    public void switchMode(){
        mBackMode = !mBackMode;
    }
    /**
     * 回到桌面。
     */
    private void turnToDesktop(){
        //TODO:回到桌面的代码
    }
    /**
     * 回到应用界面
     */
    private void turnToAppItself(){
        mContext.startActivity(new Intent(mContext, MockActivity.class));
    }

    /**
     *
     * @return true 表示检测的应用之一在前台，否则均不在前台。
     */
    private boolean getAppStatus(){
        boolean isForeground = false;
        for (String packageName : mPackagesList){
            Log.d(TAG + "_PACK_O", packageName);
            Log.d(TAG + "_PACK", mContext.getPackageName());
            isForeground = isForeground || BackgroundUtil.isForeground(mContext, 5, packageName);//3可以检测其他包名的App
            Log.d(TAG + "_SIN", "检测:    " + packageName + "  " + isForeground);
        }
        Log.d(TAG + "_OTH", "检测其他的App的结果:   " + isForeground);
        isForeground = isForeground || BackgroundUtil.isForeground(mContext, 5, mContext.getPackageName());
        Log.d(TAG + "_SIN", "检测自己的结果:   " + BackgroundUtil.isForeground(mContext, 5, mContext.getPackageName()));
        Log.d(TAG + "_NUM", "最终的检测结果:   " + isForeground);

        return isForeground;
    }

    public void setToDetectList(ArrayList<String> targetList){
        mPackagesList = targetList;
    }

    public void attach(Context context){
        mContext = context;
    }
    public void detach(){
        mContext = null;
    }


}
