package com.example.team.comearnlib.base.mvp_mode.base_presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;

/**
 * Created by Ellly on 2017/11/27.
 */

public class BasePresenter <V extends IBaseView>{

    protected V mView;
    protected Context mContext;
    @Deprecated
    public BasePresenter(V view){
        mView = view;
    }
    public BasePresenter(){

    }

    public void registerReceiver(BroadcastReceiver receiver, String action){
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(action);
        mContext.registerReceiver(receiver, iFilter);
    }
    public void unregisterReceiver(BroadcastReceiver receiver){
        mContext.unregisterReceiver(receiver);
    }

    public void attachView(V view){
        mView = view;
        mContext = view.getContext();
    }
    public void detachView(){
        mView = null;
    }
    public boolean isAttached(){
        return mView != null;
    }

}
