package com.example.team.comearnapp.activity.wang_in_liao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.team.comearnapp.R;
import com.example.team.comearnlib.base.mvp_mode.BaseActivity;

public class FriendsMessagesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleString() {
        return "验证消息";
    }

    @Override
    protected int getTopBarId() {
        return 0;
    }

    @Override
    protected int getContentViewId() {
        return 0;
    }
}
