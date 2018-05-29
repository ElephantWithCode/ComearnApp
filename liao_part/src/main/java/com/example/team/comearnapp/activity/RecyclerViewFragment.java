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
import android.widget.Toast;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.util.RecyclerViewCommonTool.CommonAdapter;
import com.example.team.comearnapp.util.RecyclerViewCommonTool.ViewHolder;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.widget.SpringView;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIFollowRefreshOffsetCalculator;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {
    QMUIPullRefreshLayout mPullRefreshLayout;

    private CommonAdapter adapter;
    static class Class{
        private String mClassName;
        private String mClassBrief;
        private long mClassHeader;
        private long mClassId;

        private Class(String mClassName,String mClassBrief,long mClassHeader,long mClassId){
            this.mClassName=mClassName;
            this.mClassBrief=mClassBrief;
            this.mClassHeader=mClassHeader;
            this.mClassId=mClassId;
        }

        public long getmClassHeader() {
            return mClassHeader;
        }

        public long getmClassId() {
            return mClassId;
        }

        public String getmClassBrief() {
            return mClassBrief;
        }

        public String getmClassName() {
            return mClassName;
        }

        public void setmClassBrief(String mClassBrief) {
            this.mClassBrief = mClassBrief;
        }

        public void setmClassHeader(long mClassHeader) {
            this.mClassHeader = mClassHeader;
        }

        public void setmClassId(long mClassId) {
            this.mClassId = mClassId;
        }

        public void setmClassName(String mClassName) {
            this.mClassName = mClassName;
        }
    }

    RecyclerView mRecyclerView;


    public static RecyclerViewFragment newInstance() {
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mRecyclerView = view.findViewById(R.id.act_class_mode_rv);

        mPullRefreshLayout = view.findViewById(R.id.pull_to_refresh);

        mPullRefreshLayout.setRefreshOffsetCalculator(new QMUIFollowRefreshOffsetCalculator());

        mPullRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                mPullRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }
        });





        final SpringView springView=(SpringView) view.findViewById(R.id.springview1);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getGlobalContext(),"正在刷新中",Toast.LENGTH_LONG).show();
//                        springView.onFinishFreshAndLoad();
//                    }
//                }, 200);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"正在加载更多",Toast.LENGTH_LONG).show();
                        springView.onFinishFreshAndLoad();
                    }
                }, 200);
            }
        });

//        springView.setHeader(new AliHeader(getGlobalContext()){
//            @Override
//            public int getDragSpringHeight(View rootView) {
//                return super.getDragSpringHeight(rootView);
//            }
//        });   //参数为：logo图片资源，是否显示文字
        springView.setFooter(new DefaultFooter(getContext()));   //参数为：logo图片资源，是否显示文字
//        springView.setType(SpringView.Type.OVERLAP);


        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        DividerItemDecoration mDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_bg));
        mRecyclerView.setHasFixedSize(true);

        ArrayList<Class> categoryChapters=new ArrayList<Class>();
        categoryChapters.add(new Class("1601013","学习本无底，前进莫徬徨。",R.drawable.demo,1));
        categoryChapters.add(new Class("1701014","我们一定要给自己提出这样的任务：第一，学习，第二是学习，第三还是学习。 —— 列宁\n",R.drawable.demo,1));
        categoryChapters.add(new Class("1602018","现在，我怕的并不是那艰苦严峻的生活，而是不能再学习和认识我迫切想了解的世界。对我来说，不学习，毋宁死",R.drawable.demo,1));
        categoryChapters.add(new Class("1605051","一个研究人员可以居陋巷，吃粗饭，穿破衣，可以得不到社会的承认。但是只要他有时间，他就可以坚持致力于科学研究。一旦剥夺了他的自由时间，他就完全毁了，再不能为知识作贡献。",R.drawable.demo,1));

        adapter=new CommonAdapter<Class>(getContext(), R.layout.act_main_class_mode_item, categoryChapters){
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }

            @Override
            public void convert(ViewHolder holder, Class mClass) {
                TextView class_name_tv = holder.getView(R.id.act_class_mode_rv_class_name_tv);
                TextView class_brief_tv = holder.getView(R.id.act_class_mode_rv_class_brief_tv);
                ImageView class_header_iv = holder.getView(R.id.act_class_mode_rv_class_header_iv);
                class_name_tv.setText(mClass.getmClassName());
                class_brief_tv.setText(mClass.getmClassBrief());
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
    }
}
