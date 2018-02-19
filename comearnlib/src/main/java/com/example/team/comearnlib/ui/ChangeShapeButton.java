package com.example.team.comearnlib.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

/**
 * Created by Ellly on 2018/2/14.
 */

public class ChangeShapeButton extends android.support.v7.widget.AppCompatButton {

    public static final String TAG = "CSB";
    private GradientDrawable mToAnimateDrawable;
    private float mCurrentRadius;
    private int mCurrentHeight;
    private int mCurrentWidth;
    private float mCurrentX;
    private float mCurrentY;

    private float mAimedRadius;
    private float mAimedX;
    private float mAimedY;
    private int mAimedColor;

    private ValueAnimator mShrinkAnimator;
    private ValueAnimator mSmallizeAnimator;
    private ValueAnimator mTransitionYAnimator;
    private ValueAnimator mTransitionXAnimator;
    private ObjectAnimator mRotateAnimator;
    private ValueAnimator mMinimizeAnimator;

    private View mParentView;
    private View mDrawCircleView;
    /**
     * 向外界提供修改的变量。
     */
    private int mDefaultColor = Color.parseColor("#47bafe");
    private float mCircleRadius;
    private float mDefaultRadius;
    private int mParentCenterWidth;
    private int mParentCenterHeight;
    private int mAimedSize;


    public ChangeShapeButton(Context context) {
        super(context);
        initOnClickListener();
        initDrawView();
    }

    public ChangeShapeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initOnClickListener();
        initDrawView();
    }

    public ChangeShapeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initOnClickListener();
        initDrawView();
    }

    private void initDrawView() {
        mDrawCircleView = new View(getContext()){
            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
            }
        };
    }

    private void init() {

        initToAnimateDrawableAndRadius();

        initLocations();

        initParentView();

        initAfterMeasuredValues();

        initAnimators();

    }

    private void initOnClickListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                startAnimators();
            }
        });
    }

    private void startAnimators() {
        AnimatorSet shrinkSet = new AnimatorSet();
        AnimatorSet transitSet = new AnimatorSet();
        AnimatorSet set = new AnimatorSet();
        shrinkSet.setDuration(800);
        transitSet.setDuration(800);
        shrinkSet.playTogether(mShrinkAnimator, mSmallizeAnimator);
        transitSet.playTogether(mMinimizeAnimator, mTransitionYAnimator);
        set.playSequentially(shrinkSet, mMinimizeAnimator, mTransitionXAnimator, mTransitionYAnimator, mRotateAnimator);
        set.start();
    }

    private void initAfterMeasuredValues() {
        mCircleRadius = ((mParentView.getHeight() > mParentView.getWidth()) ? mParentView.getWidth() : mParentView.getHeight()) / 3;
        mAimedRadius = mCircleRadius;
        mAimedSize = mCurrentHeight / 2;

        mParentCenterWidth = mParentView.getWidth() / 2;
        mParentCenterHeight = mParentView.getHeight() / 2;

        mAimedX = mParentCenterWidth - mAimedSize / 2;     //因为button并不是以中心为测量起点
                                                           //而是以左上角，所以减去自身高度一半（即动画后的半径）
        mAimedY = mParentCenterHeight - mAimedRadius;

    }

    private void initAnimators() {
        initShrinkAnimator();

        initSmallizeAnimator();

        initMinimizeAnimator();

        initTransitionYAnimator();

        initTransitionXAnimator();

        initRotateAnimator();
    }

    private void initMinimizeAnimator() {
        mMinimizeAnimator = ValueAnimator.ofInt(mCurrentHeight, mAimedSize);
        mMinimizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                getLayoutParams().width = value;
                getLayoutParams().height = value;
                requestLayout();
            }
        });
    }

    private void initRotateAnimator() {
        mRotateAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, -360f);
        mRotateAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mRotateAnimator.setDuration(2000);
        mRotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            }
        });
        setPivotX(0);
        setPivotY(mAimedRadius);

    }

    private void initTransitionXAnimator() {
        mTransitionXAnimator = ValueAnimator.ofFloat(mCurrentX, mAimedX);
        mTransitionXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setX(value);
            }
        });
    }

    private void initTransitionYAnimator() {
        mTransitionYAnimator = ValueAnimator.ofFloat(mCurrentY, mAimedY);
        mTransitionYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setY(value);
            }
        });
    }

    private void initSmallizeAnimator() {
        mSmallizeAnimator = ValueAnimator.ofInt(mCurrentWidth, mCurrentHeight);
        mSmallizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                getLayoutParams().width = value;
                requestLayout();
            }
        });
    }

    private void initShrinkAnimator() {
        mShrinkAnimator = ValueAnimator.ofFloat(mCurrentRadius, mAimedRadius);
        mShrinkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mToAnimateDrawable.setCornerRadius(value);
                setBackground(mToAnimateDrawable);
            }
        });
    }

    private void initParentView() {
        mParentView = getParentView();
    }

    private View getParentView() {
        return (View) getParent();
    }

    private void initLocations() {
        mCurrentHeight = getHeight();
        mCurrentWidth = getWidth();

        mCurrentX = getX();
        mCurrentY = getY();
        Log.d(TAG + "_H_W", mCurrentHeight + ":height     width: " + mCurrentWidth);
    }

    @SuppressLint("NewApi")
    private void initToAnimateDrawableAndRadius() {

        try {
            mToAnimateDrawable = (GradientDrawable) getBackground();
            mCurrentRadius = mToAnimateDrawable.getCornerRadius();
            mAimedColor = mToAnimateDrawable.getColor().getDefaultColor();
        } catch (NoSuchMethodError e) {
            mCurrentRadius = mDefaultRadius;
            mAimedColor = mDefaultColor;
        }catch (NullPointerException e){
            mAimedColor = mDefaultColor;
        }catch (ClassCastException e){
            mToAnimateDrawable = new GradientDrawable();
            mCurrentRadius = mDefaultRadius;
            mAimedColor = mDefaultColor;
        }
        mToAnimateDrawable.setColor(mAimedColor);
        mToAnimateDrawable.setCornerRadius(mAimedRadius);
    }

}
