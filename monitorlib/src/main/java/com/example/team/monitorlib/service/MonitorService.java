package com.example.team.monitorlib.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.team.monitorlib.AppMonitor;
import com.example.team.monitorlib.tools.PackageNameInListDetector;
import com.wenming.library.BackgroundUtil;

import java.util.ArrayList;

public class MonitorService extends Service {

    public class CallbackBinder extends Binder{

        public MonitorService getService(){return MonitorService.this;}
    }
    private class ReturnHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == HANDLER_MARK_MONITOR) {
                if (mStopSignal){
                    mDetector.monitor();
                    sendEmptyMessageDelayed(HANDLER_MARK_MONITOR, DETECT_DELAY_MILL);
                    Log.d(TAG + "_HAN", "EmptyMessageReceived");
                }
            }
        }
    }

    public static final String GET_LIST_ACTION= "APP_LIST_NAME";    //SharedPreferences的标识String。
    private static final long DETECT_DELAY_MILL = 2000;             //轮询检测前台应用的间隔。
    private static final int FOREGROUND_SERVICE_MARK = 110;         //启动前台通知的标识。
    private static int HANDLER_MARK_MONITOR = -1;                   //轮询Handler的what值。
    private static final String TAG = "Monitor";                    //此类的DebugTag。
    public static final String RECEIVE = "com.example.train";       //用于注册关闭服务的广播标识。
    public static final String RECEIVE_CLOSE = "RECEIVE_CLOSE";     //用于关闭服务的Broadcast的IntentFilter。

    private CallbackBinder mBinder = new CallbackBinder();          //为了获得Detector的回调方法而写的Binder。
    private AppMonitor.NotificationHolder mNotificationHolder;      //为了获得外界的前台通知的Holder抽象的对象。

    private Context mContext;                                       //保存的上下文。
    private boolean mStopSignal = true;                             //后台检测轮询的开关变量。

    private ArrayList<String> mAppNameList = new ArrayList<>();     //保存获得的白名单包名。
    private BroadcastReceiver mStopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(RECEIVE_CLOSE)){
                mStopSignal = false;
            }
        }
    };                                                              //对应的结束轮询的BroadcastReceiver。

    private BroadcastReceiver mCallBackReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            processCallbacks(action);
        }
        private void processCallbacks(String action) {
            int actionIndex = -1;//本身holder不能为空，且其包含的Callback不能为空。
            if (action != null && mNotificationHolder != null && mNotificationHolder.getCallbacks().size() != 0){
                for (int i = 0; i < mNotificationHolder.getCallbacks().size(); i++){
                    if (action.equals(mNotificationHolder.getCallbacks().get(i).getAction())){
                        actionIndex = i;
                        break;
                    }
                }
                mNotificationHolder.getCallbacks().get(actionIndex).getCallback().callBack(MonitorService.this);
            }
        }
    };
    private ReturnHandler mReturnHandler = new ReturnHandler();     //用于轮询的Handler。
    private PackageNameInListDetector mDetector = new PackageNameInListDetector();
                                                                    //用来做检测的抽象类对象Detector。
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "Created");

        mContext = this;
        mDetector.attach(this);

        if (mNotificationHolder.getNotification() != null){
            startForeground(FOREGROUND_SERVICE_MARK, mNotificationHolder.getNotification());
        }

        registerServiceStopReceiver();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mStopReceiver);
        if (mNotificationHolder.getNotification() != null) {
            stopForeground(true);//不确定这个在没有Notification的情况下会发生什么bug。。
        }
        super.onDestroy();
    }

    /**
     * 动态注册用于停止的Receiver。
     */
    private void registerServiceStopReceiver() {
        IntentFilter broadCastFilter = new IntentFilter();
        broadCastFilter.addAction(RECEIVE);
        registerReceiver(mStopReceiver, broadCastFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mAppNameList = intent.getStringArrayListExtra(MonitorService.GET_LIST_ACTION);
        mDetector.setToDetectList(mAppNameList);

        Log.d(TAG, "startCommanded");
        Log.d(TAG+"_Size", mAppNameList.size() + "");

        new Thread(new Runnable() {
            @Override
            public void run() {
                mReturnHandler.sendEmptyMessage(HANDLER_MARK_MONITOR);
            }
        }).start();
        return START_STICKY;
    }

    /**
     * 此方法已经被抽象到Detector里面。
     * @return True即为检测到白名单应用。
     */
    @Deprecated
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
        return mBinder;
    }

    /**
     *设置内部的Detector监听。（Listener在监听到非白名单应用后调用“AfterDetect”）
     * @param l 外界传进来的Listener。
     */
    public void setDetectListener(AppMonitor.DetectListener l){
        mDetector.setAfterDetectListener(l);
    }

    /**
     * 获得外界的Notification，这里用NotificationHolder封装了一层。
     * @param holder   封装的notification实例。
     */
    public void setForegroundNotification(AppMonitor.NotificationHolder holder){
        mNotificationHolder = holder;
    }

}
