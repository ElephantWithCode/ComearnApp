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
import com.example.team.comearnapp.bean.Group;
import com.example.team.comearnapp.bean.SearchUser;
import com.example.team.comearnapp.bean.User;
import com.example.team.comearnapp.util.RecyclerViewCommonTool.CommonAdapter;
import com.example.team.comearnapp.util.RecyclerViewCommonTool.ViewHolder;
import com.example.team.comearnapp.ui.ClearEditText;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.ArrayList;
import java.util.List;

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
initViews();
setOnClickListeners();
searchResults();
    }

    /**
     * 初始化view布局
     */
    private void initViews(){
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

        // 搜索框的键盘搜索键点击回调
        mAboutGroupListView.setVisibility(View.GONE);
    }

    /**
     * 设置点击事件回调
     */
    private void setOnClickListeners(){
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
    }

    /**
     * 根据用户输入的结果在指定范围内进行搜索
     */
    private void searchResults(){
        QMUIGroupListView.newSection(SearchActivity.this)
                .addItemView(mAboutGroupListView.createItemView("查找学呗用户"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAboutGroupListView.setVisibility(View.GONE);
                        /**
                         * 设置查询到的用户列表结果
                         * 暂时只写用昵称搜索到的结果
                         * @param checksArrayList:搜索得到的用户列表
                         */
                        List<User> checksArrayList=new ArrayList<User>();
                        checksArrayList = getSearchUsers(mClearEditText.getText().toString());
                        /**
                         * 配置搜索个人用户的列表
                         */
                        adapter_user=new CommonAdapter<User>(SearchActivity.this, R.layout.user_item, checksArrayList){
                            @Override
                            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                                super.onBindViewHolder(viewHolder, position);
                            }
                            @Override
                            public void convert(ViewHolder holder, User user) {
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
                        mRecyclerView.setAdapter(adapter_user);
                    }
                })
                .addItemView(mAboutGroupListView.createItemView("查找班级群组"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAboutGroupListView.setVisibility(View.GONE);
                        //搜索得到的群组列表
                        List<Group> checksArrayList=new ArrayList<Group>();
                        checksArrayList = getSearchClassGroups(mClearEditText.getText().toString());
                        adapter_class=new CommonAdapter<Group>(SearchActivity.this, R.layout.class_item, checksArrayList){
                            @Override
                            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                                super.onBindViewHolder(viewHolder, position);
                            }

                            @Override
                            public void convert(ViewHolder holder, Group user) {


                                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(SearchActivity.this,SearchResultActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        };

                        mRecyclerView.setAdapter(adapter_class);
                    }
                })
                .addItemView(mAboutGroupListView.createItemView("查找自习群组"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAboutGroupListView.setVisibility(View.GONE);
                        //搜索得到的用户列表
                        List<Group> checksArrayList=new ArrayList<Group>();
                        checksArrayList = getSearchStudyByOnselfGroups(mClearEditText.getText().toString());
                        adapter_class=new CommonAdapter<Group>(SearchActivity.this, R.layout.class_item, checksArrayList){
                            @Override
                            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                                super.onBindViewHolder(viewHolder, position);
                            }

                            @Override
                            public void convert(ViewHolder holder, Group user) {


                                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent(SearchActivity.this,SearchResultActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        };

                        mRecyclerView.setAdapter(adapter_class);
                    }
                })
                .addTo(mAboutGroupListView);
    }

    /**
     * 从服务器查询带有指定关键词的用户
     * @param searchstr 关键词
     * @return 用户列表
     */
    private List<User> getSearchUsers(String searchstr){
        List<User> resultUsers = new ArrayList<>();
        //TODO:网络
        return resultUsers;
    }
    /**
     * 从服务器查询带有指定关键词的用户
     * @param searchstr 关键词
     * @return 用户列表
     */
    private List<Group> getSearchClassGroups(String searchstr){
        List<Group> resultUsers = new ArrayList<>();

        return resultUsers;
    }
    /**
     * 从服务器查询带有指定关键词的用户
     * @param searchstr 关键词
     * @return 用户列表
     */
    private List<Group> getSearchStudyByOnselfGroups(String searchstr){
        List<Group> resultUsers = new ArrayList<Group>();

        return resultUsers;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
                default:
        }
        return super.onOptionsItemSelected(item);
    }
}
