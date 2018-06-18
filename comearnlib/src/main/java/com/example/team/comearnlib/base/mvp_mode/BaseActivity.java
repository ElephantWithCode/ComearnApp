package com.example.team.comearnlib.base.mvp_mode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.team.comearnlib.R;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

/**
 * Created by Ellly on 2018/6/18.
 */


public abstract class BaseActivity extends AppCompatActivity {
    QMUITopBar mTopBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        mTopBar = findViewById(getTopBarId());

        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLeftImageButtonClicked();
            }
        });

        mTopBar.setTitle(getTitleString()).setTextColor(getResources().getColor(getTitleColorInt()));
        QMUIViewHelper.setBackgroundColorKeepPadding(mTopBar, getResources().getColor(R.color.colorPrimary));

    }

    protected BasePresenter getPresenter(){
        return null;
    }

    private int getTitleColorInt() {
        return R.color.white;
    }

    protected abstract String getTitleString();

    protected void onLeftImageButtonClicked() {
        finish();
    }

    protected abstract int getTopBarId();

    protected abstract int getContentViewId();


}
