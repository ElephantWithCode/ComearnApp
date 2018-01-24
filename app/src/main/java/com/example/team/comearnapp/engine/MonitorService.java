package com.example.team.comearnapp.engine;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.LinearGradient;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.team.comearnapp.test.MockActivity;
import com.example.team.comearnapp.test.MockPresenter;
import com.wenming.library.BackgroundUtil;
import com.wenming.library.processutil.ProcessManager;
import com.wenming.library.processutil.models.AndroidAppProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MonitorService extends Service {


    private static final long DETECT_DELAY_MILL = 1000;

    private class ReturnHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLER_MARK_MONITOR) {
                monitorAndReturn();
                if (mStopSignal){
                    sendEmptyMessageDelayed(HANDLER_MARK_MONITOR, DETECT_DELAY_MILL);
                    Log.d(TAG + "_HAN", "EmptyMessageReceived");
                }
            }
        }
    }
    private static int HANDLER_MARK_MONITOR = -1;
    private static final String TAG = "Monitor";
    public static final String RECEIVE = "com.example.train";
    private Context mContext;
    private List<AndroidAppProcess> mProcesses;
    private Set<String> nameSet;
    private boolean mStopSignal = true;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mStopSignal = false;
        }
    };
    private ArrayList<String> mAppNameList = new ArrayList<>();
    private ReturnHandler mReturnHandler = new ReturnHandler();

    @Override
    public void onCreate() {
        super.onCreate();
        registerServiceStopReceiver();
        Log.d(TAG, "Created");
        mContext = this;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void registerServiceStopReceiver() {
        IntentFilter broadCastFilter = new IntentFilter();
        broadCastFilter.addAction(RECEIVE);
        registerReceiver(mReceiver, broadCastFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "startCommanded");
        /*  使用了SharedPreference存储
        SharedPreferences sp = getSharedPreferences(MockPresenter.SP_TAG, 0);
        nameSet = sp.getStringSet(MockPresenter.APP_NAME_TAG, null);
        assert nameSet != null;
        for (String s : nameSet){
            Log.d(TAG, s);
        }
        */
        mAppNameList = intent.getStringArrayListExtra(QueryAppsIntentService.APP_LIST_NAMES);

        Log.d(TAG+"_Size", mAppNameList.size() + "");

        new Thread(new Runnable() {
            @Override
            public void run() {
                mReturnHandler.sendEmptyMessage(HANDLER_MARK_MONITOR);
            }
        }).start();
        return START_STICKY;
    }

    private void monitorAndReturn() {
        Log.d(TAG, getAppStatus() + "");
        if (getAppStatus()){
            startActivity(new Intent(mContext, MockActivity.class));
        }
    }

    private boolean getAppStatus() {
        boolean isForeground = false;
        for (String packageName : mAppNameList){
            Log.d(TAG + "_PACK", packageName);
            Log.d(TAG + "_PACK", mContext.getPackageName());
            isForeground = isForeground || BackgroundUtil.isForeground(mContext, 3, packageName);//3可以检测其他包名的App
            Log.d(TAG + "_NUM", "HERE!!!!" + isForeground);
        }
        return isForeground;
//        return BackgroundUtil.isForeground(mContext, 5, mContext.getPackageName());
    }

    public MonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
