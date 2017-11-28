package com.example.team.comearnapp.engine;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.example.team.comearnapp.test.MockActivity;
import com.wenming.library.BackgroundUtil;
import com.wenming.library.processutil.ProcessManager;
import com.wenming.library.processutil.models.AndroidAppProcess;

import java.util.List;

public class MonitorService extends Service {

    private static final String TAG = "Monitor";
    public static final String RECEIVE = "com.example.train";
    private Context mContext;
    private List<AndroidAppProcess> mProcesses;
    private boolean mStopSignal = true;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mStopSignal = false;
        }
    };

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mStopSignal){
                    Log.d(TAG, getAppStatus() + "");
                    if (!getAppStatus()){
                        startActivity(new Intent(mContext, MockActivity.class));
                    }
                }
            }
        }).start();
        return START_STICKY;
    }

    private boolean getAppStatus() {
        return BackgroundUtil.isForeground(mContext, 5, mContext.getPackageName());
    }

    public MonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
