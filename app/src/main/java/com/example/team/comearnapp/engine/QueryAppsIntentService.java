package com.example.team.comearnapp.engine;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.team.comearnapp.R;
import com.example.team.comearnlib.utils.ToastTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellly on 2017/11/28.
 */

public class QueryAppsIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    private static final String TAG = "QAIS";
    private static final String APP_LIST_NAMES_SHARED = "APP_LIST_NAME_SHARED";
    public static final String APP_LIST_NAMES = "APP_LIST_NAME";
    private SharedPreferences mSavedPreferences;
    public QueryAppsIntentService() {
        super("com.example.engine.query_apps_intent_service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSavedPreferences = getSharedPreferences(APP_LIST_NAMES_SHARED, 0);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "OnHandleIntent");
        ArrayList<String> names = queryAppsList();
        Intent sendNamesIntent = new Intent();
        sendNamesIntent.setAction(getString(R.string.WYY_getListAction));
        sendNamesIntent.putStringArrayListExtra(APP_LIST_NAMES, names);
        sendBroadcast(sendNamesIntent);
    }

    private ArrayList<String> queryAppsList() {
//        mEditor = mSavedPreferences.edit();
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
