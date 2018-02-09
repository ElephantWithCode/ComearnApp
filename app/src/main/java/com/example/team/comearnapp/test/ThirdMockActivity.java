package com.example.team.comearnapp.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.personalspacelib.test.DefaultCustomCardView;

import java.util.ArrayList;

public class ThirdMockActivity extends AppCompatActivity {

    private DefaultCustomCardView mCardView;
    private ArrayList<View> mViews = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_mock);



        mCardView = findViewById(R.id.act_third_dccv_test);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv1 = new TextView(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        tv1.setText("hello world");
        tv2.setText("hello world");
        tv3.setText("hello world");
        tv1.setLayoutParams(lp);
        tv2.setLayoutParams(lp);
        tv3.setLayoutParams(lp);
        mViews.add(tv1);
        mViews.add(tv2);
        mViews.add(tv3);
        mCardView.addViewList(mViews);
    }
}
