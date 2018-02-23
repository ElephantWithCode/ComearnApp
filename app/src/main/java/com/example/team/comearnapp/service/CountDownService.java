package com.example.team.comearnapp.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.team.comearnapp.activity.OnClassActivity;
import com.example.team.comearnapp.engine.fragment.class_main.ClassMainModel;
import com.example.team.comearnlib.receiver.BaseReceiver;
import com.example.team.comearnlib.utils.ConvertTools;
import com.example.team.comearnlib.utils.ToastTools;

import java.util.Calendar;

public class CountDownService extends Service {

    public class CountDownBinder extends Binder{
        public CountDownService getService(){
            return CountDownService.this;
        }
    }

    public interface OnCountDownListener{
        void onCountDownEnd(boolean state);
    }

    public static final String TAG_GET_CALENDAR = "get_calendar";

    private CountDownBinder mBinder = new CountDownBinder();

    private Calendar mCalendar;

    private AlarmManager mManager;

    private StartCountDownForServiceReceiver mReceiver;

    private ClassMainModel mModel = new ClassMainModel(this);

    private OnCountDownListener mListener;

    public void setOnCountDownLister(OnCountDownListener l){
        mListener = l;
    }

    public CountDownService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initStartClassReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        initAlarmService(intent);

        return START_STICKY;
    }

    private void initAlarmService(Intent intent) {

        mCalendar = (Calendar) intent.getSerializableExtra(TAG_GET_CALENDAR);

        if (mCalendar != null) {
            Intent i = new Intent(mReceiver.getAction());
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

            Log.d("CDS", pi.toString());

            mManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (mManager != null) {
                mManager.setExact(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pi);
                ToastTools.showToast(this, "本课堂将在   "
                        + mCalendar.get(Calendar.HOUR_OF_DAY) + ":" + mCalendar.get(Calendar.MINUTE)
                        + "开始");

            }
        }
    }

    private void initStartClassReceiver() {
        mReceiver = new StartCountDownForServiceReceiver();
        mReceiver.setmAction("start_count_down");
        registerReceiver(mReceiver, new IntentFilter(mReceiver.getAction()));
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public Calendar getStopTimeCalendar(){
        return mCalendar;
    }

    public class StartCountDownForServiceReceiver extends BaseReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("SCDFSR", "On Start Count Receive Successfully");

            if (intent.getAction() != null && intent.getAction().equals(mAction)) {

                Intent activityIntent = new Intent(CountDownService.this, OnClassActivity.class);
                activityIntent.setAction("refresh_on_class_activity");

                if (mModel.getClassState()){
                    mModel.saveClassState(false);

                    CountDownService.this.startActivity(activityIntent);

                    ToastTools.showToast(CountDownService.this, "From Service");

                    stopSelf();
                }else {
                    mModel.saveClassState(true);

                    Intent serviceIntent = new Intent(CountDownService.this, CountDownService.class);
                    serviceIntent.putExtra(TAG_GET_CALENDAR, ConvertTools.constructFromTimeInMilis(mModel.getClassStopTime()));
                    startService(serviceIntent);

                    CountDownService.this.startActivity(activityIntent);
                }

                if (mListener != null){
//                    mListener.onCountDownEnd(mModel.getClassState());
                }
            }
        }
    }
}
