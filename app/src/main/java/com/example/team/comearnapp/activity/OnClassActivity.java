package com.example.team.comearnapp.activity;

import android.content.Context;
import android.os.Bundle;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.engine.fragment.class_main.ClassMainFragment;
import com.example.team.comearnapp.engine.fragment.proposing_students.ProposingStudentsFragment;
import com.example.team.comearnapp.engine.fragment.white_list.FragmentWhiteListModelForOnline;
import com.example.team.comearnapp.engine.fragment.white_list.FragmentWhiteListPresenterForOnline;
import com.example.team.comearnapp.engine.fragment.white_list.WhiteListFragment;
import com.example.team.comearnapp.ui.AbstractListActivity;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;

interface OnClassView extends IBaseView{}

public class OnClassActivity extends AbstractListActivity implements OnClassView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCoorTabLayout.setTitle("课堂");

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_on_class;
    }

    @Override
    protected void initFragments() {
        mFragments.add(ClassMainFragment.newInstance());

        mFragments.add(WhiteListFragment.newInstance()
                .setPresenter(new FragmentWhiteListPresenterForOnline())
                .setModel(new FragmentWhiteListModelForOnline())
                .setCheckboxVisibility(false));

        mFragments.add(ProposingStudentsFragment.newInstance());
    }

    @Override
    protected void initIndicators() {
        mIndicators.add("课堂");
        mIndicators.add("当前白名单");
        mIndicators.add("想玩手机的同学");
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
