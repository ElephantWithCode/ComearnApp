package com.example.team.comearnapp.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.engine.white_list_fragment_engine.FragmentWhiteListModelForOnline;
import com.example.team.comearnapp.engine.white_list_fragment_engine.FragmentWhiteListPresenterForOnline;
import com.example.team.comearnapp.engine.white_list_fragment_engine.WhiteListFragment;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;

import java.util.ArrayList;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

interface OnClassView extends IBaseView{}

public class OnClassActivity extends WhiteListActivity implements OnClassView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_class);

        mCoorTabLayout.setTitle("课堂");

    }

    @Override
    protected void initFragments() {
        mFragments.add(WhiteListFragment.newInstance()
                .setPresenter(new FragmentWhiteListPresenterForOnline())
                .setModel(new FragmentWhiteListModelForOnline()));
        mFragments.add(WhiteListFragment.newInstance(false));
    }

    @Override
    protected void initIndicators() {
        mIndicators.add("extended");
        mIndicators.add("origin");
    }

    @Override
    protected void bindView() {
        mCoorTabLayout = findViewById(R.id.act_on_class_ctl);
        mViewPager = findViewById(R.id.act_on_class_ctl_vp_content);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
