package com.example.team.wang.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.team.wang.debug.TestFloatWindowService;
import com.example.team.wang_part.R;
import com.example.team.wang.engine.fragment.class_main.ClassMainModel;
import com.example.team.wang.ui.SeekBarDialogBuilder;
import com.example.team.wang.utils.ClassBehaviorManager;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.comearnlib.utils.ConvertTools;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

interface RefinedClassSettingView extends IBaseView{
    void setSpot(CharSequence spot);
    void setStartTime(CharSequence time);
    void setLastTime(CharSequence time);
    CharSequence getSpot();
    CharSequence getLastTime();
    void showSpotDialog(CharSequence preText);
    void showLastTimeDialog(CharSequence preText);
    void showStartTimeDialog();
    void disableShowButton();
    void showButton();
    void setToolbar();
}

class RefinedClassSettingPresenter extends BasePresenter<RefinedClassSettingView>{
    private ClassMainModel mModel;

    @Override
    public void attachView(RefinedClassSettingView view) {
        super.attachView(view);
        mModel = new ClassMainModel(mContext);
    }


    public void saveStopTime(long time){
        mModel.saveStopTime(time);
//        Intent i = new Intent("update_count_time");
//        mContext.sendBroadcast(i);
    }

    public void saveClassStopTime(long time){
        mModel.saveClassStopTime(time);
    }

    public void saveClassState(boolean state){mModel.saveClassState(state);}

    public void showSpotDialog(){
        mView.showSpotDialog(mView.getSpot());
    }

    public void showLastTimeDialog(){
        mView.showLastTimeDialog(mView.getLastTime());
    }

    public void showStartTimeDialog(){
        mView.showStartTimeDialog();
    }

    public void setToolbar(){mView.setToolbar();}

    public void showFinishButton(){
        if (mModel.getClassState()){
            mView.disableShowButton();
        }else {
            mView.showButton();
        }
    }
}

@Route(path = "/wang_part/class_setting")
public class RefinedClassSettingActivity extends AppCompatActivity implements RefinedClassSettingView{

    private RefinedClassSettingPresenter mPresenter = new RefinedClassSettingPresenter();

    public static final String TAG_POST_CALENDAR = "post_calendar";
    private QMUIGroupListView mGroupListView;

    private TextView mFinishButton;

    private QMUIGroupListView.Section mSpotSection;
    private QMUIGroupListView.Section mTimeSection;

    private QMUICommonListItemView mSpotItemView;
    private QMUICommonListItemView mStartItemView;
    private QMUICommonListItemView mLastItemView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refined_class_setting);



        mPresenter.attachView(this);
        mPresenter.setToolbar();

        mGroupListView = findViewById(R.id.act_refined_class_setting_QMUIGLV);
        mFinishButton = findViewById(R.id.act_refined_class_setting_QMUIRB);

        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mPresenter.saveClassState(false);

                ClassBehaviorManager builder = new ClassBehaviorManager(RefinedClassSettingActivity.this)
                        .setStartTime(mStartItemView.getDetailText())
                        .setLastTime(mLastItemView.getDetailText()).buildWithText();

                builder.triggerCountDown();

                finish();

                Log.d("RCSA", "class stop time" + builder.getClassStopCalendar().toString() +
                        "\n class start time" + builder.getClassStartCalendar().toString());

            }
        });

        mSpotItemView = mGroupListView.createItemView("地点");
        mSpotItemView.setDetailText("信远楼#311");

        mStartItemView = mGroupListView.createItemView("开始时间");
        mStartItemView.setText("开始时间");

        Calendar current_calendar = Calendar.getInstance();
        mStartItemView.setDetailText(ConvertTools.parseTime(current_calendar.get(Calendar.HOUR)) + ":" + ConvertTools.parseTime(current_calendar.get(Calendar.MINUTE)));

        View.OnClickListener onSpotItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showSpotDialog();
            }
        };

        View.OnClickListener onStartTimeItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showStartTimeDialog();
            }
        };


        mLastItemView = mGroupListView.createItemView("持续时间");
        mLastItemView.setDetailText("1  min");


        View.OnClickListener onLastTimeClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showLastTimeDialog();
            }
        };

        mSpotSection = new QMUIGroupListView.Section(this);
        mSpotSection.setTitle("活动地点");
        mSpotSection.setDescription("   ");

        mTimeSection = new QMUIGroupListView.Section(this);
        mTimeSection.setTitle("活动时间");
        mTimeSection.setDescription("   ");

        mSpotSection.addItemView(mSpotItemView, onSpotItemClickListener);

        mTimeSection.addItemView(mStartItemView, onStartTimeItemClickListener);
        mTimeSection.addItemView(mLastItemView, onLastTimeClickListener);


        mSpotSection.addTo(mGroupListView);
        mTimeSection.addTo(mGroupListView);

        mPresenter.showFinishButton();
    }

    public void setToolbar() {
        StatusBarUtil.setColor(RefinedClassSettingActivity.this, getResources().getColor(R.color.colorPrimary), 50);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setSpot(CharSequence spot) {
        mSpotItemView.setDetailText(spot);
    }

    @Override
    public void setStartTime(CharSequence time) {
        mStartItemView.setDetailText(time);
    }

    @Override
    public void setLastTime(CharSequence time) {
        mLastItemView.setDetailText(time);
    }

    @Override
    public CharSequence getSpot() {
        return mSpotItemView.getDetailText();
    }

    @Override
    public CharSequence getLastTime() {
        return mLastItemView.getDetailText();
    }

    @Override
    public void showSpotDialog(CharSequence preText) {
        QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(RefinedClassSettingActivity.this);
        final EditText editText = builder.getEditText();
        editText.setText(preText);
        QMUIDialog inputDialog = builder
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                }).addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        setSpot(editText.getText());
                        dialog.dismiss();
                    }
                }).setTitle("填写地点")
                .create();
        inputDialog.show();
    }

    @Override
    public void showLastTimeDialog(CharSequence preText) {
        SeekBarDialogBuilder builder = new SeekBarDialogBuilder(RefinedClassSettingActivity.this);

        if (!preText.equals("")) {
            builder.setSeekProgress(Integer.parseInt(ConvertTools.pickNumberFromString(preText.toString())));
        }

        final TextView timeTv = builder.getTimeShowTv();


        QMUIDialog dialog = builder
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        setLastTime(timeTv.getText());
                        dialog.dismiss();
                    }
                })
                .setTitle("选择时间")
                .create();
        dialog.show();
    }

    @Override
    public void showStartTimeDialog() {
        TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                setStartTime(ConvertTools.parseTime(hourOfDay) + ":" + ConvertTools.parseTime(minute));
            }
        }, true);
        tpd.vibrate(false);
        tpd.show(getFragmentManager(), "time picker dialog");
    }

    @Override
    public void disableShowButton() {
        mFinishButton.setVisibility(View.GONE);
        mGroupListView.setClickable(false);
    }

    @Override
    public void showButton() {
        mFinishButton.setVisibility(View.VISIBLE);
        mGroupListView.setClickable(true);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
