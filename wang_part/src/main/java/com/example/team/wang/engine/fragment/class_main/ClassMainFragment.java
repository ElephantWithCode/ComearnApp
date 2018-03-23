package com.example.team.wang.engine.fragment.class_main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.team.wang_part.R;
import com.example.team.wang.receiver.UpdateCountDownReceiver;
import com.example.team.wang.service.CountDownService;
import com.example.team.comearnlib.utils.ToastTools;

import cn.iwgang.countdownview.CountdownView;

public class ClassMainFragment extends Fragment implements ClassMainView, UpdateCountDownReceiver.OnReceiveListener {

    public static final String TAG = "CMF";

    private TextView mStateTv;
    private TextView mQuitBtn;
    private CountdownView mCountDownView;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
/*
            CountDownService.CountDownBinder binder = (CountDownService.CountDownBinder) service;
            CountDownService countDownService = binder.getService();
*/
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private UpdateCountDownReceiver mReceiver;

    private ClassMainPresenter mPresenter = new ClassMainPresenter();

    private static final String TAG_ON_CLASS = "on_class";
    private boolean mOnClassState;

    private static final String TAG_STOP_TIME = "stop_time";
    private long mStopTime;


    public static ClassMainFragment newInstance(boolean isOnclass, long stopTime) {

        Bundle args = new Bundle();
        args.putBoolean(TAG_ON_CLASS, isOnclass);
        args.putLong(TAG_STOP_TIME, stopTime);

        ClassMainFragment fragment = new ClassMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null){
            mOnClassState = args.getBoolean(TAG_ON_CLASS, true);
            mStopTime = args.getLong(TAG_STOP_TIME);
        }
        mPresenter.attachView(this);
        mPresenter.saveStopTime();

        mReceiver = new UpdateCountDownReceiver()
                    .setListener(this);
        mReceiver.setmAction("update_count_time");

        getActivity().registerReceiver(mReceiver, new IntentFilter("update_count_time"));
        getActivity().bindService(new Intent(getActivity(), CountDownService.class), mConnection, Context.BIND_AUTO_CREATE);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_main, container, false);

        mCountDownView = view.findViewById(R.id.frag_class_main_cv_count);
        mCountDownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
            @Override
            public void onEnd(CountdownView cv) {
//                onCountDownEnd();
            }
        });


        mQuitBtn = view.findViewById(R.id.frag_class_main_btn_quit);
        mQuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "quit button clicked");

            }
        });

        mStateTv = view.findViewById(R.id.frag_class_main_tv_show_state);

        mPresenter.refreshClassStateTv(mPresenter.getClassState());

        mPresenter.startCountDown();

/*
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(timeInMillis);
        Log.d(TAG, instance.toString());
        instance.setTimeInMillis(mStopTime);
        Log.d(TAG, "    " + instance.toString());
*/

        return view;
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        getActivity().unregisterReceiver(mReceiver);
        getActivity().unbindService(mConnection);
        super.onDestroy();
    }

    @Override
    public void startCountDown(long miliseconds) {
        mCountDownView.start(miliseconds);
    }

    @Override
    public long getStopTime(){
        return mStopTime;
    }

    @Override
    public void refreshClassStateTv(boolean state) {
        if (state){
            mStateTv.setText("距离课堂结束还有：");
            mQuitBtn.setVisibility(View.VISIBLE);
        }else {
            mStateTv.setText("距离上课还有：");
            mQuitBtn.setVisibility(View.INVISIBLE);
        }
    }

    public void onCountDownEnd(boolean state) {
        if (mPresenter.getClassState()){
//            mPresenter.saveClassState(false);
            mPresenter.refreshClassStateTv(true);
            mPresenter.startCountDown();
        }else {
//            mPresenter.saveClassState(true);
            mPresenter.refreshClassStateTv(false);
            ToastTools.showToast(getContext(), "倒计时结束");
        }
    }

    @Override
    public boolean getClassState() {
        return mOnClassState;
    }


    @Override
    public void onReceive() {
        mPresenter.refreshClassStateTv(mPresenter.getClassState());
        mPresenter.startCountDown();
    }

    public ClassMainPresenter getPresenter(){
        return mPresenter;
    }
}
