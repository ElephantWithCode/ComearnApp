package com.example.team.comearnapp.activity.wang_in_liao;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.team.comearnapp.R;
import com.example.team.comearnapp.activity.SearchActivity;
import com.example.team.comearnapp.util.RecyclerViewCommonTool.CommonAdapter;
import com.example.team.comearnapp.util.RecyclerViewCommonTool.ViewHolder;
import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.comearnlib.utils.ToastTools;
import com.example.team.commonlibrary.base.application.MyApp;
import com.example.team.commonlibrary.base.util.DbUtil;
import com.example.team.commonlibrary.base.util.Retrofit.RetroHttpUtil;
import com.example.team.commonlibrary.base.util.Retrofit.bean.BaseResponse;
import com.example.team.commonlibrary.base.util.Retrofit.bean.FriendTest;
import com.example.team.commonlibrary.base.util.Retrofit.bean.User;
import com.example.team.commonlibrary.base.util.Retrofit.callback.AbstractCommonHttpCallback;
import com.example.team.commonlibrary.base.util.ToastUtil;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.pullRefreshLayout.QMUIPullRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

class FriendsListModel extends BaseModel {
    /**
     * TODO:返回的好友列表
     */
    List<FriendTest> friendTestList = new ArrayList<>();

    public ArrayList<User> getUsers() {
        /**
         * TODO:暂时从本地数据库拿用户ID
         */
        Call<BaseResponse<List<FriendTest>>> getFriendListCall = RetroHttpUtil.build().getFriendListCall(DbUtil.getString(MyApp.getGlobalContext(), "user_id", "null"));
        RetroHttpUtil.sendRequest(getFriendListCall, new AbstractCommonHttpCallback<BaseResponse<List<FriendTest>>>() {
            @Override
            public void onSuccess(BaseResponse<List<FriendTest>> result) {
                ToastUtil.ToastShortShow("Success!", MyApp.getGlobalContext());
                friendTestList = result.getData();
            }

            @Override
            public void onFail() {
                ToastUtil.ToastShortShow("Fail", MyApp.getGlobalContext());
            }
        });



        return adaptType(friendTestList); // TODO: 邹神这里获取好友信息。
    }

    ArrayList<User> adaptType(List<FriendTest> friendTests){
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < friendTests.size(); i++) {
            User tempUser = new User();
            tempUser.setUsername(friendTests.get(i).getFriendAlias());
        }
        return users;
    }
}

interface FriendsListView extends IBaseView {

    void updateList(ArrayList<User> users);
}

class FriendsListPresenter extends BasePresenter<FriendsListView> {
    FriendsListModel mModel = new FriendsListModel();

    public void updateFriendsList() {
        ArrayList<User> users = mModel.getUsers();
        if (users == null) {
            return;
        }
        mView.updateList(users);
    }
}

public class FriendsListActivity extends AppCompatActivity implements FriendsListView {

    FriendsListPresenter mPresenter = new FriendsListPresenter();

    QMUITopBar mTopBar;

    RecyclerView mRecyView;
    QMUIPullRefreshLayout mRefreshLayout;

    CommonAdapter<User> mAdapter;

    ArrayList<User> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.green), 50);

        mPresenter.attachView(this);

        initUI();

        mPresenter.updateFriendsList(); // 每次进去的时候都会请求一遍数据并刷新
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    private void initUI() {
        mRecyView = findViewById(R.id.act_friends_list_rv);

        mRecyView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRefreshLayout = findViewById(R.id.act_friends_list_prl);

        mRefreshLayout.setOnPullListener(new QMUIPullRefreshLayout.OnPullListener() {
            @Override
            public void onMoveTarget(int offset) {

            }

            @Override
            public void onMoveRefreshView(int offset) {

            }

            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.updateFriendsList();
                        mRefreshLayout.finishRefresh();
                    }
                }, 500);
            }
        });

        mTopBar = findViewById(R.id.act_friends_list_tb);
        mTopBar.setTitle("好友列表").setTextColor(getResources().getColor(R.color.white));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mAdapter = new CommonAdapter<User>(FriendsListActivity.this, R.layout.user_item, mList) {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }

            @Override
            public void convert(ViewHolder holder, User user) {
                TextView nicknameTv = holder.getItemView().findViewById(R.id.type_top_title6);
                nicknameTv.setText(user.getUsername());
                final User id = user;
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build("/wang_part/personal_info").withSerializable("target_user_id", id).navigation();
                        //TODO：汪工在这里跳转到他人用户信息页面
                    }
                });
            }
        };

        mRecyView.setAdapter(mAdapter);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void updateList(ArrayList<User> users) {
        mList.clear();
        mList.addAll(users);
        mAdapter.notifyDataSetChanged();
    }
}
