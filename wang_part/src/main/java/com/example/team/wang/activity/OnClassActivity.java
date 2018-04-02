package com.example.team.wang.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.team.wang.debug.TestFloatWindowService;
import com.example.team.wang.utils.ClassBehaviorManager;
import com.example.team.wang.utils.FloatWindowManager;
import com.example.team.wang_part.R;
import com.example.team.wang.engine.fragment.class_main.ClassMainFragment;
import com.example.team.wang.engine.fragment.class_main.ClassMainModel;
import com.example.team.wang.engine.fragment.proposing_students.ProposingStudentsFragment;
import com.example.team.wang.engine.fragment.white_list.FragmentWhiteListModelForOnline;
import com.example.team.wang.engine.fragment.white_list.FragmentWhiteListPresenterForOnline;
import com.example.team.wang.engine.fragment.white_list.WhiteListFragment;
import com.example.team.wang.ui.AbstractListActivity;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;

import java.util.Objects;

interface OnClassView extends IBaseView{}

class IntentProcessor{
    private Context mContext;

    IntentProcessor(Context mContext) {
        this.mContext = mContext;
    }

    public void processIntentFromRouter(Intent intent){
        boolean fromRouter = Objects.equals(intent.getStringExtra("on_class_mark"), "on_class_mark");
        if (fromRouter){
            Log.d("OCA", intent.getLongExtra("stop_time", 0) + "");

            ClassBehaviorManager manager = new ClassBehaviorManager(mContext);
            manager.setStartTime(0)
                    .setStopTime(intent.getLongExtra("stop_time", 0))
                    .buildWithExactTime()
                    .triggerCountDown();
        }
    }

    public boolean isRefreshOnClassActivity(Intent intent) {
        return Objects.equals(intent.getAction(), "refresh_on_class_activity");
    }

}

class OnClassPresenter extends BasePresenter<OnClassView>{
    private ClassMainModel mModel;

    public long getStopTime(){
        return mModel.getStopTime();
    }

    public boolean getClassState(){
        return mModel.getClassState();
    }

    public void setStopTime(long stopTime){ mModel.saveStopTime(stopTime);}

    @Override
    public void attachView(OnClassView view) {
        super.attachView(view);
        mModel = new ClassMainModel(mContext);
    }
}

@Route(path = "/wang_part/on_class")
public class OnClassActivity extends AbstractListActivity implements OnClassView{

    private OnClassPresenter mPresenter = new OnClassPresenter();
    private ClassMainFragment mClassMainFragment;
    private IntentProcessor mProcessor = new IntentProcessor(this);

    @SuppressLint("HandlerLeak")
    Handler mWindowHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 123){
                startService(new Intent(OnClassActivity.this, TestFloatWindowService.class));
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.act_on_class_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        mWindowHandler.sendEmptyMessageDelayed(123, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProcessor.processIntentFromRouter(getIntent());

        mCoorTabLayout.setTitle("课堂");
        mViewPager.setOffscreenPageLimit(3);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /*switch (keyCode){
            case KeyEvent.KEYCODE_MENU:
            case KeyEvent.KEYCODE_BACK:
                return true;
        }*/
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mProcessor.isRefreshOnClassActivity(intent)){
            mClassMainFragment.getPresenter().refreshWholeView();
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
        int i = item.getItemId();
        if (i == R.id.act_on_class_item_edit) {
            startActivity(new Intent(this, RefinedClassSettingActivity.class));

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
