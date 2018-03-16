package com.example.team.comearnapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.bean.Class;
import com.example.team.comearnapp.tool.RecyclerViewCommonTool.CommonAdapter;
import com.example.team.comearnapp.tool.RecyclerViewCommonTool.ViewHolder;
import com.example.team.comearnapp.ui.DefaultFooter;
import com.example.team.comearnapp.ui.DefaultHeader;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import com.liaoinstan.springview.widget.SpringView;


import java.util.ArrayList;

/**
 * 课堂模式页面Fragment
 * 是课堂群组（ClassDetailActivity）和群助手（MoreClassActivity）的入口
 * */
public class MainRecyclerViewFragment extends Fragment {
//    @BindView(R.id.pull_to_refresh) QMUIPullRefreshLayout mPullRefreshLayout;

    private CommonAdapter adapter;


    RecyclerView mRecyclerView;

    private View rootView;
    private SpringView springView;

    public static MainRecyclerViewFragment newInstance() {
        return new MainRecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.act_main_fragment_rv, container, false);
        return rootView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


       mRecyclerView=(RecyclerView) rootView.findViewById(R.id.act_class_mode_rv);
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration mDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_bg));
        mRecyclerView.setHasFixedSize(true);

        ArrayList<Class> classArrayList=new ArrayList<Class>();
        classArrayList.add(new Class("16级通信1班","学习本无底，前进莫徬徨。",R.drawable.books,1,true));
        classArrayList.add(new Class("17级计科3班","我们一定要给自己提出这样的任务：第一，学习，第二是学习，第三还是学习。 —— 列宁\n",R.drawable.books,1,false));
        classArrayList.add(new Class("15级经管1班","现在，我怕的并不是那艰苦严峻的生活，而是不能再学习和认识我迫切想了解的世界。对我来说，不学习，毋宁死",R.drawable.books,1,false));

        adapter=new CommonAdapter<Class>(getContext(), R.layout.act_main_class_mode_item, classArrayList){
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }

            @Override
            public void convert(ViewHolder holder, Class mClass) {
                TextView class_name_tv = holder.getView(R.id.act_class_mode_rv_class_name_tv);
                TextView class_brief_tv = holder.getView(R.id.act_class_mode_rv_class_brief_tv);

                ImageView class_state_iv=holder.getView(R.id.act_class_mode_study_state_iv);
                ImageView class_header_iv = holder.getView(R.id.act_class_mode_rv_class_header_iv);

                if(!mClass.isStudying()){
                    class_state_iv.setVisibility(View.GONE);
                }
                class_name_tv.setText(mClass.getmClassName());
                class_brief_tv.setText(mClass.getmClassBrief());
                class_header_iv.setImageDrawable(getResources().getDrawable(mClass.getmClassHeader()));
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(getContext(),ClassDetailActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(adapter);

        //初始化SpringView
        springView = (SpringView) rootView.findViewById(R.id.springview1);
        springView.setHeader(new DefaultHeader(getContext()));
        springView.setFooter(new DefaultFooter(getContext()));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                        Intent intent=new Intent(getContext(),MoreClassActivity.class);
                        startActivity(intent);
                    }
                }, 500);
            }
        });
    }

    }

