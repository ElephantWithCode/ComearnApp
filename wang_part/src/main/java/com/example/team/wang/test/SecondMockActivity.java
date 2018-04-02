package com.example.team.wang.test;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.team.wang_part.R;
import com.example.team.monitorlib.components.AppMonitor;
import com.example.team.monitorlib.components.ApplicationInfoEntity;

import java.util.ArrayList;

public class SecondMockActivity extends AppCompatActivity {

    private AppMonitor mMonitor = new AppMonitor();
    private ArrayList<ApplicationInfoEntity> mList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_mock);
        mMonitor.attach(this);

//        mMonitor.switchQueryScope();更换监听范围。
        mMonitor.setDetectListener(new AppMonitor.DetectListener() {
            @Override
            public void afterDetect(Context context) {
                Log.d("SMA_", "Monitor Succeed");
                context.startActivity(new Intent(context, SecondMockActivity.class));//这里有个坑：必须得用service的context才能保证一直都能正常工作，直接Activity的话会不能startActivity
            }
        });

        AppMonitor.NotificationHolder holder = new AppMonitor.NotificationHolder();
        Notification.Builder builder = new Notification.Builder(getApplicationContext()).setContentTitle("测试").setContentText("测试Content").setSmallIcon(R.mipmap.ic_launcher).setWhen(System.currentTimeMillis());
        /*holder.addCallback(new AppMonitor.NotificationCallback("ACTION_CLICK", new AppMonitor.NotificationCallback.ICallback() {
            @Override
            public void callBack(Context context) {
                Log.d("SMA_CALL", "call back");
            }
        }));*/
        Intent intent = new Intent(this, SecondMockActivity.class);
        PendingIntent pi = PendingIntent.getActivities(this, 0, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);
        holder.setNotification(builder.build());
        mMonitor.setForegroundNotification(holder);


        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        Button acquire = findViewById(R.id.acquire);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(mList.get(0).getPackageName());
                arrayList.add(mList.get(1).getPackageName());
                mMonitor.startMonitor(arrayList);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMonitor.stopMonitor();
            }
        });
        acquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList = mMonitor.getAllInformationList();
                for (ApplicationInfoEntity info : mList){
                    Log.d("SMA", info.getPackageName() + "  "  + info.getAppLabel());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMonitor.detach();
    }
}
