package com.example.team.personalspacelib.test;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.team.personalspacelib.R;

import java.util.ArrayList;

/**
 * Created by Ellly on 2018/2/9.
 */

public class DefaultCustomCardView extends CardView {

    public static final int DEFAULT_DIVIDER_PADDING = 2;
    public static final int RADIUS = 10;
    public static final int ELEVATION = 10;
    public static  int DEFAULT_Padding_TB = 0;
    public static  int DEFAULT_PADDING_LR = 15;

    public static class WeightedViewWrapper{
        private View mView;
        private LinearLayout.LayoutParams mLayoutParams;
        private float mWeight;
        private int mTopAndBottomPadding;
        private int mLeftAndRightPadding;
        private int mGravity;

        @Deprecated
        public WeightedViewWrapper(View view, float weight, int topAndBottomMargin, int leftAndRightMargin) {
            this.mView = view;
            this.mWeight = weight;
            this.mLeftAndRightPadding = leftAndRightMargin;
            this.mTopAndBottomPadding = topAndBottomMargin;

            LinearLayout.LayoutParams lp;
            if (view.getLayoutParams() == null){
                lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            // 由于weight的原因
                            // 其实height的值并不会起作用
            }else {
                lp = new LinearLayout.LayoutParams(view.getLayoutParams());
            }
            lp.weight = weight;
//            lp.setMargins(leftAndRightMargin, topAndBottomMargin, leftAndRightMargin, topAndBottomMargin);
            //考虑兼容性不再设置margin而是padding。

            view.setPadding(leftAndRightMargin + view.getPaddingLeft(),
                            topAndBottomMargin + view.getPaddingTop(),
                            leftAndRightMargin + view.getPaddingRight(),
                            topAndBottomMargin + view.getPaddingBottom());
            view.setLayoutParams(lp);
        }

        public WeightedViewWrapper(View view){
            this.mView = view;
            if (mView.getLayoutParams() != null){
                mLayoutParams = new LinearLayout.LayoutParams(mView.getLayoutParams());
            }else {
                mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }

        public WeightedViewWrapper setWeight(float weight){
            this.mWeight = weight;
            mLayoutParams.weight = weight;
            return this;
        }

        public WeightedViewWrapper setPadding(int lrPadding, int tbPadding){
            this.mLeftAndRightPadding = lrPadding;
            this.mTopAndBottomPadding = tbPadding;
            this.mView.setPadding(lrPadding + mView.getPaddingLeft(),
                    tbPadding + mView.getPaddingTop(),
                    lrPadding + mView.getPaddingRight(),
                    tbPadding + mView.getPaddingBottom());
            return this;
        }

        public WeightedViewWrapper setGravity(int gravity){
            this.mGravity = gravity;
            mLayoutParams.gravity = gravity;
            return this;
        }

        public WeightedViewWrapper build(){
            mView.setLayoutParams(mLayoutParams);
            return this;
        }

        public View getView() {
            return mView;
        }
    }


    public static final String TAG = "DCCV";
    private LinearLayout mDirectChildLayout;
    private Drawable mDivider;
    private ArrayList<WeightedViewWrapper> mViewWrappers = new ArrayList<>();
    private boolean mHeadEnable = false;
    private TextView mHeadText;

    public DefaultCustomCardView(Context context) {
        super(context);
        init();
    }

    public DefaultCustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DefaultCustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        relocateChildViews();
        super.onFinishInflate();
    }

    private void init() {

        initSelf();
        
    }

    private void relocateChildViews() {
        ArrayList<View> childViews = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++){
            childViews.add(getChildAt(i));
        }
        removeAllViews();
        initDirectChild(getContext());
        addViewList(childViews);
    }

    private void initSelf() {
        setRadius(RADIUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(ELEVATION);
        }
    }

    private void initDirectChild(Context context) {
        mDirectChildLayout = new LinearLayout(context);
        mDirectChildLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        //源码中使用的是位与，
        //所以要保证既Begin又Middle
        //则应传'3' -->（即二进制下）0011
        //既位与1为真
        //也位与2为真（认为非0即为真）
        //这里只想在中间加，所以直接传值即可。
        mDivider = ContextCompat.getDrawable(context, R.drawable.divider_in_linear_card_default);
        mDirectChildLayout.setDividerDrawable(mDivider);
        mDirectChildLayout.setDividerPadding(DEFAULT_DIVIDER_PADDING);
        mDirectChildLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(DEFAULT_PADDING_LR, DEFAULT_Padding_TB, DEFAULT_PADDING_LR, DEFAULT_Padding_TB);
        mDirectChildLayout.setLayoutParams(lp);

        addView(mDirectChildLayout);
    }

    private TextView generateHeadTextView(){
        TextView tv = new TextView(getContext());
        tv.setText("标题");
        tv.setTextSize(15);
        tv.setTextColor(Color.BLACK);
        tv.setPadding(0, 10, 0, 10);
        return tv;
    }

    /**
     * @param views
     */
    public void addViewList(ArrayList<View> views){

        //默认情况下将所有的View的Weight均设为1,margins设为默认值。
        ArrayList<WeightedViewWrapper> wrappers = new ArrayList<>();
        /*if (mHeadEnable) {
            wrappers.add(new WeightedViewWrapper(mHeadText).setWeight(1).setPadding(DEFAULT_PADDING_LR, DEFAULT_Padding_TB).setGravity(Gravity.CENTER).build());
        }*/
        for (View v : views){
            WeightedViewWrapper wrapper = new WeightedViewWrapper(v).setWeight(1).setPadding(DEFAULT_PADDING_LR, DEFAULT_Padding_TB).build();
            wrappers.add(wrapper);
        }
        mViewWrappers.addAll(wrappers);

        addViewsIntoDCLayout(wrappers);
    }

    public void addSingleView(View view){
        ArrayList<View> views = new ArrayList<>();
        views.add(view);
        addViewList(views);
    }

    private void addViewsIntoDCLayout(ArrayList<WeightedViewWrapper> wrappers) {
        try {
            for (WeightedViewWrapper wvw : wrappers){
                mDirectChildLayout.addView(wvw.getView());
            }
        } catch (NullPointerException e) {
            Log.w(TAG, "ArrayList为空");
            throw e;
        }
    }

    /**
     * 设置头部文字是否存在。
     * <strong>设置存在时可以直接设置{@link #setHeadText(CharSequence)}
     * 内部直接设置了此方法为true</strong>
     * @param enable 设置值
     * @return 为了支持链式调用。
     */
    public DefaultCustomCardView setHeadTextEnable(boolean enable){
        mHeadEnable = enable;
        if (enable) {
            mHeadText = generateHeadTextView();
        }else {
            mDirectChildLayout.removeView(mHeadText);
        }
        return this;
    }

    /**
     * 添加一个头部文字
     * <strong>PS：只在有内部LinearLayout至少有一个子View的条件下成立</strong>
     * @param text 标题
     * @return 为了支持链式调用
     */
    public DefaultCustomCardView setHeadText(CharSequence text){
        setHeadTextEnable(true);
        mHeadText.setText(text);
        mDirectChildLayout.addView(new WeightedViewWrapper(mHeadText).setWeight(1).setPadding(DEFAULT_PADDING_LR, DEFAULT_Padding_TB).setGravity(Gravity.CENTER).build().getView(), 0);

        return this;
    }

    public DefaultCustomCardView setHeadTextColor(int color){
        if (mHeadText != null) {
            mHeadText.setTextColor(color);
        }
        return this;
    }
}
