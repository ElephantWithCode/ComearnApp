package com.example.team.wang.engine.fragment.white_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.team.wang.entity.AppInfo;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Ellly on 2018/2/20.
 */

public class FragmentWhiteListModelForOnline extends FragmentWhiteListModel {

    public static final String TAG = "FWLMFO";

    public static final String GET_APP_INFO_FROM_SP = "get_app_info_from_sp";
    public static final String GET_APP_INFO = "gaifwlmfo";

    public void setAppInfos(ArrayList<AppInfo> infos){
        SharedPreferences preferences = mContext.getSharedPreferences(GET_APP_INFO_FROM_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (int i = 0; i < infos.size(); i++){
            AppInfo info = infos.get(i);
            editor.putString(GET_APP_INFO + i, info.getAppPackageName());
        }
        editor.putInt(GET_APP_INFO + "SIZE", infos.size());
        editor.apply();
    }

    /**
     * 成功写出了人生中最丑的代码。
     * 由于工期有限这里实现存储直接用SP。
     * TODO:后期应该改成数据库db。
     * @return
     */
    @Override
    public ArrayList<AppInfo> getAppInfos() {
        Log.d(TAG, "new model applied");
        ArrayList<AppInfo> allInfos = new ArrayList<>();
        allInfos.addAll(super.getAppInfos());

        ArrayList<AppInfo> selectedInfos = new ArrayList<>();

        SharedPreferences preferences = mContext.getSharedPreferences(GET_APP_INFO_FROM_SP, Context.MODE_PRIVATE);

        ArrayList<String> packages = new ArrayList<>();

        int size = preferences.getInt(GET_APP_INFO + "SIZE", 0);
        for (int i = 0; i < size; i++){
            packages.add(preferences.getString(GET_APP_INFO + i, ""));
        }

        //TODO:即将成为效率灾难的代码段。
        for (String packageName : packages){
            for (AppInfo appInfo : allInfos){
                if (Objects.equals(appInfo.getAppPackageName(), packageName)){
                    selectedInfos.add(appInfo);
                    break;
                }
            }
        }
        return selectedInfos;
    }
}
