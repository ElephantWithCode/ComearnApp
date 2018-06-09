package com.example.team.wang.engine.fragment.class_main;

import android.util.Log;

import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;

import java.util.Calendar;

/**
 * Created by Ellly on 2018/2/22.
 */
public class ClassMainPresenter extends BasePresenter<ClassMainView> {
    public ClassMainModel mModel;

    @Override
    public void attachView(ClassMainView view) {
        super.attachView(view);
        mModel = new ClassMainModel(mContext);
    }

    public void startCountDown(){
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        if (mModel.getClassState()) {
            mView.startCountDown(mModel.getClassStopTime() - timeInMillis);
        }else {
            mView.startCountDown(mModel.getStopTime() - timeInMillis);
        }

        Log.d("CMP", mModel.getStopTime() + " : " + mModel.getClassStopTime() + "  1 " + timeInMillis);


    }


    public void saveStopTime(){
        mModel.saveStopTime(mView.getStopTime());
    }

    public void saveClassState(boolean state){
        mModel.saveClassState(state);
    }

    public boolean getClassState(){
        return mModel.getClassState();
    }

    public void saveClassStopTime(){

    }

    public void refreshClassStateTv(boolean state){
        mView.refreshClassStateTv(state);
    }

    public void refreshWholeView(){
        refreshClassStateTv(getClassState());
        startCountDown();
    }


    public void onCountDownEnd(){

    }
}
