package com.example.team.comearnapp.engine.fragment.class_main;

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

    private Context mContext;


    public ClassMainModel(Context mContext) {
        this.mContext = mContext;
    }

    public void saveStopTime(long time){
        SharedPreferences preferences = mContext.getSharedPreferences(TAG_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(TAG_STOP_TIME, time);
        editor.apply();
    }

    public long getStopTime(){
        SharedPreferences preferences = mContext.getSharedPreferences(TAG_PREFERENCES, Context.MODE_PRIVATE);
        Log.d("CMM", preferences.getLong(TAG_STOP_TIME, 0) + "");
        return preferences.getLong(TAG_STOP_TIME, 0);
    }
}
