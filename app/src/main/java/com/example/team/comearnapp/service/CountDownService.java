package com.example.team.comearnapp.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.team.comearnapp.receiver.StartCountDownForServiceReceiver;

import java.util.Calendar;

public class CountDownService extends Service {

    public class CountDownBinder extends Binder{
        public CountDownService getService(){
            return CountDownService.this;
        }
    }

    public static final String TAG_GET_CALENDAR = "get_calendar";

    private CountDownBinder mBinder = new CountDownBinder();
    private Calendar mCalendar;
    private AlarmManager mManager;

    public CountDownService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mCalendar = (Calendar) intent.getSerializableExtra(TAG_GET_CALENDAR);

        if (mCalendar != null) {
            Intent i = new Intent(this, StartCountDownForServiceReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

            mManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (mManager != null) {
                mManager.setExact(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), pi);
            }
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public Calendar getStopTimeCalendar(){
        return mCalendar;
    }
}
