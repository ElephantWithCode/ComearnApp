package com.example.team.wang.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.team.wang.engine.fragment.white_list.FragmentWhiteListModelForOnline;
import com.example.team.wang_part.R;
import com.example.team.wang.engine.fragment.white_list.WhiteListFragment;
import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;

import java.util.ArrayList;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

interface WhiteListBaseView extends IBaseView{

}

class WhiteListModel extends BaseModel{

}

class WhiteListPresenter extends BasePresenter<WhiteListBaseView>{

}
@Route(path = "/wang_part/white_list")
public class WhiteListActivity extends AppCompatActivity implements WhiteListBaseView{

    protected CoordinatorTabLayout mCoorTabLayout;
    protected ViewPager mViewPager;

    protected ArrayList<String> mIndicators = new ArrayList<>();
    protected ArrayList<Fragment> mFragments = new ArrayList<>();
    private FloatingActionButton mFloatingActionBtn;
    private WhiteListFragment mNonSystemList;

    private FragmentWhiteListModelForOnline mWhiteListModel = new FragmentWhiteListModelForOnline();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_white_list);

        prepareModel();

        bindView();

        initIndicators();

        initFragments();

        initViewPager();

        initCoorTabLayout();

        initFAB();

    }

    private void prepareModel() {
        mWhiteListModel.attach(this);
    }

    private void initFAB() {
        mFloatingActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WhiteListActivity.this, RefinedClassSettingActivity.class));
                new Thread(){
                    @Override
                    public void run() {
                        mWhiteListModel.setAppInfos(mNonSystemList.getInfos());
                    }
                }.start();
            }
        });
    }

    protected void initIndicators() {
        mIndicators.add("非系统");
        mIndicators.add("系统");
    }

    protected void initFragments() {
        mNonSystemList = WhiteListFragment.newInstance(false);
        mNonSystemList.setCheckboxVisibility(true);
        mFragments.add(mNonSystemList);
        mFragments.add(WhiteListFragment.newInstance(true));
    }

    protected void initViewPager() {
        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mIndicators.get(position);
            }
        };
        mViewPager.setAdapter(pagerAdapter);
    }

    protected void initCoorTabLayout() {
        mCoorTabLayout.setTitle("设置白名单");
        mCoorTabLayout.setTranslucentStatusBar(this);
        mCoorTabLayout.setBackEnable(true);
        mCoorTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mWhiteListModel.detach();
        super.onDestroy();
    }

    protected void bindView() {
        mCoorTabLayout = findViewById(R.id.act_white_list_ctl);
        mViewPager = findViewById(R.id.act_white_list_ctl_vp_content);
        mFloatingActionBtn = findViewById(R.id.act_white_list_fab);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
