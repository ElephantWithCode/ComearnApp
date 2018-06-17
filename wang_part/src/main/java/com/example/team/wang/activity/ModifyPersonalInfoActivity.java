package com.example.team.wang.activity;

import android.content.Context;
import android.content.Intent;
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
import com.example.team.commonlibrary.base.application.MyApp;
import com.example.team.commonlibrary.base.util.MapGenerator;
import com.example.team.commonlibrary.base.util.Retrofit.RetroHttpUtil;
import com.example.team.commonlibrary.base.util.Retrofit.bean.BaseResponse;
import com.example.team.commonlibrary.base.util.Retrofit.bean.User;
import com.example.team.commonlibrary.base.util.Retrofit.callback.AbstractCommonHttpCallback;
import com.example.team.commonlibrary.base.util.ToastUtil;
import com.example.team.wang_part.R;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import retrofit2.Call;


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

    User mUser;

    public User getUser(){
        return mUser;
    }

    public void setUser(User user){
        mUser = user;
    }

    public void uploadInfo(User info) {
        User userInfo = info;//TODO 待汪神添加
        String userId = " ";//TODO 待汪神添加
        Call<BaseResponse<Object>> editUserInformationCall = RetroHttpUtil.build().editUserInformationCall(userId,userInfo);
        RetroHttpUtil.sendRequest(editUserInformationCall, new AbstractCommonHttpCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> result) {
                ToastUtil.ToastShortShow("修改成功", MyApp.getGlobalContext());
            }

            @Override
            public void onFail() {
                ToastUtil.ToastShortShow("修改失败",MyApp.getGlobalContext());
            }
        });
        //TODO 邹神这里上传个人信息； info内包含各种信息
    }
}

class ModifyPersonalInfoPresenter extends BasePresenter<ModifyPersonalInfoView>{
    ModifyPersonalInfoModel mModel = new ModifyPersonalInfoModel();

    public User getUser(){
        return mModel.getUser();
    }

    public void setUser(User user){
        mModel.setUser(user);
    }

    public void updatePersonalInfo(User info){
        mModel.uploadInfo(info);
    }

    public void processViewWithUserInfo(Intent intent){
        User user = (User) intent.getSerializableExtra("user_info_extra");
        setUser(user);
        mView.refreshInfoView(user);
    }


}

interface ModifyPersonalInfoView extends IBaseView{

    void refreshInfoView(User user);
}

public class ModifyPersonalInfoActivity extends AppCompatActivity implements ModifyPersonalInfoView{

    QMUITopBar mTopBar;
    QMUIGroupListView mGroupListView;

    private QMUICommonListItemView mGenderView;
    private QMUICommonListItemView mAgeView;
    private QMUICommonListItemView mNickNameView;
    private QMUICommonListItemView mInfoView;
    private ModifyPersonalInfoPresenter mPresenter = new ModifyPersonalInfoPresenter();
    private QMUICommonListItemView mUniversityView;
    private QMUICommonListItemView mSchoolView;
    private QMUICommonListItemView mMajorView;

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

        mUniversityView = mGroupListView.createItemView("学校");
        mSchoolView = mGroupListView.createItemView("学院");
        mMajorView = mGroupListView.createItemView("专业");



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
                editTextDialogBuilder.getEditText().setText(mNickNameView.getDetailText());
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
                editTextDialogBuilder.getEditText().setText(mInfoView.getDetailText());
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

        View.OnClickListener uniListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QMUIDialog.EditTextDialogBuilder editTextDialogBuilder = new QMUIDialog.EditTextDialogBuilder(ModifyPersonalInfoActivity.this);
                editTextDialogBuilder.getEditText().setText(mUniversityView.getDetailText());
                editTextDialogBuilder
                        .setTitle("修改个人信息")
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                final String text = editTextDialogBuilder.getEditText().getText().toString();
                                mUniversityView.setDetailText(text);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        };

        View.OnClickListener schoolListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QMUIDialog.EditTextDialogBuilder editTextDialogBuilder = new QMUIDialog.EditTextDialogBuilder(ModifyPersonalInfoActivity.this);
                editTextDialogBuilder.getEditText().setText(mSchoolView.getDetailText());
                editTextDialogBuilder
                        .setTitle("修改个人信息")
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                final String text = editTextDialogBuilder.getEditText().getText().toString();
                                mSchoolView.setDetailText(text);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        };
        View.OnClickListener majorListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QMUIDialog.EditTextDialogBuilder editTextDialogBuilder = new QMUIDialog.EditTextDialogBuilder(ModifyPersonalInfoActivity.this);
                editTextDialogBuilder.getEditText().setText(mMajorView.getDetailText());
                editTextDialogBuilder
                        .setTitle("修改个人信息")
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                final String text = editTextDialogBuilder.getEditText().getText().toString();
                                mMajorView.setDetailText(text);
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
        mStudyInfo.addItemView(mUniversityView, uniListener);
        mStudyInfo.addItemView(mSchoolView, schoolListener);
        mStudyInfo.addItemView(mMajorView, majorListener);
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

    @Override
    public void refreshInfoView(User user) {
        mNickNameView.setDetailText(user.getUsername());
        mInfoView.setDetailText(user.getEmail()); //TODO：信息不对。
        mAgeView.setDetailText(user.getNumber() + ""); // TODO：信息不对。
        mGenderView.setDetailText(user.getGender());
        mMajorView.setDetailText(user.getMajor());
        mUniversityView.setDetailText(user.getInstitute());
        mSchoolView.setDetailText(user.getSchool());
    }
}
