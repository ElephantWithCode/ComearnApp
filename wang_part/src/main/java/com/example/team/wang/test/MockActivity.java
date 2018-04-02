package com.example.team.wang.test;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.team.wang_part.R;
//import com.example.team.monitorlib.components.AppInfoObtainer;
//import com.example.team.monitorlib.components.MonitorService;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;

import java.util.ArrayList;

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
//    private AppInfoObtainer mObtainer;
    private ArrayList<String> mAppNameList = new ArrayList<>();
/*
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> listExtra = intent.getStringArrayListExtra(MonitorService.GET_LIST_ACTION);
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
        Log.d(TAG + "_C", "OnCreate");


        mPresenter = new MockPresenter();

        mPresenter.attachView(this);

/*
        mObtainer = new AppInfoObtainer(this);

        mObtainer.startQuery();
*/

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
/*
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter.isAttached()) {
                    ArrayList<String> selectedList = mPresenter.getSelectedAppNamesList();
                    Intent sendNameIntent = new Intent(MockActivity.this, MonitorService.class);
                    sendNameIntent.putStringArrayListExtra(MonitorService.GET_LIST_ACTION, selectedList);
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
                mObtainer.startQuery();
                mAppNameList.clear();//为了保证原地修改对象，而不是更改对象。
                mAppNameList.addAll(mObtainer.getPackageNames());
                mPresenter.updateList(mAppNameList);
            }
        });*/
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
//        mObtainer.detach();
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
