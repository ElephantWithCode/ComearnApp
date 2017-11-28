package com.example.team.comearnapp.test;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.engine.MonitorService;
import com.example.team.comearnapp.engine.QueryAppsIntentService;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.comearnlib.utils.ToastTools;

import java.util.ArrayList;
import java.util.List;

interface MockBaseView extends IBaseView{
    <T>void updateList(ArrayList<T> list);
}

public class MockActivity extends AppCompatActivity implements MockBaseView{


    private static final String TAG = "Mock";
    private MockPresenter mPresenter;
    private Button mStartBtn;
    private Button mStopBtn;
    private Button mGetAppListBtn;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> names = intent.getStringArrayListExtra(getResources().getString(R.string.WYY_getAppListTag));
            mPresenter.updateList(names);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock);

        mPresenter = new MockPresenter();

        mPresenter.attachView(this);
        mPresenter.registerReceiver(mReceiver, getString(R.string.WYY_getListAction));

        iniViews();
    }

    private void iniViews() {
        mStartBtn = findViewById(R.id.act_mock_btn_service_start);
        mStopBtn = findViewById(R.id.act_mock_btn_service_stop);
        mGetAppListBtn = findViewById(R.id.act_mock_btn_start_get_app_list_service_);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter.isAttached()) {
                    mPresenter.startService(MonitorService.class);
                }else {
                    Log.d(TAG, "Unattached");
                }
            }
        });
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter.isAttached()) {
                    mPresenter.stopService(MonitorService.class);
                    mPresenter.sendBroadCast(new Intent(MonitorService.RECEIVE));
                }else {
                    Log.d(TAG, "Unattached");
                }
            }
        });
        mGetAppListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.startService(QueryAppsIntentService.class);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter.unregisterReceiver(mReceiver);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public <T> void updateList(ArrayList<T> list) {
        for (T t : list) {
            Log.d(TAG+"_NAME_LIST", t.toString());
        }
    }
}
