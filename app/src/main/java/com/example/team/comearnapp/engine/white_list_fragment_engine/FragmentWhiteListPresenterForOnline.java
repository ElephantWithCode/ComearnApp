package com.example.team.comearnapp.engine.white_list_fragment_engine;

import com.example.team.comearnapp.entity.AppInfo;

import java.util.ArrayList;

/**
 * Created by Ellly on 2018/2/20.
 */

public class FragmentWhiteListPresenterForOnline extends FragmentWhiteListPresenter {
    @Override
    public void updateAppList() {
        mView.presentLoading();
        new Thread(){
            @Override
            public void run() {
                final ArrayList<AppInfo> infos = mModel.getAppInfos();
                mUpdateHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.updateAppList(infos);
                        mView.stopLoading();
                    }
                });
            }
        }.start();
    }
}
