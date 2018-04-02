package com.example.team.wang.engine.fragment.white_list;

import com.example.team.wang.entity.AppInfo;

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
