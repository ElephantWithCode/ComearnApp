package com.example.team.wang.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.team.comearnlib.utils.ToastTools;
import com.example.team.wang.activity.OnClassActivity;
import com.example.team.wang.debug.TestFloatWindowService;

/**
 * Created by Ellly on 2018/4/8.
 */

public class MonitorRecentClickReceiver extends BroadcastReceiver {

    private boolean isWindowShow = false;

    @Override
    public void onReceive(Context context, Intent intent) {

        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        String action = intent.getAction();

        if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
//                    ToastTools.showToast(context, "Home");
                } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {

                    context.startActivity(new Intent(context, OnClassActivity.class));
                    /*if (! isWindowShow){
                        context.startService(new Intent(context, TestFloatWindowService.class));
                        isWindowShow = true;
                    }else {
                        context.stopService(new Intent(context, TestFloatWindowService.class));
                        isWindowShow = false;
                    }*/

//                    ToastTools.showToast(context, "Recent");
                    Log.d("_MRCR", "Recent Clicked");
                }
            }
        }
    }
}
