package com.example.team.wang.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.wang_part.R;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;


class AddFriendModel extends BaseModel{
    public void sendOutMessage(String text, Context context){
        //TODO 跟Personal里面的一样，可复制
    }
}

class AddFriendPresenter extends BasePresenter<AddFriendView>{
    private AddFriendModel mModel = new AddFriendModel();
    public void sendOutMessage(String text){
        mModel.sendOutMessage(text, mContext);
    }
}

interface AddFriendView extends IBaseView{

}

public class AddFriendActivity extends AppCompatActivity implements AddFriendView{

    private QMUITopBar mTopBar;
    private AddFriendPresenter mPresenter = new AddFriendPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        mPresenter.attachView(this);

        StatusBarUtil.setColor(AddFriendActivity.this, getResources().getColor(R.color.green), 50);

        initUI();

        initListeners();
    }

    private void initListeners() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTopBar.addRightTextButton("发送", 123).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sendOutMessage("");
            }
        });
    }

    private void initUI() {
        mTopBar = findViewById(R.id.act_add_friend_top_bar);

    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
