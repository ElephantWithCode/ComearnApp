package com.example.team.comearnapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.team.comearnapp.R;
import com.example.team.comearnlib.utils.ConvertTools;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

public class RefinedClassSettingActivity extends AppCompatActivity {

    private QMUIGroupListView mGroupListView;

    private QMUIGroupListView.Section mSection;

    private QMUICommonListItemView mSpotItemView;
    private QMUICommonListItemView mStartItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refined_class_setting);

        mGroupListView = findViewById(R.id.act_refined_class_setting_QMUIGLV);

        mSpotItemView = mGroupListView.createItemView("地点");
        mSpotItemView.setDetailText("信远楼#311");

        mStartItemView = mGroupListView.createItemView("开始时间");
        mStartItemView.setText("开始时间");
        mStartItemView.setDetailText("10:00");

        mSection = new QMUIGroupListView.Section(this);

        View.OnClickListener onSpotItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(RefinedClassSettingActivity.this);
                final EditText editText = builder.getEditText();
                QMUIDialog inputDialog = builder
                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        }).addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                mSpotItemView.setDetailText(editText.getText());
                                dialog.dismiss();
                            }
                        }).setTitle("填写地点")
                        .create();
                inputDialog.show();
            }
        };
        mSection.addItemView(mSpotItemView, onSpotItemClickListener);

        View.OnClickListener onStartTimeItemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        mStartItemView.setDetailText(ConvertTools.parseTime(hourOfDay) + ":" + ConvertTools.parseTime(minute));
                    }
                }, true);
                tpd.vibrate(false);
                tpd.show(getFragmentManager(), "time picker dialog");
            }
        };
        mSection.addItemView(mStartItemView, onStartTimeItemClickListener);
        mSection.addTo(mGroupListView);
    }
}
