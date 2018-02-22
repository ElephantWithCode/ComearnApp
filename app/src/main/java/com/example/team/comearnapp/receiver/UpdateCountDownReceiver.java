package com.example.team.comearnapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.team.comearnlib.receiver.BaseReceiver;

public class UpdateCountDownReceiver extends BaseReceiver {


    public static final String TAG = "UCDR";

    public interface OnReceiveListener{
        void onReceive();
    }


    private OnReceiveListener mListener;

    public UpdateCountDownReceiver setListener(OnReceiveListener listener){
        mListener = listener;
        return this;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        mListener.onReceive();
        Log.d(TAG, "Received Update");
    }
}
