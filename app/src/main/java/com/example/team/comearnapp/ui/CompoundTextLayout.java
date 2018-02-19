package com.example.team.comearnapp.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Ellly on 2018/2/11.
 */

public class CompoundTextLayout extends LinearLayout {

    public static final int EDIT_MAX_LINES = 1;
    private static int MARGIN = 0;
    private StringBuilder mTextString = new StringBuilder();
    private TextView mTextView;
    private EditText mEditTextView;

    public CompoundTextLayout(Context context) {
        super(context);
        init();
    }

    public CompoundTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CompoundTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CompoundTextLayout(Context context, CharSequence text){
        super(context);
        init();
        mTextView.setText(mTextString.append(text).append("："));

        if (text.toString().contains("年龄")){
            mEditTextView.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }

    private void init(){
        initTextView();

        initEditText();



        setLayoutParams(generateLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void initEditText() {
        mEditTextView = new EditText(getContext());
        mEditTextView.setFocusable(false);
        mEditTextView.setBackground(null);
        mEditTextView.setMaxLines(EDIT_MAX_LINES);
        mEditTextView.setLayoutParams(generateLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addView(mEditTextView);
    }

    private void initTextView() {
        mTextView = new TextView(getContext());
        mTextView.setTextColor(Color.BLACK);
        mTextView.setTextSize(18);
        mTextView.setLayoutParams(generateLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addView(mTextView);
    }

    public CompoundTextLayout setLabelText(CharSequence text){
        mTextView.setText(text);
        return this;
    }

    public CompoundTextLayout setContentText(CharSequence text){
        mEditTextView.setText(text);
        return this;
    }

    @NonNull
    private LayoutParams generateLayoutParams(int width, int height) {
        LayoutParams lp = new LayoutParams(width, height);
//        lp.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
        return lp;
    }
}
