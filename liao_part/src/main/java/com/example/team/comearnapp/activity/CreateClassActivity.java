package com.example.team.comearnapp.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.team.comearnapp.R;
import com.example.team.commonlibrary.base.util.Retrofit.bean.Friend;
import com.example.team.commonlibrary.base.util.Retrofit.bean.User;
import com.example.team.commonlibrary.base.util.Retrofit.bean.UserTest;
import com.example.team.commonlibrary.base.util.Retrofit.bean.BaseResponse;
import com.example.team.commonlibrary.base.util.Retrofit.bean.Group;
import com.example.team.commonlibrary.base.util.DbUtil;
import com.example.team.commonlibrary.base.util.Retrofit.RetroHttpUtil;
import com.example.team.commonlibrary.base.util.Retrofit.callback.AbstractCommonHttpCallback;
import com.example.team.commonlibrary.base.util.ToastUtil;
import com.example.team.commonlibrary.base.application.MyApp;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import retrofit2.Call;

/**
 * 创建群组页面
 */
public class CreateClassActivity extends AppCompatActivity implements View.OnClickListener, RongIM.UserInfoProvider {

    private RadioButton rb_Class;
    private RadioButton rb_ZiXi;
    private QMUIRoundButton creat_class_btn;
    private QMUIRoundButton face2face_btn;
    private MaterialEditText class_name;
    private MaterialEditText class_information;
    private List<User> userList;
    /**
     * 建立的群组id
     */
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_class);
        initUserInfo();
        StatusBarUtil.setColor(CreateClassActivity.this, getResources().getColor(R.color.green), 50);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("创建群组");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        creat_class_btn = (QMUIRoundButton) findViewById(R.id.creat_class_btn);
        face2face_btn = (QMUIRoundButton) findViewById(R.id.face2face_btn);
        class_name = (MaterialEditText) findViewById(R.id.class_name_met);
        class_information = (MaterialEditText) findViewById(R.id.class_information_met);
        creat_class_btn.setOnClickListener(this);
        face2face_btn.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View var1) {
        /**
         * 普通的创建群组
         */
        if (var1.getId() == R.id.creat_class_btn) {
            if (class_name.getText().length() == 0 || class_information.getText().length() == 0) {
                Toast.makeText(getApplicationContext(), "请完善信息", Toast.LENGTH_SHORT).show();
            } else {
                /**
                 * 先向cto的服务器提出创建群组请求，再向融云服务器提出群组请求
                 * userList那里可能出现问题
                 * @param userIdList:已经注册用户的id集合
                 */
                List<UserTest.UserTempBean> userIdList = new ArrayList<>();
                UserTest.UserTempBean user1 = new UserTest.UserTempBean();
                user1.setId("5ac38bbe60b27023703e0ec7");
                userIdList.add(user1);
                UserTest.UserTempBean user2 = new UserTest.UserTempBean();
                user2.setId("5b152f34e996eebc7422412b");
                userIdList.add(user2);
                UserTest userTest = new UserTest();
                userTest.setUuid("5ab6738260b20797b26114e7");
                userTest.setGroupName(class_name.getText().toString());
                userTest.setGroupInformation(class_information.getText().toString());
                userTest.setUserTemps(userIdList);
                if (DbUtil.contains(MyApp.getGlobalContext(), "user_id")) {
                    //MapGenerator.generate()
//                            .add("uuid", DbUtil.getString(MyApp.getGlobalContext(), "user_id", "null"))
//                            .add("groupName", class_name.getText().toString())
//                            .add("groupInformation", class_information.getText().toString())
//                            .add("userTemp", userIdList)
                    final Call<BaseResponse<Group>> groupCall = RetroHttpUtil.build().createGroupCall(userTest);
                    RetroHttpUtil.sendRequest(groupCall, new AbstractCommonHttpCallback<BaseResponse<Group>>() {
                        @Override
                        public void onSuccess(BaseResponse<Group> result) {
                            groupId = result.getData().getGroupId();
                            if (RongIM.getInstance() != null) {
                                //第一个参数必须是配置了AndroidManifest.xml参数的活动，实现Fragment的隐式跳转
//                                RongIM.getInstance().startConversation(CreateClassActivity.this, Conversation.ConversationType.GROUP,groupId,class_name.getText().toString());
//                                RongIM.getInstance().startGroupChat(MyApp.getGlobalContext(),groupId,class_name.getText().toString());
                                RongIM.getInstance().startPrivateChat(CreateClassActivity.this, "5ac38bbe60b27023703e0ec7", "title");
                            }
                            Toast.makeText(getApplicationContext(), "创建成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail() {
                            ToastUtil.ToastShortShow("建群失败", CreateClassActivity.this);
                        }
                    });

                }
            }

        } else if (var1.getId() == R.id.face2face_btn) {
/**
 * 面对面建群
 */
            if (class_name.getText().length() == 0 || class_information.getText().length() == 0) {
                Toast.makeText(getApplicationContext(), "请完善信息", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "开始面对面建群", Toast.LENGTH_SHORT).show();
                finish();
            }

        }


    }

    private void initUserInfo() {
        userList = new ArrayList<>();
        User user1 = new User();
        user1.setId("5ab6738260b20797b26114e7");
        userList.add(user1);
        User user2 = new User();
        user2.setId("5ac38bbe60b27023703e0ec7");
        userList.add(user2);
        RongIM.setUserInfoProvider(this, true);
    }

    /**
     * s代表userId
     *
     * @param s
     * @return
     */
    @Override
    public UserInfo getUserInfo(String s) {
        int k = 0;
        for (User i : userList) {
            if (i.getId().equals(s)) {
                k++;
                return new UserInfo(i.getId(), "汪神" + k + "", Uri.parse("http://www.51zxw.net/bbs/UploadFile/2013-4/201341122335711220.jpg"));
            }
        }
        return null;
    }

}
/**
 * 内测用户id信息
 * Zou:5ab6738260b20797b26114e7
 * Wang:5ac38bbe60b27023703e0ec7
 * Peng:5b152f34e996eebc7422412b
 */

