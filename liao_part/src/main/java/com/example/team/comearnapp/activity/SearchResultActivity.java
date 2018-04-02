package com.example.team.comearnapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.team.comearnapp.R;
import com.jaeger.library.StatusBarUtil;

import butterknife.ButterKnife;
/**
 * 搜索群组的结果页面
 * */
public class SearchResultActivity extends AppCompatActivity {
    ClassDetailInformationFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_s);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(SearchResultActivity.this, getResources().getColor(R.color.green), 50);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("群组资料");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchFragment = new ClassDetailInformationFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, searchFragment, "f1")
                    .commit();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
