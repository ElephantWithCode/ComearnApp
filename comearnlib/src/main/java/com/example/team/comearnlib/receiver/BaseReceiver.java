package com.example.team.comearnlib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Ellly on 2018/2/22.
 */

public class BaseReceiver extends BroadcastReceiver {

    public static String ACTION;

    protected String mAction;

    public BaseReceiver(){}

    public void setmAction(String mAction){
        this.mAction = mAction;
        ACTION = mAction;
    }

    @Deprecated
    public BaseReceiver(String mAction) {
        this.mAction = mAction;
        ACTION = mAction;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("_1BR", "Base Received" + intent.getAction());
        if (intent.getAction() == null || !intent.getAction().equals(mAction)){
            return;
        }
    }

    public String getAction(){
        return mAction;
    }
}
