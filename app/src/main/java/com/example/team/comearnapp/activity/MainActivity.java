package com.example.team.comearnapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.team.comearnapp.R;
import com.example.team.monitorlib.components.AppMonitor;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppMonitor monitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monitor = new AppMonitor();
        monitor.attach(this);
        monitor.startMonitor(new ArrayList<String>());
    }

    @Override
    protected void onDestroy() {
        monitor.detach();
        super.onDestroy();
    }
}
