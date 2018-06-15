package com.example.team.wang.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.comearnlib.utils.ToastTools;
import com.example.team.wang_part.R;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;


class PersonalInfo{
    String mNickName;
    String mInfo;
    String mGender;
    String mAge;
    String mUniversity;
    String mSchool;
    String mMajor;
}

class ModifyPersonalInfoModel extends BaseModel{

    public void uploadInfo(PersonalInfo info) {
        //TODO 邹神这里上传个人信息； info内包含各种信息
    }
}

class ModifyPersonalInfoPresenter extends BasePresenter<ModifyPersonalInfoView>{
    ModifyPersonalInfoModel mModel = new ModifyPersonalInfoModel();

    public void updatePersonalInfo(PersonalInfo info){
        mModel.uploadInfo(info);
    }
}

interface ModifyPersonalInfoView extends IBaseView{

}

public class ModifyPersonalInfoActivity extends AppCompatActivity implements ModifyPersonalInfoView{

    QMUITopBar mTopBar;
    QMUIGroupListView mGroupListView;

    private QMUICommonListItemView mGenderView;
    private QMUICommonListItemView mAgeView;
    private QMUICommonListItemView mNickNameView;
    private QMUICommonListItemView mInfoView;
    private ModifyPersonalInfoPresenter mPresenter = new ModifyPersonalInfoPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.attachView(this);

        setContentView(R.layout.activity_modify_personal_info);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.green), 50);

        initUI();

        initListeners();


    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    private void initListeners() {
        Button finishBtn = mTopBar.addRightTextButton("完成", 123);
        finishBtn.setTextColor(getResources().getColor(R.color.white));
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.updatePersonalInfo(null);
                ToastTools.showToast(ModifyPersonalInfoActivity.this, "完成信息修改上传");
                finish();
            }
        });
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initUI() {
        mTopBar = findViewById(R.id.act_modi_per_info_tb);
        mTopBar.setTitle("修改个人信息").setTextColor(getResources().getColor(R.color.white));

        mGroupListView = findViewById(R.id.act_modi_per_info_glv);

        QMUIGroupListView.Section mBasicInfo = new QMUIGroupListView.Section(this);
        QMUIGroupListView.Section mStudyInfo = new QMUIGroupListView.Section(this);

        mBasicInfo.setTitle("基本信息");
        mStudyInfo.setTitle("学习信息");


        mNickNameView = mGroupListView.createItemView("昵称");
        mInfoView = mGroupListView.createItemView("个人信息");
        mGenderView = mGroupListView.createItemView("性别");
        mAgeView = mGroupListView.createItemView("年龄");



        mNickNameView.setDetailText("用户昵称");

        mInfoView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        mGenderView.setDetailText("男");

        mAgeView.setDetailText("18");



        View.OnClickListener genderListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QMUIBottomSheet selectGender = new QMUIBottomSheet.BottomListSheetBuilder(ModifyPersonalInfoActivity.this)
                        .addItem("男")
                        .addItem("女")
                        .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                            @Override
                            public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                                if (position == 0) {
                                    mGenderView.setDetailText("男");
                                } else {
                                    mGenderView.setDetailText("女");
                                }
                                dialog.dismiss();
                            }
                        }).build();
                selectGender.show();
            }
        };

        View.OnClickListener ageViewListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO  打开底层滚轮View
            }
        };

        View.OnClickListener nickNameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QMUIDialog.EditTextDialogBuilder editTextDialogBuilder = new QMUIDialog.EditTextDialogBuilder(ModifyPersonalInfoActivity.this);
                editTextDialogBuilder
                        .setTitle("修改昵称")
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                final String text = editTextDialogBuilder.getEditText().getText().toString();
                                mNickNameView.setDetailText(text);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        };
        View.OnClickListener infoListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QMUIDialog.EditTextDialogBuilder editTextDialogBuilder = new QMUIDialog.EditTextDialogBuilder(ModifyPersonalInfoActivity.this);
                editTextDialogBuilder
                        .setTitle("修改个人信息")
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                final String text = editTextDialogBuilder.getEditText().getText().toString();
                                mInfoView.setDetailText(text);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        };

        mBasicInfo.addItemView(mNickNameView, nickNameListener);
        mBasicInfo.addItemView(mInfoView, infoListener);
        mBasicInfo.addItemView(mGenderView, genderListener);
        mBasicInfo.addItemView(mAgeView, ageViewListener);
        mBasicInfo.addTo(mGroupListView);
        mStudyInfo.addTo(mGroupListView);
    }

    private EditText generateEditText() {
        EditText editText = new EditText(this);
        editText.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        return editText;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
