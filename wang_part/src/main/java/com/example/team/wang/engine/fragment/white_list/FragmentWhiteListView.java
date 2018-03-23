package com.example.team.wang.engine.fragment.white_list;

import com.example.team.wang.entity.AppInfo;
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
    WhiteListFragment setCheckboxVisibility(boolean visibility);
}
