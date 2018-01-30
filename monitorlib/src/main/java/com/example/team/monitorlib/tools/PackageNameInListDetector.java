package com.example.team.monitorlib.tools;

import android.content.Context;
import android.util.Log;

import com.example.team.monitorlib.AppMonitor;
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
    private AppMonitor.DetectListener mAfterDetectListener;

    public PackageNameInListDetector(ArrayList<String> targetList){
        mPackagesList = targetList;
    }

    public PackageNameInListDetector(){}

    public void monitor(){
        if (mContext == null){
            throw new RuntimeException("mContext must exists. (the detector must be attached)");
        }
        if (!getAppStatus()){
            executeWithdraw();
        }
    }

    private void executeWithdraw() {
        try {
            mAfterDetectListener.afterDetect();
        }catch (NullPointerException e){
            Log.e(TAG + "_E", "listener must be implemented");
        }
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
            boolean b = BackgroundUtil.isForeground(mContext, 3, packageName);
            Log.d(TAG + "_SIN", "检测:    " + packageName + "  " + b);
            isForeground = isForeground || b;//3可以检测其他包名的App
        }
        Log.d(TAG + "_OTH", "检测其他的App的结果:   " + isForeground);
        isForeground = isForeground || BackgroundUtil.isForeground(mContext, 3, mContext.getPackageName());
        Log.d(TAG + "_SIN", "检测自己的结果:   " + BackgroundUtil.isForeground(mContext, 3, mContext.getPackageName()));
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

    public void setAfterDetectListener(AppMonitor.DetectListener l){
        mAfterDetectListener = l;
    }


}
