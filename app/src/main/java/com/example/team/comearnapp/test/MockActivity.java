package com.example.team.comearnapp.test;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.engine.MonitorService;
import com.example.team.comearnapp.engine.QueryAppsIntentService;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.comearnlib.utils.ToastTools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface MockBaseView extends IBaseView{
    <T>void updateList(ArrayList<T> list);
    ArrayList<CheckBox> getCheckBoxList();
    void setAllCheckBoxStatus(boolean checked);
    ArrayList<String> getAppNameList();
}

public class MockActivity extends AppCompatActivity implements MockBaseView{


    private static final String TAG = "Mock";
    private MockPresenter mPresenter;
    private Button mStartBtn;
    private Button mStopBtn;
    private Button mGetAppListBtn;
    private Button mSelectAllBtn;
    private Button mCancelAllBtn;
    private Button mEnsureListBtn;
    private RecyclerView mAppListRv;
    private AppListRvAdapter<String> mAppListAdapter;
    private ArrayList<String> mAppNameList = new ArrayList<>();
/*
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> listExtra = intent.getStringArrayListExtra(QueryAppsIntentService.APP_LIST_NAMES);
            //这里的List引用不能更改
            mAppNameList.addAll(listExtra);
            mPresenter.updateList(mAppNameList);

            Log.d(TAG+"_List", mAppNameList + "");
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock);

        BroadcastReceiver updateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ArrayList<String> listExtra = intent.getStringArrayListExtra(QueryAppsIntentService.APP_LIST_NAMES);
                //这里的List引用不能更改
                mAppNameList.addAll(listExtra);
                mPresenter.updateList(mAppNameList);

                Log.d(TAG+"_List", mAppNameList + "");
            }
        };

        mPresenter = new MockPresenter();

        mPresenter.attachView(this);
        mPresenter.registerReceiver(updateReceiver, getString(R.string.WYY_getListAction));

        mPresenter.startService(QueryAppsIntentService.class);

        iniViews();
    }

    private void iniViews() {
        mEnsureListBtn = findViewById(R.id.act_mock_btn_ensure_list);
        mCancelAllBtn = findViewById(R.id.act_mock_btn_cancel_all);
        mSelectAllBtn = findViewById(R.id.act_mock_btn_select_all);
        mAppListRv = findViewById(R.id.act_mock_rv_app_list);
        mStartBtn = findViewById(R.id.act_mock_btn_service_start);
        mStopBtn = findViewById(R.id.act_mock_btn_service_stop);
        mGetAppListBtn = findViewById(R.id.act_mock_btn_start_get_app_list_service_);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter.isAttached()) {
                    ArrayList<String> selectedList = mPresenter.getSelectedAppNamesList();
                    Intent sendNameIntent = new Intent(MockActivity.this, MonitorService.class);
                    sendNameIntent.putStringArrayListExtra(QueryAppsIntentService.APP_LIST_NAMES, selectedList);
                    mPresenter.startService(sendNameIntent);   
                }else {
                    Log.d(TAG, "Unattached");
                }
            }
        });
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter.isAttached()) {
                    mPresenter.sendBroadCast(new Intent(MonitorService.RECEIVE));
                    mPresenter.stopService(MonitorService.class);
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
        mSelectAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllCheckBoxStatus(true);
            }
        });
        mCancelAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllCheckBoxStatus(false);
            }
        });
        mEnsureListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.saveAppSelectedStatus();
            }
        });
        mAppListAdapter = new AppListRvAdapter<String>(mAppNameList);
        mAppListRv.setAdapter(mAppListAdapter);
        mAppListRv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter.unregisterAllReceiver();
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
        mAppListRv.getAdapter().notifyDataSetChanged();
    }

    @Override
    public ArrayList<CheckBox> getCheckBoxList() {
        return mAppListAdapter.getCheckBoxList();
    }

    @Override
    public void setAllCheckBoxStatus(boolean checked) {
        ArrayList<CheckBox> list = getCheckBoxList();
        for (CheckBox cb : list){
            cb.setChecked(checked);
        }
    }

    @Override
    public ArrayList<String> getAppNameList() {
        return mAppNameList;
    }
}
