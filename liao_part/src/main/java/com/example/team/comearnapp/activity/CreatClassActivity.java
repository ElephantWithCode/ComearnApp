package com.example.team.comearnapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.activity.groupActivity.GroupActivity;
import com.example.team.comearnapp.bean.BaseResponse;
import com.example.team.comearnapp.bean.Group;
import com.example.team.comearnapp.interfaces.HttpRequestInterface;
import com.example.team.comearnapp.util.DbUtil;
import com.example.team.comearnapp.util.MapGenerator;
import com.example.team.comearnapp.util.Retrofit.RetroHttpUtil;
import com.example.team.comearnapp.util.Retrofit.callback.AbstractCommonHttpCallback;
import com.example.team.comearnapp.util.ToastUtil;
import com.example.team.commonlibrary.base.MyApp;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import retrofit2.Call;

/**
 * 创建群组页面
 */
public class CreatClassActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton rb_Class;
    private RadioButton rb_ZiXi;
    private QMUIRoundButton creat_class_btn;
    private QMUIRoundButton face2face_btn;
    private MaterialEditText class_name;
    private MaterialEditText class_information;
    /**
     * 建立的群组id
     */
    private String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_class);
        StatusBarUtil.setColor(CreatClassActivity.this, getResources().getColor(R.color.green), 50);
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
                 */
                /**
                 * 已经注册用户的id集合
                 * TODO:待添加
                 */
                List<String> userIdList = new ArrayList<>();
                if (DbUtil.contains(MyApp.getGlobalContext(), "user_id")) {
                    final Call<BaseResponse<Group>> groupCall = RetroHttpUtil.build().createGroupCall(MapGenerator.generate()
                            .add("uuid", DbUtil.getString(MyApp.getGlobalContext(), "user_id", "null"))
                            .add("groupName", class_name.getText().toString())
                            .add("groupInformation", class_information.getText().toString())
                            .add("userList", userIdList));
                    RetroHttpUtil.sendRequest(groupCall, new AbstractCommonHttpCallback<BaseResponse<Group>>() {
                        @Override
                        public void onSuccess(BaseResponse<Group> result) {
                            groupId = result.getData().getGroupId();
                            if (RongIM.getInstance() != null) {
                                //第一个参数必须是配置了AndroidManifest.xml参数的，实现Fragment的隐式跳转
                                RongIM.getInstance().startGroupChat(CreatClassActivity.this,groupId,"西电家装公司（仅供测试）");
                            }
                            Toast.makeText(getApplicationContext(), "创建成功", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFail() {
                            ToastUtil.ToastShortShow("建群失败！",MyApp.getGlobalContext());
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
}
