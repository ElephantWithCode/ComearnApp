package com.example.team.wang.engine.fragment.class_main;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;

/**
 * Created by Ellly on 2018/2/20.
 */

public class ClassMainModel extends BaseModel {

    public static final String TAG_PREFERENCES = "get_preferences";
    public static final String TAG_STOP_TIME = "get_stop_time";
    public static final String TAG_CLASS_STOP_TIME = "get_class_stop_time";
    public static final String TAG_CLASS_STATE = "get_class_state";
    private static final String TAG_FROM_QUIT_BTN = "from_quit_btn";

    private Context mContext;


    public ClassMainModel(Context mContext) {
        this.mContext = mContext;
    }

    public void saveStopTime(long time){
        saveLongValueInPreferences(time, TAG_STOP_TIME);
    }

    public void saveClassStopTime(long time){
        saveLongValueInPreferences(time, TAG_CLASS_STOP_TIME);

        Log.d("CMM", "class stop time : " + time);
    }

    public void saveClassState(boolean state){
        SharedPreferences preferences = mContext.getSharedPreferences(TAG_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(TAG_CLASS_STATE, state);
        editor.apply();
    }

    public boolean getClassState(){
        SharedPreferences preferences = mContext.getSharedPreferences(TAG_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getBoolean(TAG_CLASS_STATE, false);
    }
    public void saveFromQuitBtn(boolean state){
        SharedPreferences preferences = mContext.getSharedPreferences(TAG_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(TAG_FROM_QUIT_BTN, state);
        editor.apply();
    }

    public boolean getFromQuitBtn(){
        SharedPreferences preferences = mContext.getSharedPreferences(TAG_PREFERENCES, Context.MODE_PRIVATE);
        return preferences.getBoolean(TAG_FROM_QUIT_BTN, false);
    }

    public long getStopTime(){
        return getValueFromPreferences(TAG_STOP_TIME);
    }

    public long getClassStopTime(){
        return getValueFromPreferences(TAG_CLASS_STOP_TIME);
    }

    private long getValueFromPreferences(String tag) {
        SharedPreferences preferences = mContext.getSharedPreferences(TAG_PREFERENCES, Context.MODE_PRIVATE);

        Log.d("CMM", preferences.getLong(tag, 0) + "");

        return preferences.getLong(tag, 0);
    }

    private void saveLongValueInPreferences(long time, String tagClassStopTime) {
        SharedPreferences preferences = mContext.getSharedPreferences(TAG_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(tagClassStopTime, time);
        editor.apply();
    }
}
