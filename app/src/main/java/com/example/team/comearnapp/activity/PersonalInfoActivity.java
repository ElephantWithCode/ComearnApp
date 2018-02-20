package com.example.team.comearnapp.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.entity.GroupInfo;
import com.example.team.comearnapp.ui.CompoundTextLayout;
import com.example.team.comearnapp.ui.GroupInfoPresentView;
import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.personalspacelib.test.DefaultCustomCardView;
import com.example.team.personalspacelib.test.DefaultCustomCollapsingToolbarLayout;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.ArrayList;

class PersonalInfoModel extends BaseModel{
    public GroupInfo[] obtainGroupInfos(){
        GroupInfo[] infos = new GroupInfo[10];
        infos[0] = new GroupInfo("哈哈哈01");
        infos[1] = new GroupInfo("哈哈哈02");
        infos[2] = new GroupInfo("哈哈哈03");
        infos[3] = new GroupInfo("哈哈哈03");
        infos[4] = new GroupInfo("哈哈哈03");
        infos[5] = new GroupInfo("哈哈哈03");
        infos[6] = new GroupInfo("哈哈哈03");
        infos[7] = new GroupInfo("哈哈哈03");
        infos[8] = new GroupInfo("哈哈哈03");
        infos[9] = new GroupInfo("哈哈哈03");
        return infos;
    }
}

class PersonalInfoPresenter extends BasePresenter<PersonalInfoView>{
    private PersonalInfoModel mInfoModel = new PersonalInfoModel();

    public void fetchGroupInfos(){
        GroupInfo[] infos = mInfoModel.obtainGroupInfos();
        mView.inflateGroupList(infos);
    }

}

class WidgetsManager{
    private Activity mActivity;
    private SparseArray<View> mViewArray = new SparseArray<>();

    WidgetsManager(){}

    public void attachToActivity(Activity activity){
        this.mActivity = activity;
        addViewGroup(mActivity.findViewById(android.R.id.content));
    }

    public void detach(){
        this.mActivity = null;
        this.mViewArray.clear();
    }

    public void addViewGroup(View viewGroup){
        if (viewGroup instanceof ViewGroup){
            for (int i = 0; i < ((ViewGroup) viewGroup).getChildCount(); i++){
                View v = ((ViewGroup) viewGroup).getChildAt(i);
                addViewGroup(v);
            }
        }
        mViewArray.put(viewGroup.getId(), viewGroup);
    }

    public View addView(int id){
        mViewArray.put(id, mActivity.findViewById(id));
        return mActivity.findViewById(id);
    }

    public View addView(String mark, View view){
        mViewArray.put(mark.hashCode(), view);
        return view;
    }

    public View getView(int id){
        return mViewArray.get(id);
    }

    public View getView(String mark){
        return mViewArray.get(mark.hashCode());
    }

}

interface PersonalInfoView extends IBaseView{
    void inflateGroupList(GroupInfo[] infos);
}

public class PersonalInfoActivity extends AppCompatActivity implements PersonalInfoView{

    private WidgetsManager mViewManager = new WidgetsManager();
    private PersonalInfoPresenter mPresenter = new PersonalInfoPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

//        QMUIStatusBarHelper.translucent(this); //沉浸式状态栏

        attachWidgets();

        initUI();

        mPresenter.fetchGroupInfos();
    }

    private void initUI() {
        mViewManager.addView("clps_bg", View.inflate(this, R.layout.activity_personal_info_collapse_background, null));
        mViewManager.addView("clps", new DefaultCustomCollapsingToolbarLayout(this, mViewManager.getView("clps_bg")));

        {
            DefaultCustomCollapsingToolbarLayout mCollapseView = (DefaultCustomCollapsingToolbarLayout) mViewManager.getView("clps");
                mCollapseView.setContentScrimColor(Color.parseColor("#4caf50"));
                mCollapseView.setTitle("亮神牛b！");
                mCollapseView.setSupportActionBar(this);
        }

        ((AppBarLayout)mViewManager.getView(R.id.act_personal_info_abl)).addView(mViewManager.getView("clps"));

        ArrayList<View> views = new ArrayList<View>();

        views.add(mViewManager.addView("txt_age", new CompoundTextLayout(this, "年龄")));
        views.add(mViewManager.addView("txt_gender", new CompoundTextLayout(this, "性别")));
        views.add(mViewManager.addView("txt_university", new CompoundTextLayout(this, "学校")));
        views.add(mViewManager.addView("txt_school", new CompoundTextLayout(this, "学院")));
        views.add(mViewManager.addView("txt_major", new CompoundTextLayout(this, "专业")));

        ((DefaultCustomCardView)mViewManager.getView(R.id.act_personal_info_nsv_dccv_hold))
                .setHeadText("基本信息").addViewList(views);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachWidgets();
    }

    private void attachWidgets() {
        mPresenter.attachView(this);
        mViewManager.attachToActivity(this);
    }

    private void detachWidgets() {
        mViewManager.detach();
        mPresenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void inflateGroupList(GroupInfo[] infos) {
        ArrayList<View> views = new ArrayList<>();

        for (GroupInfo info : infos){
            GroupInfoPresentView view = new GroupInfoPresentView(this);
            view.setGroupInfo(info);
            views.add(view);
        }

        ((DefaultCustomCardView)mViewManager.getView(R.id.act_personal_info_nsv_dccv_group_list))
                .setHeadText("加入的群组").addViewList(views);
    }
}
