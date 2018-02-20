package com.example.team.comearnapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

/**
 * Created by Ellly on 2018/2/20.
 */

public abstract class AbstractListActivity extends AppCompatActivity {
    protected CoordinatorTabLayout mCoorTabLayout;
    protected ViewPager mViewPager;

    protected ArrayList<String> mIndicators = new ArrayList<>();
    protected ArrayList<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResId());

        bindView();

        initIndicators();

        initFragments();

        initViewPager();

        initCoorTabLayout();


    }

    protected abstract int getLayoutResId();

    protected abstract void initFragments();

    protected abstract void initIndicators();

    protected abstract void bindView();


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

}
