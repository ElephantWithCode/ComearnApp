package com.example.team.personalspacelib.test;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.team.personalspacelib.R;

public class PersonalSpaceActivity extends AppCompatActivity {

    private CoordinatorLayout mCLParent;
    private AppBarLayout mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCLParent = (CoordinatorLayout) LayoutInflater.from(this).inflate(R.layout.activity_personal_space, null, false);
        setContentView(mCLParent);


        mAppBarLayout = findViewById(R.id.act_personal_space_abl_head);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) LayoutInflater.from(this).inflate(R.layout.collapsing_toolbar_layout_default, null, false); // 缺省情况下使用默认layout。

        mAppBarLayout.addView(mCollapsingToolbarLayout);// 目标是为了以后方便扩展。

    }
}
