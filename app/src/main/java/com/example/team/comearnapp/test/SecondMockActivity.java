package com.example.team.comearnapp.test;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.team.comearnapp.R;
import com.example.team.monitorlib.AppMonitor;
import com.example.team.monitorlib.entity.ApplicationInfoEntity;

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
