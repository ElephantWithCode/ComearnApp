package com.example.team.personalspacelib.test;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.team.personalspacelib.R;

import java.util.ArrayList;

/**
 * Created by Ellly on 2018/2/9.
 */

public class DefaultCustomCardView extends CardView {

    public static final int DEFAULT_DIVIDER_PADDING = 2;
    public static  int DEFAULT_MARGIN_TB = 10;
    public static  int DEFAULT_MARGIN_LR = 10;

    public static class WeightedViewWrapper{
        private View mView;
        private float mWeight;
        private int mTopAndBottomMargin;
        private int mLeftAndRightMargin;

        public WeightedViewWrapper(View view, float weight, int topAndBottomMargin, int leftAndRightMargin) {
            this.mView = view;
            this.mWeight = weight;
            this.mLeftAndRightMargin = leftAndRightMargin;
            this.mTopAndBottomMargin = topAndBottomMargin;

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(view.getLayoutParams());
            lp.weight = weight;
            lp.gravity = Gravity.CENTER;
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;// 由于weight的原因
                                                            // 其实height的值并不会起作用
            lp.setMargins(leftAndRightMargin, topAndBottomMargin, leftAndRightMargin, topAndBottomMargin);
            view.setLayoutParams(lp);
        }

        public View getView() {
            return mView;
        }

        public float getWeight() {
            return mWeight;
        }

        public int getTopAndBottomMargin() {
            return mTopAndBottomMargin;
        }

        public int getLeftAndRightMargin() {
            return mLeftAndRightMargin;
        }
    }


    public static final String TAG = "DCCV";
    private LinearLayout mDirectChildLayout;
    private Drawable mDivider;
    private ArrayList<WeightedViewWrapper> mViewWrappers;
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

    public void addViewList(ArrayList<View> views){

        //默认情况下将所有的View的Weight均设为1,margins设为默认值。
        ArrayList<WeightedViewWrapper> wrappers = new ArrayList<>();
        for (View v : views){
            wrappers.add(new WeightedViewWrapper(v, 1, DEFAULT_MARGIN_TB, DEFAULT_MARGIN_LR));
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


    public LinearLayout getDirectChildLayout(){return mDirectChildLayout; }

    public ArrayList<WeightedViewWrapper> getWeightedWrappers(){return mViewWrappers;}
}
