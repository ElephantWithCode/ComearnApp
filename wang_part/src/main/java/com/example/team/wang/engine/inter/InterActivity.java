package com.example.team.wang.engine.inter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.team.wang.activity.OnClassActivity;
import com.example.team.wang_part.R;

@Route(path = "/wang_part/inter")
public class InterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = "refresh_on_class_activity";

        Intent toOnClassIntent = new Intent(this, OnClassActivity.class);
        toOnClassIntent.setAction(action);
        startActivity(toOnClassIntent);

        finish();
    }
}
