package com.example.team.wang.debug;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.team.wang.utils.FloatWindowManager;
import com.example.team.wang_part.R;

public class TestFloatWindowService extends Service {

    private FloatWindowManager mWindowManager;

    public TestFloatWindowService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mWindowManager = new FloatWindowManager(this);
        mWindowManager.setContentLayout(R.layout.activity_on_class_float_window_layout).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWindowManager.cancel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
