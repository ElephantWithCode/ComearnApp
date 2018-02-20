package com.example.team.comearnapp.engine.white_list_fragment_engine;

import android.util.Log;

import com.example.team.comearnapp.entity.AppInfo;

import java.util.ArrayList;

/**
 * Created by Ellly on 2018/2/20.
 */

public class FragmentWhiteListModelForOnline extends FragmentWhiteListModel {

    public static final String TAG = "FWLMFO";

    @Override
    public ArrayList<AppInfo> getAppInfos() {
        Log.d(TAG, "new model applied");
        return super.getAppInfos();
    }
}
