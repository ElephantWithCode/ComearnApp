package com.example.team.comearnapp.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.ui.CompoundTextLayout;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.comearnlib.utils.ConvertTools;
import com.example.team.personalspacelib.test.DefaultCustomCardView;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;

interface ClassSettingView extends IBaseView{}

public class ClassSettingActivity extends AppCompatActivity implements ClassSettingView{

    private DefaultCustomCardView mSpotCard;
    private DefaultCustomCardView mStartTimeCard;
    private DefaultCustomCardView mLastTimeCard;

    private TextView mStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_setting);

        initUI();
    }

    private void initUI() {
        mSpotCard = findViewById(R.id.act_class_setting_dccv_spot);
        mSpotCard.setHeadText("地点");
        mSpotCard.addSingleView(new EditText(this));

        mStartTimeCard = findViewById(R.id.act_class_setting_dccv_start_time);
        mStartTimeCard.setHeadText("开始时间");


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
        mLastTimeCard.setHeadText("持续时间");


    }

    @Override
    public Context getContext() {
        return this;
    }
}
