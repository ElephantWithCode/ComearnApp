package com.example.team.wang.utils;

import android.content.Context;
import android.content.Intent;

import com.example.team.wang.activity.OnClassActivity;
import com.example.team.wang.engine.fragment.class_main.ClassMainModel;
import com.example.team.wang.service.CountDownService;
import com.example.team.comearnlib.utils.ConvertTools;

import java.util.Calendar;

/**
 * Created by Ellly on 2018/2/26.
 */

public class ClassBehaviorManager {
    private Calendar mClassStartCalendar;
    private Calendar mClassStopCalendar;

    private CharSequence mStartTimeText;
    private CharSequence mLastTimeText;

    private long mStartTime;
    private long mStopTime;

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
        mStartTimeText = startTime;
        return this;
    }

    public ClassBehaviorManager setLastTime(CharSequence lastTime){
        mLastTimeText = lastTime;
        return this;
    }

    public ClassBehaviorManager setStartTime(long startTimeInMillis){
        mStartTime = startTimeInMillis;
        return this;
    }

    public ClassBehaviorManager setStopTime(long stopTimeInMillis){
        mStopTime = stopTimeInMillis;
        return this;
    }

    public ClassBehaviorManager buildWithText(){
        if (mStartTimeText != null) {
            setClassStartCalendar(mStartTimeText);
        }
        if (mLastTimeText != null) {
            setClassStopCalendar(mLastTimeText);
        }
        return this;
    }

    public ClassBehaviorManager buildWithExactTime(){
        setClassStartCalendar(mStartTime);
        setClassStopCalendar(mStopTime);
        return this;
    }

    private void setClassStartCalendar(CharSequence startTime){
        setClassStartCalendar(ConvertTools.getTimeInMillisFromAssignedHourAndMinute(startTime.toString()));
    }

    private void setClassStartCalendar(long startTimeInMillis){
        mClassStartCalendar = Calendar.getInstance();
        mClassStartCalendar.setTimeInMillis(startTimeInMillis);
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

    private void setClassStopCalendar(long classStopTimeInMillis){
        mClassStopCalendar = Calendar.getInstance();
        mClassStopCalendar.setTimeInMillis(classStopTimeInMillis);
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

    /**
     * 仅仅有逻辑上的倒计时
     * 并不会引起视图上的倒计时
     */
    public void triggerCountDown(){

        mModel.saveStopTime(getClassStartCalendar().getTimeInMillis());

        mModel.saveClassStopTime(getClassStopCalendar().getTimeInMillis());

        mContext.startService(createToServiceIntent());

        mContext.sendBroadcast(new Intent("update_count_time"));

        Intent intent = new Intent(mContext, OnClassActivity.class);
        mContext.startActivity(intent);

    }
}
