package com.example.team.comearnapp.engine.fragment.class_main;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.receiver.UpdateCountDownReceiver;
import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;

import java.util.Calendar;

import cn.iwgang.countdownview.CountdownView;

public class ClassMainFragment extends Fragment implements ClassMainView, UpdateCountDownReceiver.OnReceiveListener{

    public static final String TAG = "CMF";
    private TextView mQuitBtn;
    private CountdownView mCountDownView;

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
                getActivity().finish();
            }
        });


        mQuitBtn = view.findViewById(R.id.frag_class_main_btn_quit);
        mQuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "quit button clicked");

            }
        });

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
    public void onReceive() {
        mPresenter.startCountDown();
    }
}
