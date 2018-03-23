package com.example.team.comearnapp.utils;

import android.content.Context;
import android.content.Intent;

import com.example.team.comearnapp.activity.OnClassActivity;
import com.example.team.comearnapp.activity.RefinedClassSettingActivity;
import com.example.team.comearnapp.engine.fragment.class_main.ClassMainModel;
import com.example.team.comearnapp.service.CountDownService;
import com.example.team.comearnlib.utils.ConvertTools;

import java.util.Calendar;

/**
 * Created by Ellly on 2018/2/26.
 */

public class ClassBehaviorManager {
    private Calendar mClassStartCalendar;
    private Calendar mClassStopCalendar;

    private CharSequence mStartTime;
    private CharSequence mLastTime;

    private Context mContext;

    private ClassMainModel mModel;

    public ClassBehaviorManager(Context context){
        mContext = context;
        mModel = new ClassMainModel(context);
    }

    public Calendar getClassStartCalendar(){
        return mClassStartCalendar;
    }

    public Calendar getClassStopCalendar(){
        return mClassStopCalendar;
    }

    public ClassBehaviorManager setStartTime(CharSequence startTime){
        mStartTime = startTime;
        return this;
    }

    public ClassBehaviorManager setLastTime(CharSequence lastTime){
        mLastTime = lastTime;
        return this;
    }

    public ClassBehaviorManager build(){
        if (mStartTime != null) {
            setClassStartCalendar(mStartTime);
        }
        if (mLastTime != null) {
            setClassStopCalendar(mLastTime);
        }
        return this;
    }

    private void setClassStartCalendar(CharSequence startTime){
        mClassStartCalendar = ConvertTools.constructFromAssignedHourAndMinute(startTime.toString());
    }

    private void setClassStopCalendar(CharSequence lastTime){
        if (mClassStartCalendar != null){
            if (mClassStartCalendar.getTimeInMillis() > System.currentTimeMillis()) {
                mClassStopCalendar = (Calendar) mClassStartCalendar.clone();
            }else {
                mClassStopCalendar = Calendar.getInstance();
            }
            mClassStopCalendar.set(Calendar.MINUTE, mClassStopCalendar.get(Calendar.MINUTE) +
                    Integer.parseInt(ConvertTools.pickNumberFromString(lastTime.toString())));
        }
    }

    private Intent createToServiceIntent(){
        Intent serviceIntent = new Intent(mContext, CountDownService.class);
        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (mClassStartCalendar.getTimeInMillis() > System.currentTimeMillis()) {
            serviceIntent.putExtra(CountDownService.TAG_GET_CALENDAR, mClassStartCalendar);
        }else {
            serviceIntent.putExtra(CountDownService.TAG_GET_CALENDAR, mClassStopCalendar);
            mModel.saveClassState(true);
        }
        return serviceIntent;
    }

    public void triggerCountDown(){

        mContext.startService(createToServiceIntent());

        mContext.sendBroadcast(new Intent("update_count_time"));

        Intent intent = new Intent(mContext, OnClassActivity.class);
        mContext.startActivity(intent);

    }
}
