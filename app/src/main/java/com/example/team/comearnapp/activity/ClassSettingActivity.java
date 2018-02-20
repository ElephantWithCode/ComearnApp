package com.example.team.comearnapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.ui.CompoundTextLayout;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.comearnlib.utils.ConvertTools;
import com.example.team.personalspacelib.test.DefaultCustomCardView;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;

interface ClassSettingView extends IBaseView{}

public class ClassSettingActivity extends AppCompatActivity implements ClassSettingView{

    private DefaultCustomCardView mSpotCard;
    private DefaultCustomCardView mStartTimeCard;
    private DefaultCustomCardView mLastTimeCard;

    private TextView mStartTime;
    private TextView mLastTime;
    private BubbleSeekBar mLastTimeSeek;

    private Button mStartStudyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_setting);

        initToolbar();

        initUI();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        mSpotCard = findViewById(R.id.act_class_setting_dccv_spot);
        mSpotCard.setHeadText("地点").setHeadTextColor(getResources().getColor(R.color.colorPrimary));


        mStartTimeCard = findViewById(R.id.act_class_setting_dccv_start_time);
        mStartTimeCard.setHeadText("开始时间").setHeadTextColor(getResources().getColor(R.color.colorPrimary));


        mStartTime = findViewById(R.id.act_class_setting_dccv_start_time_tv_start_time);
        mStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        mStartTime.setText(ConvertTools.parseTime(hourOfDay) + ":" + ConvertTools.parseTime(minute));
                    }
                }, true);
                tpd.vibrate(false);
                tpd.show(getFragmentManager(), "time picker dialog");
            }
        });

        mLastTimeCard = findViewById(R.id.act_class_setting_dccv_last_time);
        mLastTimeCard.setHeadText("持续时间").setHeadTextColor(getResources().getColor(R.color.colorPrimary));


        mLastTime = findViewById(R.id.act_class_setting_dccv_last_time_tv_last_time);

        mLastTimeSeek = findViewById(R.id.act_class_setting_dccv_last_time_bsb_seek_time);
        mLastTimeSeek.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                super.onProgressChanged(bubbleSeekBar, progress, progressFloat);
                mLastTime.setText(progress + "");
            }
        });

        mStartStudyBtn = findViewById(R.id.act_class_setting_btn_start_study);
        mStartStudyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClassSettingActivity.this, OnClassActivity.class));
            }
        });

    }

    @Override
    public Context getContext() {
        return this;
    }
}
