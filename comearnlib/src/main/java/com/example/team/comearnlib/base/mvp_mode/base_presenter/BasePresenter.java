package com.example.team.comearnlib.base.mvp_mode.base_presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.Loader;
import android.nfc.Tag;
import android.util.Log;

import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;

import java.util.ArrayList;

/**
 * Created by Ellly on 2017/11/27.
 */

public class BasePresenter <V extends IBaseView>{

    private static final String TAG = "BASE_PRE";

    protected V mView;
    protected Context mContext;
    protected ArrayList<BroadcastReceiver> mReceivers;
    @Deprecated
    public BasePresenter(V view){
        mView = view;
    }
    public BasePresenter(){

    }

    public void registerReceiver(BroadcastReceiver receiver, String action){
        if (mReceivers == null){
            mReceivers = new ArrayList<>();
        }
        Log.d(TAG, mReceivers + "");
        mReceivers.add(receiver);
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(action);
        mContext.registerReceiver(receiver, iFilter);
    }

    public void unregisterReceiver(BroadcastReceiver receiver){
        mContext.unregisterReceiver(receiver);
    }

    public void unregisterAllReceiver(){
        for (BroadcastReceiver receiver : mReceivers){
            mContext.unregisterReceiver(receiver);
        }
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
