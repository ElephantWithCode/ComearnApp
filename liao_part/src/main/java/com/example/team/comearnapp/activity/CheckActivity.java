package com.example.team.comearnapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.tool.RecyclerViewCommonTool.CommonAdapter;
import com.example.team.comearnapp.tool.RecyclerViewCommonTool.ViewHolder;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;

import butterknife.BindView;
/**
 * 考勤记录查看页面
 * */
public class CheckActivity extends AppCompatActivity {

    private android.support.v7.widget.RecyclerView act_check_rv;

    private CommonAdapter adapter;
    RecyclerView mRecyclerView;
    static class check{

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_check);
        StatusBarUtil.setColor(CheckActivity.this, getResources().getColor(R.color.green), 50);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("考勤记录");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.mRecyclerView = (RecyclerView) findViewById(R.id.act_check_rv);

        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        DividerItemDecoration mDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_bg));
        mRecyclerView.setHasFixedSize(true);

        ArrayList<CheckActivity.check> checksArrayList=new ArrayList<CheckActivity.check>();
        checksArrayList.add(new CheckActivity.check());


        adapter=new CommonAdapter<CheckActivity.check>(this, R.layout.act_check_item, checksArrayList){
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }

            @Override
            public void convert(ViewHolder holder, CheckActivity.check mCheck) {

            }
        };

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    }


