package com.example.team.monitorlib.components;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Ellly（汪衣宇） on 2018/1/30.
 * 提供手机所有应用信息，以及监控功能。
 * ——————————————————
 * 新增提供前台服务功能。
 */

public class AppMonitor {

    /**
     * 此接口为检测到非白名单应用后应该进行的行动。
     * 注意：
     *      如果需要强制返回应用界面的话，最好使用接口中的Context（来自MonitorService），直接用Activity的startActivity会在退出应用后失效。
     */
    public interface DetectListener{
        void afterDetect(Context context);
    }

    /**
     * 用来获得自定义前台服务通知的PendingIntent。
     * PS：用的是服务的Context。
     * @param action 对应的写进MonitorService中的Receiver所接受的action字符串。
     * @param requestCode Notification的RequestCode属性（应该是与外部关联较大）。
     * @return 生成的PendingIntent。
     */
    public  PendingIntent getPendingIntent(String action, int requestCode){
        Intent intent = new Intent(action);
        return PendingIntent.getBroadcast(mContext.getApplicationContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 这个类是点击事件的Action和回调的抽象集合类。
     */
    public static class NotificationCallback{
        public NotificationCallback(String mAction, ICallback mCallback) {
            this.mAction = mAction;
            this.mCallback = mCallback;
        }

        public String getAction() {
            return mAction;
        }

        public ICallback getCallback() {
            return mCallback;
        }

        /**
         *回调接口
         */
        public interface ICallback{
            void callBack(Context context);
        }

        private String mAction;
        private ICallback mCallback;
    }

    /**
     * 整体notification的抽象集合类。
     */
    public static  class NotificationHolder{
        private Notification mNotification;

        private ArrayList<NotificationCallback> mNosyCallbacks = new ArrayList<>();

        public ArrayList<NotificationCallback> getCallbacks(){return mNosyCallbacks;}

        public void addCallback(NotificationCallback callback){mNosyCallbacks.add(callback);}

        public Notification getNotification(){
            return mNotification;
        }

        public void setNotification(Notification mNotification) {
            this.mNotification = mNotification;
        }
    }

    /**
     * 为MonitorService添加接口。
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MonitorService.CallbackBinder binder = (MonitorService.CallbackBinder) service;
            MonitorService monitorService = binder.getService();
            monitorService.setDetectListener(mListener);
            Log.d("AM", mListener + "");

            monitorService.setForegroundNotification(mNotificationHolder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private NotificationHolder mNotificationHolder = new NotificationHolder() {};

    private DetectListener mListener;

    private AppInfoObtainer mObtainer;
    private Context mContext;

    public AppMonitor(){
        mObtainer = new AppInfoObtainer();
    }

    /**
     *  主要是bindService比较重要。（用来传递回调接口）
     * @param context 上下文
     */
    public void attach(Context context){
        mContext = context;
        mObtainer.attach(context);
        mContext.bindService(new Intent(mContext, MonitorService.class), connection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 退出应用时调用此方法
     * 即应在OnDestroy()里面调用。
     * 防止connection泄露。
     */
    public void detach(){
        mContext.unbindService(connection);
        mObtainer.detach();
        mContext = null;
    }

    /**
     * 改变查询手机应用的范围；
     * 默认开始状态为查询非系统应用
     * 调用一次后变为只查询系统应用
     * 再调用一次后变反。
     *
     */
    public void switchQueryScope(){
        mObtainer.switchQueryScope();
    }

    /**
     * 获取一个包含所有查询得到的应用信息列表。
     * @return  ApplicationInfoEntity 包含：0：包名
     *                                      1：应用名
     *                                      2：应用图标（Drawable）
     *
     * 注意：！
     *      此方法在绝大多数情景下为耗时操作，不应在主线程下直接调用。
     */
    public ArrayList<ApplicationInfoEntity> getAllInformationList(){
        ArrayList<ApplicationInfoEntity> entities = new ArrayList<>();
        mObtainer.startQuery();//主要耗时逻辑
        ArrayList<String> packageNames = mObtainer.getPackageNames();
        ArrayList<String> appLabels = mObtainer.getAppsNames();
        ArrayList<Drawable> appIcons = mObtainer.getAppIcons();
        for (int i = 0; i < packageNames.size(); i++){
            entities.add(new ApplicationInfoEntity(packageNames.get(i), appLabels.get(i), appIcons.get(i)));
        }
        return entities;
    }


    /**
     * 开启监控服务
     * @param detectPackageNames 表示白名单应用的包名列表，在此列表下的应用不会被检测。
     */
    public void startMonitor(ArrayList<String> detectPackageNames){
        Intent sendNameIntent = new Intent(mContext, MonitorService.class);
        sendNameIntent.putStringArrayListExtra(MonitorService.GET_LIST_ACTION, detectPackageNames);
        mContext.startService(sendNameIntent);
    }

    /**
     * 关闭服务。
     */
    public void stopMonitor(){
        mContext.sendBroadcast(new Intent(MonitorService.RECEIVE_CLOSE));
        mContext.stopService(new Intent(mContext, MonitorService.class));
    }

    /**
     * 监听（Monitor）的回调。
     * @param l 接口。
     */
    public void setDetectListener(DetectListener l){
        mListener = l;
    }

    /**
     * 获得外界的Notification，这里用NotificationHolder封装了一层。
     * @param holder   封装的notification实例。
     */
    public void setForegroundNotification(AppMonitor.NotificationHolder holder){
        mNotificationHolder = holder;
    }
}
