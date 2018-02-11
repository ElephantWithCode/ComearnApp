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
    public static  int DEFAULT_MARGIN_TB = 0;
    public static  int DEFAULT_MARGIN_LR = 15;

    public static class WeightedViewWrapper{
        private View mView;
        private LinearLayout.LayoutParams mLayoutParams;
        private float mWeight;
        private int mTopAndBottomMargin;
        private int mLeftAndRightMargin;
        private int mGravity;

        @Deprecated
        public WeightedViewWrapper(View view, float weight, int topAndBottomMargin, int leftAndRightMargin) {
            this.mView = view;
            this.mWeight = weight;
            this.mLeftAndRightMargin = leftAndRightMargin;
            this.mTopAndBottomMargin = topAndBottomMargin;

            LinearLayout.LayoutParams lp;
            if (view.getLayoutParams() == null){
                lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            // 由于weight的原因
                            // 其实height的值并不会起作用
            }else {
                lp = new LinearLayout.LayoutParams(view.getLayoutParams());
            }
            lp.weight = weight;
            lp.setMargins(leftAndRightMargin, topAndBottomMargin, leftAndRightMargin, topAndBottomMargin);
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

        public WeightedViewWrapper setMargins(int lrMargins, int tbMargins){
            this.mLeftAndRightMargin = lrMargins;
            mLayoutParams.setMargins(lrMargins, tbMargins, lrMargins, tbMargins);
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
    private ArrayList<WeightedViewWrapper> mViewWrappers;
    private boolean mHeadEnable = false;
    private TextView mHeadText;

    public DefaultCustomCardView(Context context) {
        super(context);
        init(context);
    }

    public DefaultCustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DefaultCustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        initDirectChild(context);

        initSelf();
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
        lp.setMargins(DEFAULT_MARGIN_LR, DEFAULT_MARGIN_TB, DEFAULT_MARGIN_LR, DEFAULT_MARGIN_TB);
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
        if (mHeadEnable) {
            wrappers.add(new WeightedViewWrapper(mHeadText).setWeight(1).setMargins(DEFAULT_MARGIN_LR, DEFAULT_MARGIN_TB).setGravity(Gravity.CENTER).build());
        }
        for (View v : views){
            WeightedViewWrapper wrapper = new WeightedViewWrapper(v).setWeight(1).setMargins(DEFAULT_MARGIN_LR, DEFAULT_MARGIN_TB).build();
            wrappers.add(wrapper);
        }
        mViewWrappers = wrappers;

        addViewsIntoDCLayout(wrappers);
    }

    private void addViewsIntoDCLayout(ArrayList<WeightedViewWrapper> wrappers) {
        try {
            for (WeightedViewWrapper wvw : wrappers){
                mDirectChildLayout.addView(wvw.getView());
            }
        } catch (NullPointerException e) {
            Log.w(TAG, "ArrayList为空");
        }
    }

    public DefaultCustomCardView setHeadTextEnable(boolean enable){
        mHeadEnable = enable;
        if (enable) {
            mHeadText = generateHeadTextView();
        }
        return this;
    }

    public DefaultCustomCardView setHeadText(CharSequence text){
        setHeadTextEnable(true);
        mHeadText.setText(text);
        return this;
    }
}
