package com.example.team.personalspacelib.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team.personalspacelib.R;

/**
 * Created by Ellly on 2018/2/8.
 */

public class DefaultCustomCollapsingToolbarLayout extends CollapsingToolbarLayout {

    public static final String TAG  = "DCCTL";

    private Toolbar mDefaultToolbar;
    private View mToCollapseView;
    private int mActionBarHeight;

    public DefaultCustomCollapsingToolbarLayout(Context context, View view) {
        super(context);
        init(view);
    }

    public DefaultCustomCollapsingToolbarLayout(Context context, AttributeSet attrs, View view) {
        super(context, attrs);
        init(view);
    }

    public DefaultCustomCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr, View view) {
        super(context, attrs, defStyleAttr);
        init(view);
    }

    private void init(View view) {

        mActionBarHeight = obtainActionBarHeight();
                                            //获得?attr/actionBarSize
        CollapsingToolbarLayout.LayoutParams clp = new CollapsingToolbarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mActionBarHeight);
                                            //这里主要是不知道怎么代码获得?attr/actionBarSize值
                                            //TODO 已经获得了。
        mToCollapseView = view;
        mDefaultToolbar = new Toolbar(getContext());
        mDefaultToolbar.setLayoutParams(clp);

        resetViewCollapseMode(mDefaultToolbar, LayoutParams.COLLAPSE_MODE_PIN);
        resetViewCollapseMode(mToCollapseView, LayoutParams.COLLAPSE_MODE_PARALLAX);

        addView(mToCollapseView);           //的子View全清空，
        addView(mDefaultToolbar);           //防止再添加时出现冲突。



        initSelf();
        initTextColor();
    }

    private int obtainActionBarHeight() {
        TypedValue tv = new TypedValue();
        int actionBarHeight= 0;
        if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)){
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getContext().getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    private void initSelf() {
        AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setScrollFlags(0x3);             //SCROLL|EXIT_UNTIL_COLLAPSED = 0x3

        setLayoutParams(lp);
    }

    private void initTextColor() {
        setExpandedTitleColor(Color.WHITE);
        setCollapsedTitleTextColor(Color.WHITE);
    }

    /**
     * 把View的参数添加一个"CollapseMode"。
     * 同时会将paddingTop参数再加上actionBarSize以避免重叠。
     * @param view 待修改的View。
     */
    private void resetViewCollapseMode(View view, int mode) {
        if (view.getLayoutParams() == null){
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setPadding(view.getPaddingLeft(), mActionBarHeight + view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());    //防止与Toolbar重叠。
            view.setLayoutParams(lp);
            Log.d(TAG, view.getClass().getSimpleName() + "  " + view);
        }
        LayoutParams lp = new LayoutParams(view.getLayoutParams());
        lp.setCollapseMode(mode);
        view.setLayoutParams(lp);
    }

    /**
     * 获得Toolbar的一个引用
     * 主要目的为方便自定义。
     * @return Toolbar
     */
    public Toolbar getToolbar(){return mDefaultToolbar;}

    public void setSupportActionBar(final AppCompatActivity activity){
        activity.setSupportActionBar(mDefaultToolbar);
        if (activity.getSupportActionBar() != null){
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeButtonEnabled(true);
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.white_back_arrow);
            //TODO 只是不知道怎样把颜色调成白的，应该可以用theme。
        }
        mDefaultToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }


}
