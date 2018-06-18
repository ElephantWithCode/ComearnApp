package com.example.team.comearnapp.activity.wang_in_liao;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.team.comearnapp.R;
import com.example.team.comearnapp.util.RecyclerViewCommonTool.CommonAdapter;
import com.example.team.comearnapp.util.RecyclerViewCommonTool.ViewHolder;
import com.example.team.comearnlib.base.mvp_mode.BaseActivity;
import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.commonlibrary.base.util.Retrofit.bean.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


class FriendsMessagesModel extends BaseModel{


    public void handOnReceiveMessage(User user) {
        //TODO：邹神这里处理用户已接受验证
    }

    public void handOnDeleteMessage(User user) {
        //TODO：邹神这里删除验证信息，只需要不再让此条消息出现在该用户的验证信息列表内
    }

    public List<User> fetchAllMessages(){
        return null; // TODO:邹神这里获得所有的用户验证信息
    }
}

class FriendsMessagesPresenter extends BasePresenter<FriendsMessagesView>{

    FriendsMessagesModel mModel = new FriendsMessagesModel();
    /**
     * 用户点击确认接受好友验证信息
     * @param
     */

    public void fetchAllMessages(){
        List<User> users = mModel.fetchAllMessages();
        mView.refreshMessages(users);
    }

    public void handOnReceiveMessage(User user) {
       mModel.handOnReceiveMessage(user);
//       mView.onMessagesReceived();
    }

    public void deleteThisItem(User user) {
        mModel.handOnDeleteMessage(user);
    }
}

interface FriendsMessagesView extends IBaseView{

    void refreshMessages(List<User> users);
}



public class FriendsMessagesActivity extends BaseActivity implements FriendsMessagesView{

    RecyclerView mRecyView;
    CommonAdapter<User> mAdapter;
    ArrayList<User> mList = new ArrayList<>();

    FriendsMessagesPresenter mPresenter = new FriendsMessagesPresenter();

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.attachView(this);

        mRecyView = findViewById(R.id.act_friends_messages_rv);
        mRecyView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new CommonAdapter<User>(FriendsMessagesActivity.this, R.layout.act_friends_messages_item, mList) {


            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }

            @Override
            public void convert(final ViewHolder holder, final User user) {

                //TODO : 注意！ 由于此时的FriendTest没有Serializable，所以暂时全部使用了User
                //TODO ： 应该需要有一个bool标记这个验证申请是否已被通过，来刷新我的UI
                //TODO ：receiveBtn 需要根据这个bool来表示是否可以点击
                TextView nicknameTv = holder.getItemView().findViewById(R.id.act_f_m_i_tv_name);
                nicknameTv.setText(user.getUsername());
                final TextView receiveBtn = holder.getItemView().findViewById(R.id.receive);
                TextView deleteBtn = holder.getItemView().findViewById(R.id.delete);
                CircleImageView portraitView = holder.getItemView().findViewById(R.id.act_f_m_i_ci);

                final User id = user;
                portraitView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ARouter.getInstance().build("/wang_part/personal_info").withSerializable("target_user_id", id).navigation();
                        //TODO：汪工在这里跳转到他人用户信息页面
                    }
                });

                receiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.handOnReceiveMessage(user);
                        receiveBtn.setText("已同意");
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.deleteThisItem(user);
                        mList.remove(holder.getLayoutPosition());
                        mAdapter.notifyDataSetChanged();
                    }
                });

            }
        };

        mRecyView.setAdapter(mAdapter);
        mPresenter.fetchAllMessages();

    }

    @Override
    protected String getTitleString() {
        return "验证消息";
    }

    @Override
    protected int getTopBarId() {
        return R.id.act_friends_messages_tb;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_friends_messages;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void refreshMessages(List<User> users) {
        mList.clear();
        mList.addAll(users);
        mAdapter.notifyDataSetChanged();
    }
}
