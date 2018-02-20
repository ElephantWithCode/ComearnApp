package com.example.team.comearnapp.engine.white_list_fragment_engine;

import android.os.Handler;

import com.example.team.comearnapp.entity.AppInfo;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;

import java.util.ArrayList;

/**
 * Created by Ellly on 2018/2/20.
 */
public class FragmentWhiteListPresenter extends BasePresenter<FragmentWhiteListView> {

    public static final String TAG = "WLF";
    protected FragmentWhiteListModel mModel = new FragmentWhiteListModel();
    protected Handler mUpdateHandler = new Handler();

    public FragmentWhiteListPresenter(){

    }

    public FragmentWhiteListPresenter setModel(FragmentWhiteListModel model){
        mModel = model;
        return this;
    }

    @Override
    public void attachView(FragmentWhiteListView view) {
        super.attachView(view);
        mModel.attach(mView.getContext());
    }

    @Override
    public void detachView() {
        super.detachView();
        mModel.detach();
    }

    public void updateAppList(){
        mView.presentLoading();
        new Thread(){
            @Override
            public void run() {
                if (mView.appsShowType()){
                    mModel.switchQueryScope();
                }
                final ArrayList<AppInfo> appInfos = mModel.getAppInfos();
                mUpdateHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mView.updateAppList(appInfos);
                        mView.stopLoading();
                    }
                }, 0);
            }
        }.start();
    }

    public void uploadAppSelectedStates(){
        mView.getInfos();
    }
}
