package com.example.team.comearnapp.activity.groupActivity;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.R2;
import com.example.team.comearnapp.activity.ClassDetailActivity;
import com.example.team.comearnapp.activity.MoreClassActivity;
import com.jaeger.library.StatusBarUtil;

import java.nio.file.Path;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.Body;

/**
 * 普通的群组界面
 */
public class GroupActivity extends AppCompatActivity {
    @BindView(R2.id.groupIcon_img)
    ImageView imgGroupIcon;
    @BindView(R2.id.groupName_tv)
    TextView tvGroupName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        initViews();
    }

    private void initViews() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.green), 50);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        /**
         * 通过url的方式获取群组名
         */
        if (getIntent().getData().getQueryParameter("title") != null) {
            tvGroupName.setText(getIntent().getData().getQueryParameter("title"));
        }
    }

    @OnClick(R2.id.groupIcon_img)
    void onClickGroupImg() {
        Intent intent = new Intent(this, ClassDetailActivity.class);
        //传递群组昵称、群组id
        intent.putExtra("groupName", getIntent().getData().getQueryParameter("title"))
                .putExtra("groupId", getIntent().getData().getQueryParameter("targetId"));
        startActivity(intent);
    }
}
