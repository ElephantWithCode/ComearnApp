package com.example.team.personalspacelib.test;

import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.example.team.personalspacelib.R;

/**
 * Created by Ellly on 2018/2/8.
 */

public class DefaultCustomCollapsingToolbarLayout extends CollapsingToolbarLayout {

    private Context mContext;

    private Toolbar mDefaultToolbar;
    private View mToCollapseView;

    public DefaultCustomCollapsingToolbarLayout(Context context, View view) {
        super(context);
        init(context, view);
    }

    public DefaultCustomCollapsingToolbarLayout(Context context, AttributeSet attrs, View view) {
        super(context, attrs);
        init(context, view);
    }

    public DefaultCustomCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr, View view) {
        super(context, attrs, defStyleAttr);
        init(context, view);
    }

    private void init(Context context, View view) {
        mContext = context;
        mToCollapseView = view;
        mDefaultToolbar = (Toolbar) LayoutInflater.from(context).inflate(R.layout.toolbar_in_clpstbl_default, null, false);
                                            //这里主要是不知道怎么代码获得?attr/actionBarSize值
                                            //TODO 考虑以后修改。
        resetViewCollapseMode(mToCollapseView, LayoutParams.COLLAPSE_MODE_PARALLAX);
        resetViewCollapseMode(mDefaultToolbar, LayoutParams.COLLAPSE_MODE_PIN);
        removeAllViews();                   //这里先把拥有（xml里面可能预设的）
        addView(mToCollapseView);           //的子View全清空，
        addView(mDefaultToolbar);           //防止再添加时出现冲突。
    }

    /**
     * 把View的参数添加一个"CollapseMode"。
     * @param view 待修改的View。
     */
    private void resetViewCollapseMode(View view, int mode) {
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

}
