package com.example.team.wang.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.team.comearnlib.utils.ToastTools;
import com.example.team.commonlibrary.base.application.MyApp;
import com.example.team.commonlibrary.base.util.DbUtil;
import com.example.team.commonlibrary.base.util.MapGenerator;
import com.example.team.commonlibrary.base.util.Retrofit.RetroHttpUtil;
import com.example.team.commonlibrary.base.util.Retrofit.bean.BaseResponse;
import com.example.team.commonlibrary.base.util.Retrofit.bean.User;
import com.example.team.commonlibrary.base.util.Retrofit.callback.AbstractCommonHttpCallback;
import com.example.team.commonlibrary.base.util.ToastUtil;
import com.example.team.wang_part.R;
import com.example.team.wang.entity.GroupInfo;
import com.example.team.wang.ui.CompoundTextLayout;
import com.example.team.wang.ui.GroupInfoPresentView;
import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.personalspacelib.test.DefaultCustomCardView;
import com.example.team.personalspacelib.test.DefaultCustomCollapsingToolbarLayout;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

class PersonalInfoModel extends BaseModel {

    User mUser;

    public User getUser(){
        return mUser;
    }

    public GroupInfo[] obtainGroupInfos() {
        GroupInfo[] infos = new GroupInfo[1];
        infos[0] = new GroupInfo("群组01");
        return infos;
    }

    public String getTargetUerId(Intent intent) {
        if (intent != null) {
            Bundle infoFromNavi = intent.getExtras();
            if (infoFromNavi != null && !infoFromNavi.isEmpty()) {
                User target_user = (User) infoFromNavi.getSerializable("target_user_id");
                mUser = target_user;
                return target_user != null ? target_user.getId() : null;
            }
        }
        return getThisUerId();
    }

    public void signOut() {

    }
    
    /**
     * 修改用户名
     * @param newName 更改后的用户名
     */
    public void refreshName(String newName,Context context) {
        Call<BaseResponse<Object>> changeUsernameCall = RetroHttpUtil.build().changeUsernameCall(DbUtil.getString(MyApp.getGlobalContext(),"user_id","null"), MapGenerator.generate().add("username",newName));
        System.out.println("refreshName:"+DbUtil.getString(MyApp.getGlobalContext(),"user_id","null"));
        RetroHttpUtil.sendRequest(changeUsernameCall, new AbstractCommonHttpCallback<BaseResponse<Object>>() {
            @Override
            public void onSuccess(BaseResponse<Object> result) {
                ToastUtil.ToastShortShow("修改成功", MyApp.getGlobalContext());
            }

            @Override
            public void onFail() {
                ToastUtil.ToastShortShow("修改失败", MyApp.getGlobalContext());
            }
        });
    }

    /**
     * 发送好友请求
     * @param text 验证消息
     */
    public void sendOutMessage(String text, Context context) {

        //TODO 邹神在这里发送验证消息
    }

    /**
     * 上传用户头像
     */
    public void uploadHeadPortrait(){
        //用户的头像
        String portraitFilePath= " ";
        String userId = " ";
        File portraitFile = new File(portraitFilePath);/**TODO 等待汪神补充**/
        try{
            final RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"),portraitFile);
            Call<BaseResponse<String>> editHeadPortraitCall = RetroHttpUtil.build().editHeadPortraitCall(userId,MapGenerator.generate().add("file",requestBody));
            RetroHttpUtil.sendRequest(editHeadPortraitCall, new AbstractCommonHttpCallback<BaseResponse<String>>() {
                @Override
                public void onSuccess(BaseResponse<String> result) {
                    ToastUtil.ToastShortShow("修改成功",MyApp.getGlobalContext());
                    /**
                     * 从服务器获取修改后的头像网址,并保存在本地
                     * TODO:汪神记得从本地数据库调用
                     */
                    String headPortraitUrl = result.getData();
                    DbUtil.setString(MyApp.getGlobalContext(),"head_portrait_path",headPortraitUrl);
                }

                @Override
                public void onFail() {
                    ToastUtil.ToastShortShow("修改失败",MyApp.getGlobalContext());
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}

class PersonalInfoPresenter extends BasePresenter<PersonalInfoView> {
    private PersonalInfoModel mInfoModel = new PersonalInfoModel();

    public boolean isUser = true;


    public User getUser(){
        return mInfoModel.getUser();
    }

    public void fetchGroupInfos() {
        GroupInfo[] infos = mInfoModel.obtainGroupInfos();
        mView.inflateGroupList(infos);
    }

    public void signOut() {
        mInfoModel.signOut(); //TODO 邹神这里写网络逻辑
        mView.signOut();  // TODO 视图交互逻辑
    }

    public void addFriend() {

        final QMUIDialog.EditTextDialogBuilder editTextDialogBuilder = new QMUIDialog.EditTextDialogBuilder(mContext);
        editTextDialogBuilder
                .setTitle("填写验证信息")
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        final String text = editTextDialogBuilder.getEditText().getText().toString();
                        ToastTools.showToast(mContext, text);
                        mInfoModel.sendOutMessage(text, mContext);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void refreshName(String text) {
        mView.refreshName(text);
        mInfoModel.refreshName(text, mContext);
    }

    /**
     * 同时记录下了是不是本用户
     * @param mTargetUserId
     */
    public void refreshBtn(String mTargetUserId) {
        isUser = mTargetUserId.equals(mInfoModel.getThisUerId());
        mView.refreshBtn(isUser);
    }

    public void updateHeadPortrait(){
        mView.selectHeadImage();
        mInfoModel.uploadHeadPortrait();
    }
}

class WidgetsManager {
    private Activity mActivity;
    private SparseArray<View> mViewArray = new SparseArray<>();

    WidgetsManager() {
    }

    public void attachToActivity(Activity activity) {
        this.mActivity = activity;
        addViewGroup(mActivity.findViewById(android.R.id.content));
    }

    public void detach() {
        this.mActivity = null;
        this.mViewArray.clear();
    }

    public void addViewGroup(View viewGroup) {
        if (viewGroup instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) viewGroup).getChildCount(); i++) {
                View v = ((ViewGroup) viewGroup).getChildAt(i);
                addViewGroup(v);
            }
        }
        mViewArray.put(viewGroup.getId(), viewGroup);
    }

    public View addView(int id) {
        mViewArray.put(id, mActivity.findViewById(id));
        return mActivity.findViewById(id);
    }

    public View addView(String mark, View view) {
        mViewArray.put(mark.hashCode(), view);
        return view;
    }

    public View getView(int id) {
        return mViewArray.get(id);
    }

    public View getView(String mark) {
        return mViewArray.get(mark.hashCode());
    }

}

interface PersonalInfoView extends IBaseView {
    void inflateGroupList(GroupInfo[] infos);

    void signOut();

    void refreshName(String text);

    void refreshBtn(boolean equals);

    void selectHeadImage();

    void updatePortrait(Uri uri);
}

@Route(path = "/wang_part/personal_info")
public class PersonalInfoActivity extends AppCompatActivity implements PersonalInfoView {

    private static final int FOR_CROP = 100;
    public static final String PORTRAIT_IMAGE = "portrait_image";
    private static final int FOR_INFO = 101;
    private PersonalInfoModel mInfoModel = new PersonalInfoModel();
    private WidgetsManager mViewManager = new WidgetsManager();
    private PersonalInfoPresenter mPresenter = new PersonalInfoPresenter();

    private String mTargetUserId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

//        QMUIStatusBarHelper.translucent(this); //沉浸式状态栏


        StatusBarUtil.setColor(PersonalInfoActivity.this, getResources().getColor(R.color.green), 50);

        attachWidgets();

        initUI();

        initListeners();

        mTargetUserId = mInfoModel.getTargetUerId(getIntent());

        mPresenter.fetchGroupInfos();

        mPresenter.refreshBtn(mTargetUserId);
    }

    private void initListeners() {
        mViewManager.getView(R.id.act_personal_info_nsv_btn_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTargetUserId.equals(mInfoModel.getThisUerId())) {
                    mPresenter.signOut();
                } else {
                    mPresenter.addFriend();
                }
            }
        });
        mViewManager.getView(R.id.act_personal_info_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QMUIDialog.EditTextDialogBuilder editTextDialogBuilder = new QMUIDialog.EditTextDialogBuilder(PersonalInfoActivity.this);
                editTextDialogBuilder
                        .setTitle("修改昵称")
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                final String text = editTextDialogBuilder.getEditText().getText().toString();
                                mPresenter.refreshName(text);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        mViewManager.getView("clps_bg").findViewById(R.id.act_personal_info_c_bg_ci_portrait).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.updateHeadPortrait();
            }
        });
    }

    private void initUI() {

        mViewManager.addView("clps_bg", View.inflate(this, R.layout.activity_personal_info_collapse_background, null));
        mViewManager.addView("clps", new DefaultCustomCollapsingToolbarLayout(this, mViewManager.getView("clps_bg")));

        {
            DefaultCustomCollapsingToolbarLayout mCollapseView = (DefaultCustomCollapsingToolbarLayout) mViewManager.getView("clps");
            mCollapseView.setContentScrimColor(Color.parseColor("#4caf50"));
            mCollapseView.setTitle("用户姓名");
            mCollapseView.setSupportActionBar(this);
        }

        ((AppBarLayout) mViewManager.getView(R.id.act_personal_info_abl)).addView(mViewManager.getView("clps"));

        ArrayList<View> views = new ArrayList<View>();

        views.add(mViewManager.addView("txt_age", new CompoundTextLayout(this, "年龄").setContentText("18")));
        views.add(mViewManager.addView("txt_gender", new CompoundTextLayout(this, "性别").setContentText("男")));
        views.add(mViewManager.addView("txt_university", new CompoundTextLayout(this, "学校").setContentText("西安电子科技大学")));
        views.add(mViewManager.addView("txt_school", new CompoundTextLayout(this, "学院").setContentText("通信工程学院")));
        views.add(mViewManager.addView("txt_major", new CompoundTextLayout(this, "专业").setContentText("通信工程")));

        ((DefaultCustomCardView) mViewManager.getView(R.id.act_personal_info_nsv_dccv_hold))
                .setHeadText("基本信息").addViewList(views);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachWidgets();
    }

    private void attachWidgets() {
        mPresenter.attachView(this);
        mViewManager.attachToActivity(this);
    }

    private void detachWidgets() {
        mViewManager.detach();
        mPresenter.detachView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.act_on_class_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.act_on_class_item_edit){
            Intent intent = new Intent(this, ModifyPersonalInfoActivity.class);
            intent.putExtra("user_info_extra", mPresenter.getUser());
            startActivityForResult(intent, FOR_INFO);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.getItem(0).setVisible(mPresenter.isUser);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FOR_CROP && resultCode == RESULT_OK){
            if (data != null){
                Uri uri = data.getParcelableExtra(PORTRAIT_IMAGE);
                updatePortrait(uri);
            }
        }
        else if (requestCode == FOR_INFO && resultCode == RESULT_OK){
            if (data != null){
                //TODO 获得修改后的信息
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void inflateGroupList(GroupInfo[] infos) {
        ArrayList<View> views = new ArrayList<>();

        for (GroupInfo info : infos) {
            GroupInfoPresentView view = new GroupInfoPresentView(this);
            view.setGroupInfo(info);
            views.add(view);
        }

        ((DefaultCustomCardView) mViewManager.getView(R.id.act_personal_info_nsv_dccv_group_list))
                .setHeadText("加入的群组").addViewList(views);
    }

    @Override
    public void signOut() {
        if (DbUtil.contains(MyApp.getGlobalContext(), "isLogined")) {
            DbUtil.delete(MyApp.getGlobalContext(), "isLogined");
        }
        ARouter.getInstance().build("/liao_part/login_activity").navigation();
        finish();
    }

    @Override
    public void refreshName(String text) {
        DefaultCustomCollapsingToolbarLayout mCollapseView = (DefaultCustomCollapsingToolbarLayout) mViewManager.getView("clps");
        mCollapseView.setTitle(text);
    }

    @Override
    public void refreshBtn(boolean equals) {
        Button bottomBtn = (Button) mViewManager.getView(R.id.act_personal_info_nsv_btn_bottom);
        if (!equals) {
            bottomBtn.setText("添加好友");
        } else {
            bottomBtn.setText("注销");
        }
    }

    @Override
    public void selectHeadImage() {
//        startActivityForResult(new Intent(this, CropperActivity.class), FOR_CROP); TODO: 等待头像裁剪部分接入
        ToastTools.showToast(this, "修改头像。");
    }

    @Override
    public void updatePortrait(Uri uri) {
        ((ImageView)mViewManager.getView("clps_bg").findViewById(R.id.act_personal_info_c_bg_ci_portrait)).setImageURI(uri);
    }
}
