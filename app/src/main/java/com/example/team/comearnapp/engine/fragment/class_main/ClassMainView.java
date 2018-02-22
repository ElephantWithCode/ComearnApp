package com.example.team.comearnapp.engine.fragment.class_main;

import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;

/**
 * Created by Ellly on 2018/2/22.
 */
public interface ClassMainView extends IBaseView {
    void startCountDown(long miliseconds);
    long getStopTime();
}
