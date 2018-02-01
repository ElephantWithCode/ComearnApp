package com.example.team.monitorlib.components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellly on 2018/1/29.
 */

class AppInfoObtainer {

    public static final String TAG = "AIO";
    private boolean mAttached = false;
    private ArrayList<PackageInfo> mPackageInfoList;
    private Context mContext;
    private PackageInformationExtractor mExtractor = new PackageInformationExtractor();

    @Deprecated
    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPackageInfoList = intent.getParcelableArrayListExtra(MonitorService.GET_LIST_ACTION);
            mExtractor = new PackageInformationExtractor(mPackageInfoList);
        }
    };

    public AppInfoObtainer(){}

    public AppInfoObtainer(Context context){
        attach(context);
    }

    public ArrayList<PackageInfo> queryPackageInfoList(){
        List<PackageInfo> lst = mContext.getPackageManager().getInstalledPackages(0);
        ArrayList<PackageInfo> aLst = new ArrayList<>();
        aLst.addAll(lst);
        return aLst;
    }

    public void attach(Context context){
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mAttached = true;
//        registerReceiver(mUpdateReceiver, mContext.getString(R.string.WYY_getListAction)); 弃用Service
    }

    public void detach(){
//        mContext.unregisterReceiver(mUpdateReceiver);
        mAttached = false;
        mContext = null;
    }

    public void startQuery(){
//        mContext.startService(new Intent(mContext, QueryAppsIntentService.class));
        mExtractor.setToExtractList(queryPackageInfoList());
    }

    public ArrayList<String> getAppsNames(){
        if (checkExtractor()) return null;
        return mExtractor.getAppNames(mContext.getPackageManager());
    }

    public ArrayList<Drawable> getAppIcons(){
        if (checkExtractor()) return null;
        return mExtractor.getAppIcons(mContext.getPackageManager());
    }

    public ArrayList<String> getPackageNames(){
        if (checkExtractor()) return null;
        return mExtractor.getPackageNames();
    }
    private boolean checkExtractor() {
        if (mExtractor == null){
            Log.e(TAG, "mExtractor must be initialized");
            return true;
        }
        return false;
    }

    public void registerReceiver(BroadcastReceiver receiver, String action){
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(action);
        mContext.registerReceiver(receiver, iFilter);
    }

    public void switchQueryScope(){
        mExtractor.switchScope();
    }

}
