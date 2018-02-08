package com.example.team.monitorlib.components;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.support.annotation.Nullable;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellly on 2017/11/28.
 */
@Deprecated
class QueryAppsIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private static final String TAG = "QAIS";
    private static final String APP_LIST_NAMES_SHARED = "APP_LIST_NAME_SHARED";
    public static final String APP_LIST_NAMES = "APP_LIST_NAME";
    private static final String GET_LIST_ACTION = "GetListAction";
    public QueryAppsIntentService() {
        super("com.example.engine.query_apps_intent_service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG + "_OHI", "OnHandleIntent");
/*
        ArrayList<String> names = queryAppsList();
        Intent sendPackageInfoIntent = new Intent();
        sendPackageInfoIntent.setAction(GET_LIST_ACTION);
        sendPackageInfoIntent.putStringArrayListExtra(APP_LIST_NAMES, names);
        sendBroadcast(sendPackageInfoIntent);
*/
        //这里把之前直接返回包名的操作改成了返回PackageInfo的操作
        ArrayList<PackageInfo> infos = queryPackageInfoList();
        Intent sendPackageInfoIntent = new Intent();
        sendPackageInfoIntent.setAction(GET_LIST_ACTION);
        sendPackageInfoIntent.putParcelableArrayListExtra(APP_LIST_NAMES, infos);
        sendBroadcast(sendPackageInfoIntent);
    }

    private ArrayList<PackageInfo> queryPackageInfoList(){
        List<PackageInfo> lst = getPackageManager().getInstalledPackages(0);
        ArrayList<PackageInfo> aLst = new ArrayList<>();
        aLst.addAll(lst);
        return aLst;
    }
    @Deprecated
    private ArrayList<String> queryAppsList() {
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) ==  0) {
                names.add(packageInfo.packageName);
//                packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();  可以获取应用名称
//                packageInfo.applicationInfo.loadIcon(getPackageManager()); 可以获得应用图标
                Log.d(TAG+"_NAME", packageInfo.packageName);
            }
        }
        return names;
    }
}
