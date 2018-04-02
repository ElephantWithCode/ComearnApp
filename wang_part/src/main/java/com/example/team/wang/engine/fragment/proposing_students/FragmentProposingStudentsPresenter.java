package com.example.team.wang.engine.fragment.proposing_students;

import android.os.Handler;

import com.example.team.wang.entity.PersonInfo;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;

import java.util.ArrayList;

/**
 * Created by Ellly on 2018/2/20.
 */

public class FragmentProposingStudentsPresenter extends BasePresenter<FragmentProposingStudentsView> {

    private FragmentProposingStudentsModel mModel = new FragmentProposingStudentsModel();
    private Handler mHandler = new Handler();

    public void updateList(){
        mView.presentLoading();
        new Thread(){
            @Override
            public void run() {
                final ArrayList<PersonInfo> infos =  mModel.receiveQuitRequests();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.updateList(infos);
                        mView.stopLoading();
                    }
                });
            }
        }.start();
    }

}
