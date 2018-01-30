package com.example.team.monitorlib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.IBinder;

import com.example.team.monitorlib.service.MonitorService;
import com.example.team.monitorlib.service.QueryAppsIntentService;
import com.example.team.monitorlib.entity.ApplicationInfoEntity;
import com.example.team.monitorlib.tools.AppInfoObtainer;

import java.util.ArrayList;

/**
 * Created by Ellly on 2018/1/30.
 */

public class AppMonitor {

    /**
     * 此接口为检测到非白名单应用后应该进行的行动。
     */
    public interface DetectListener{
        public void afterDetect();
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
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private AppInfoObtainer mObtainer;
    private DetectListener mListener;
    private Context mContext;

    public AppMonitor(){
        mObtainer = new AppInfoObtainer();
    }

    /**
     *  主要是bindService比较重要。
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
     */
    public ArrayList<ApplicationInfoEntity> getAllInformationList(){
        ArrayList<ApplicationInfoEntity> entities = new ArrayList<>();
        mObtainer.startQuery();
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
        mContext.sendBroadcast(new Intent(MonitorService.RECEIVE));
        mContext.stopService(new Intent(mContext, MonitorService.class));
    }

    /**
     * 监听（Monitor）的回调。
     * @param l 接口。
     */
    public void setDetectListener(DetectListener l){
        mListener = l;
    }

}
