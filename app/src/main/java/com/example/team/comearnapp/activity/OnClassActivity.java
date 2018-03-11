package com.example.team.comearnapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.engine.fragment.class_main.ClassMainFragment;
import com.example.team.comearnapp.engine.fragment.class_main.ClassMainModel;
import com.example.team.comearnapp.engine.fragment.proposing_students.ProposingStudentsFragment;
import com.example.team.comearnapp.engine.fragment.white_list.FragmentWhiteListModelForOnline;
import com.example.team.comearnapp.engine.fragment.white_list.FragmentWhiteListPresenterForOnline;
import com.example.team.comearnapp.engine.fragment.white_list.WhiteListFragment;
import com.example.team.comearnapp.ui.AbstractListActivity;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;

import java.util.Calendar;
import java.util.Objects;

interface OnClassView extends IBaseView{}

class OnClassPresenter extends BasePresenter<OnClassView>{
    private ClassMainModel mModel;

    public long getStopTime(){
        return mModel.getStopTime();
    }

    public boolean getClassState(){
        return mModel.getClassState();
    }

    @Override
    public void attachView(OnClassView view) {
        super.attachView(view);
        mModel = new ClassMainModel(mContext);
    }
}

public class OnClassActivity extends AbstractListActivity implements OnClassView{

    private OnClassPresenter mPresenter = new OnClassPresenter();
    private ClassMainFragment mClassMainFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.act_on_class_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCoorTabLayout.setTitle("课堂");
        mViewPager.setOffscreenPageLimit(3);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Objects.equals(intent.getAction(), "refresh_on_class_activity")) {
            mClassMainFragment.getPresenter().refreshWholeView();
            Log.d("OCA", "recreated");
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_on_class;
    }

    @Override
    protected void initFragments() {
        mClassMainFragment = ClassMainFragment.newInstance(false, mPresenter.getStopTime());
        mFragments.add(mClassMainFragment);

        mFragments.add(WhiteListFragment.newInstance()
                .setPresenter(new FragmentWhiteListPresenterForOnline())
                .setModel(new FragmentWhiteListModelForOnline())
                .setCheckboxVisibility(false));

        mFragments.add(ProposingStudentsFragment.newInstance());
    }

    @Override
    protected void preSet() {

        mPresenter.attachView(this);

    }

    @Override
    protected void initIndicators() {
        mIndicators.add("课堂");
        mIndicators.add("当前白名单");
        mIndicators.add("想玩手机的同学");
    }

    @Override
    protected void bindView() {
        mCoorTabLayout = findViewById(R.id.act_on_class_ctl);
        mViewPager = findViewById(R.id.act_on_class_ctl_vp_content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.act_on_class_item_edit:
                startActivity(new Intent(this, RefinedClassSettingActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
