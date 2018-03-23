package com.example.team.comearnapp.test;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.comearnlib.utils.ToastTools;
import com.example.team.personalspacelib.test.DefaultCustomCardView;
import com.example.team.personalspacelib.test.DefaultCustomCollapsingToolbarLayout;

import java.util.ArrayList;

public class ThirdMockActivity extends AppCompatActivity {

    private DefaultCustomCardView mCardView;
    private ArrayList<View> mViews = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_mock);

        AppBarLayout appBarLayout = findViewById(R.id.test_app_bar);

        View tmpView = View.inflate(this, R.layout.test_background, null);
        tmpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastTools.showToast(getApplicationContext(), "Heloo Wolord");
            }
        });
        DefaultCustomCollapsingToolbarLayout collapsingToolbarLayout = new DefaultCustomCollapsingToolbarLayout(this, tmpView);

        collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#4caf50"));
        setSupportActionBar(collapsingToolbarLayout.getToolbar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        appBarLayout.addView(collapsingToolbarLayout);
    }

    /**
     * 从CollapsingToolbarLayout源码下摘录的。
     * ————————————————————
     * 其实找到的是根节点。//错了错了。
     * 这个其实是找的包裹着Toolbar的在CollapsingToolbarLayout下的第一层布局。
     * 而且如果并没有包裹层，即Toolbar的直接父布局就是CollapsingToolbar的话
     * 则返回Toolbar（descendant）。
     * direct是针对CollapsingToolbarLayout而言的
     * @param descendant 目标View
     * @return 找到的View
     */
    private View findDirectChild(final View descendant) {
        View directChild = descendant;
        for (ViewParent p = descendant.getParent(); p != this && p != null; p = p.getParent()) {
            if (p instanceof View) {
                directChild = (View) p;
            }
        }
        return directChild;
    }

}
