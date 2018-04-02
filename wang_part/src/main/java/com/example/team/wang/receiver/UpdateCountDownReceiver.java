package com.example.team.wang.receiver;

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
        if (intent.getAction() != null && intent.getAction().equals("update_count_time")) {
            mListener.onReceive();
        }
        Log.d(TAG, "Received Update");
    }
}
