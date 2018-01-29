package com.example.team.comearnapp.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.util.Log;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.engine.QueryAppsIntentService;
import com.example.team.comearnlib.utils.ToastTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellly on 2018/1/29.
 */

public class AppInfoObtainer {

    public static final String TAG = "AIO";
    private boolean mAttached = false;
    private ArrayList<PackageInfo> mPackageInfoList;
    private Context mContext;
    private PackageInformationExtractor mExtractor;
    private BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPackageInfoList = intent.getParcelableArrayListExtra(QueryAppsIntentService.APP_LIST_NAMES);
            mExtractor = new PackageInformationExtractor(mPackageInfoList);
        }
    };

    public AppInfoObtainer(){}

    public AppInfoObtainer(Context context){
        mContext = context;
        mAttached = true;
        registerReceiver(mUpdateReceiver, mContext.getString(R.string.WYY_getListAction));
    }

    private ArrayList<PackageInfo> queryPackageInfoList(){
        List<PackageInfo> lst = mContext.getPackageManager().getInstalledPackages(0);
        ArrayList<PackageInfo> aLst = new ArrayList<>();
        aLst.addAll(lst);
        return aLst;
    }

    public void attach(Context context){
        mContext = context;
        mAttached = true;
        registerReceiver(mUpdateReceiver, mContext.getString(R.string.WYY_getListAction));
    }

    public void detach(){
        mContext.unregisterReceiver(mUpdateReceiver);
        mAttached = false;
        mContext = null;
    }

    public void startQuery(){
//        mContext.startService(new Intent(mContext, QueryAppsIntentService.class));
        mExtractor = new PackageInformationExtractor(queryPackageInfoList());
    }

    public ArrayList<String> getAppsNames(){
        if (checkExtractor()) return null;
        return mExtractor.getAppNames(mContext.getPackageManager());
    }

    public ArrayList<String> getPackageNames(){
        if (checkExtractor()) return null;
        return mExtractor.getPackageNames();
    }

    private boolean checkExtractor() {
        if (mExtractor == null){
            Log.d(TAG, "mExtractor must be initialized");
            return true;
        }
        return false;
    }

    public void registerReceiver(BroadcastReceiver receiver, String action){
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(action);
        mContext.registerReceiver(receiver, iFilter);
    }


}
