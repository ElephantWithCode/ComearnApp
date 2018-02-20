package com.example.team.comearnapp.engine.white_list_fragment_engine;

import com.example.team.comearnapp.entity.AppInfo;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;

import java.util.ArrayList;

/**
 * Created by Ellly on 2018/2/12.
 */

public interface FragmentWhiteListView extends IBaseView {
    void updateAppList(ArrayList<AppInfo> infos);
    void presentLoading();
    void stopLoading();
    boolean appsShowType();
    ArrayList<AppInfo> getInfos();
}
