package com.example.team.comearnapp.activity.wang_in_liao;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.comearnlib.base.mvp_mode.BaseActivity;
import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.commonlibrary.base.util.Retrofit.bean.User;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class ChooseFriendsModel extends BaseModel{

}

interface ChooseFriendsView extends IBaseView{

}

class ChooseFriendsPresenter extends BasePresenter<ChooseFriendsView>{

    public void uploadSelected(ArrayList<User> selected_users) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                //TODO ：理论上邹神这里也可以处理已经选择的用户（在CreateClassActivity 里面也有这部分的处理接口，邹神选择一个应该即可）
            }
        });
    }
}

class ChooseFriendsAdapter extends SuperAdapter<User>{

    public boolean[] getFlags(){
        return flags;
    }

    boolean[] flags = new boolean[10];

    public ChooseFriendsAdapter(Context context, List<User> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    public ChooseFriendsAdapter(Context context, List<User> items, IMulItemViewType<User> mulItemViewType) {
        super(context, items, mulItemViewType);
    }

    @Override
    public void notifyDataSetHasChanged() {
        super.notifyDataSetHasChanged();
        flags = new boolean[getItemCount()];
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, final int layoutPosition, User item) {
        TextView userName = (TextView) holder.findViewById(R.id.act_f_m_i_tv_name);
        TextView userInfo = (TextView) holder.findViewById(R.id.act_f_m_i_tv_messages);
        CircleImageView userPortrait = (CircleImageView) holder.findViewById(R.id.act_f_m_i_ci);
        CheckBox checkBox = (CheckBox) holder.findViewById(R.id.act_f_m_i_cb);

        userName.setText(item.getUsername());
        userInfo.setText(item.getEmail());


        checkBox.setOnCheckedChangeListener(null);
        checkBox.setChecked(flags[layoutPosition]);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flags[layoutPosition] = isChecked;
            }
        });



    }
}

public class ChooseFriendsActivity extends BaseActivity implements ChooseFriendsView{

    ChooseFriendsPresenter mPresenter = new ChooseFriendsPresenter();

    RecyclerView mRecyView;

    FloatingActionButton mBtn;

    List<User> mData = new ArrayList<>();
    private ChooseFriendsAdapter mAdapter;

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.attachView(this);

        mRecyView = findViewById(R.id.act_c_f_rv);
        mRecyView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new ChooseFriendsAdapter(this, mData, R.layout.item_act_choose_friends);

        mBtn = findViewById(R.id.act_c_f_fab);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean[] flags = mAdapter.getFlags();

                ArrayList<User> selected_users = new ArrayList<>();
                for (int i = 0; i < mData.size(); i++){
                    if (flags[i]){
                        selected_users.add(mData.get(i));
                    }
                }
                mPresenter.uploadSelected(selected_users);
                Intent data = new Intent();
                data.putExtra("selected_users", selected_users);
                setResult(RESULT_OK, data);
                finish();
            }
        });


    }

    @Override
    protected String getTitleString() {
        return "选择好友";
    }

    @Override
    protected int getTopBarId() {
        return R.id.act_c_f_tb;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_choose_friends;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
