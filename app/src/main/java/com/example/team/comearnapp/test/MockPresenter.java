package com.example.team.comearnapp.test;

import android.content.Intent;

import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.utils.ToastTools;

import java.util.ArrayList;

public  class MockPresenter extends BasePresenter<MockBaseView> {


        public void startService(Class<?> className){
            mContext.startService(new Intent(mContext, className));
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


    }
