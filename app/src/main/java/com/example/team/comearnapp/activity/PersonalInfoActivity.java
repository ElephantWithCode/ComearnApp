package com.example.team.comearnapp.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.example.team.comearnapp.R;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.personalspacelib.test.DefaultCustomCollapsingToolbarLayout;

class WidgetsManager{
    private Activity mActivity;
    private SparseArray<View> mViewArray = new SparseArray<>();

    WidgetsManager(Activity mActivity) {
        this.mActivity = mActivity;
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

    public void addView(int id){
        mViewArray.put(id, mActivity.findViewById(id));
    }

    public void addView(String mark, View view){
        mViewArray.put(mark.hashCode(), view);
    }

    public View getView(int id){
        return mViewArray.get(id);
    }

    public View getView(String mark){
        return mViewArray.get(mark.hashCode());
    }

}

public class PersonalInfoActivity extends AppCompatActivity implements IBaseView{

    private WidgetsManager mViewManager = new WidgetsManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        mViewManager.addView(R.id.act_personal_info_abl);
        mViewManager.addView("clps_bg", View.inflate(this, R.layout.activity_personal_info_collapse_background, null));
        mViewManager.addView("clps", new DefaultCustomCollapsingToolbarLayout(this, mViewManager.getView("clps_bg")));

        DefaultCustomCollapsingToolbarLayout mCollapseView = (DefaultCustomCollapsingToolbarLayout) mViewManager.getView("clps");
        mCollapseView.setContentScrimColor(Color.parseColor("#4caf50"));
        mCollapseView.setTitle("亮神牛b！");
        ((AppBarLayout)mViewManager.getView(R.id.act_personal_info_abl)).addView(mViewManager.getView("clps"));
//        mViewManager.addView();
    }

    @Override
    public Context getContext() {
        return this;
    }
}
