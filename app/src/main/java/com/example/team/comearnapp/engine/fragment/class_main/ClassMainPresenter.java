package com.example.team.comearnapp.engine.fragment.class_main;

import android.util.Log;

import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;

import java.util.Calendar;

/**
 * Created by Ellly on 2018/2/22.
 */
public class ClassMainPresenter extends BasePresenter<ClassMainView> {
    private ClassMainModel mModel;

    @Override
    public void attachView(ClassMainView view) {
        super.attachView(view);
        mModel = new ClassMainModel(mContext);
    }

    public void startCountDown(){
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        mView.startCountDown(mModel.getStopTime() - timeInMillis);
        Log.d("CMP", mModel.getStopTime() + "   " + timeInMillis);
    }

    public void saveStopTime(){
        mModel.saveStopTime(mView.getStopTime());
    }
}
