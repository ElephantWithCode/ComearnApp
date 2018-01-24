package com.example.team.comearnapp.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.CheckBox;

import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.utils.ToastTools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public  class MockPresenter extends BasePresenter<MockBaseView> {

    public static final String SP_TAG = "checkbox_status";
    public static final String APP_NAME_TAG = "selected_app_name";


    public void startService(Class<?> className){
        mContext.startService(new Intent(mContext, className));
    }

    public void startService(Intent intent){
        mContext.startService(intent);
    }

    public void stopService(Class<?> className){
        ToastTools.showToast(mContext, "DummyStop");
        mContext.stopService(new Intent(mContext, className));
    }

    public void sendBroadCast(Intent i) {
            mContext.sendBroadcast(i);
        }

    public <T>void updateList(ArrayList<T> list){
            mView.updateList(list);
        }


    public void saveAppSelectedStatus() {
        ArrayList<CheckBox> cbList = mView.getCheckBoxList();
        ArrayList<String> nameList = mView.getAppNameList();
        Set<String> nameSet = Collections.EMPTY_SET;
        nameSet.addAll(nameList);
        SharedPreferences sp = mContext.getSharedPreferences(SP_TAG, 0);
        SharedPreferences.Editor e = sp.edit();
        e.putStringSet(APP_NAME_TAG, nameSet);
        e.apply();
    }

    public ArrayList<String> getSelectedAppNamesList(){
        ArrayList<CheckBox> cbList = mView.getCheckBoxList();
        ArrayList<String> nameList = mView.getAppNameList();
        ArrayList<String> selectedList = new ArrayList<>();
        for (int i = 0; i < cbList.size(); i++){
            if (cbList.get(i).isChecked()){
                selectedList.add(nameList.get(i));
            }
        }
        return selectedList;
    }

}
