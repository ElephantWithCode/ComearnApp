package com.example.team.comearnapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import com.example.team.comearnapp.R;
import com.example.team.comearnapp.bean.SearchUser;
import com.example.team.comearnapp.tool.RecyclerViewCommonTool.CommonAdapter;
import com.example.team.comearnapp.tool.RecyclerViewCommonTool.ViewHolder;
import com.example.team.comearnapp.ui.ClearEditText;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * 搜索页面
 * 是查看用户信息（？）的入口
 *查看和群组信息（SearchResultActivity）的入口
 * */
public class SearchActivity extends AppCompatActivity{


    ClearEditText mClearEditText;

    QMUIGroupListView mAboutGroupListView;

    RecyclerView mRecyclerView;
    private CommonAdapter adapter_user;
    private CommonAdapter adapter_class;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_fragment_search);
        ButterKnife.bind(this);

        mClearEditText = findViewById(R.id.clearEditText);

        mAboutGroupListView = findViewById(R.id.about_list);

        mRecyclerView = findViewById(R.id.act_search_rv);

        StatusBarUtil.setColor(SearchActivity.this, getResources().getColor(R.color.green), 50);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("添加好友/群组");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        DividerItemDecoration mDecoration = new DividerItemDecoration(SearchActivity.this, DividerItemDecoration.VERTICAL);
        mDecoration.setDrawable(ContextCompat.getDrawable(SearchActivity.this, R.drawable.divider_bg));
        mRecyclerView.setHasFixedSize(true);

        ArrayList<SearchUser> checksArrayList=new ArrayList<SearchUser>();
        checksArrayList.add(new SearchUser());

        adapter_user=new CommonAdapter<SearchUser>(SearchActivity.this, R.layout.user_item, checksArrayList){
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }
            @Override
            public void convert(ViewHolder holder, SearchUser user) {
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(SearchActivity.this,SearchResultActivity.class);
                        startActivity(intent);
                        //TODO：汪工在这里跳转到他人用户信息页面
                    }
                });
            }
        };

        adapter_class=new CommonAdapter<SearchUser>(SearchActivity.this, R.layout.class_item, checksArrayList){
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }

            @Override
            public void convert(ViewHolder holder, SearchUser user) {


                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(SearchActivity.this,SearchResultActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };

        // 搜索框的键盘搜索键点击回调
        mAboutGroupListView.setVisibility(View.GONE);
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mAboutGroupListView.getVisibility()==View.GONE){
                    mRecyclerView.setAdapter(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    mAboutGroupListView.setVisibility(View.GONE);
                } else {
                    mAboutGroupListView.setVisibility(View.VISIBLE);
                }
            }
        });


        QMUIGroupListView.newSection(SearchActivity.this)
                .addItemView(mAboutGroupListView.createItemView("查找学呗用户"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAboutGroupListView.setVisibility(View.GONE);
                        mRecyclerView.setAdapter(adapter_user);
                    }
                })
                .addItemView(mAboutGroupListView.createItemView("查找班级群组"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAboutGroupListView.setVisibility(View.GONE);
                        mRecyclerView.setAdapter(adapter_class);
                    }
                })
                .addItemView(mAboutGroupListView.createItemView("查找自习群组"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAboutGroupListView.setVisibility(View.GONE);
                        mRecyclerView.setAdapter(adapter_class);
                    }
                })
                .addTo(mAboutGroupListView);



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
