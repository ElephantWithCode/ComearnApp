package com.example.team.personalspacelib.test;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
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
import android.view.WindowManager;

import com.example.team.personalspacelib.R;

/**
 * Created by Ellly on 2018/2/8.
 */

public class DefaultCustomCollapsingToolbarLayout extends CollapsingToolbarLayout {

    public static final String TAG  = "DCCTL";

    private Toolbar mToolbar;
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
        mToolbar = new Toolbar(getContext());
        mToolbar.setLayoutParams(clp);

        resetViewCollapseMode(mToolbar, LayoutParams.COLLAPSE_MODE_PIN);
        resetViewCollapseMode(mToCollapseView, LayoutParams.COLLAPSE_MODE_PARALLAX);

        addView(mToCollapseView);           //的子View全清空，
        addView(mToolbar);           //防止再添加时出现冲突。



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
    public Toolbar getToolbar(){return mToolbar;}

    public void setSupportActionBar(final AppCompatActivity activity){
        activity.setSupportActionBar(mToolbar);
        if (activity.getSupportActionBar() != null){
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setHomeButtonEnabled(true);
            activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.white_back_arrow);
            //TODO 只是不知道怎样把颜色调成白的，应该可以用theme。
        }
        mToolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    /**
     * 设置透明状态栏
     *
     * @param activity 当前展示的activity
     * @return CoordinatorTabLayout
     */
    public DefaultCustomCollapsingToolbarLayout setTranslucentStatusBar(@NonNull Activity activity) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return this;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow()
                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (mToolbar != null) {
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mToolbar.getLayoutParams();
            layoutParams.setMargins(
                    layoutParams.leftMargin,
                    layoutParams.topMargin + getStatusBarHeight(activity),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
        }

        return this;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
