package com.example.team.comearnapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.bean.Friend;
import com.example.team.comearnapp.util.ToastUtil;
import com.example.team.commonlibrary.base.MyApp;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class RongYunTestActivity extends AppCompatActivity implements View.OnClickListener,RongIM.UserInfoProvider {
//    private static final String token1 = "Z/UIJmmy4exp0JJAWry6ulR5sFqG6PcqX+S73jdEbz2lfg+a1CVitU5W5AmK8PNQes1Wiw5q8tqmduQh1uPzb56CigYbYf7VxKChiteaMJokqaSDx8eRAg==";
//    private static final String token2 = "FfL49XOlLXYOMw92FaKm1VR5sFqG6PcqX+S73jdEbz2lfg+a1CVitfYLcaywG2wPVyHZTPC3WMUfrDt6qsZ/NzqiS/MGhF5BhBqg4lw3UDWYX3vrKa39PA==";
private static final String token1 = "uFHTGcZMzSCSugxRmRA7lRf1AH3ArXbvLbNoacl+xLf5jFwBPsU0d+uBXWEKpLMlrv/cTWb4cdSdzE0shl1/oA==";
    private static final String token2 = "0L3LhZvmEhmeY/O6z+jqkDdw425PLr6tFkyLhkSfXOmlDCw9tNfLfUcyTHdC9vWaTz2z6Hb0nwEYYnzkBXdBuw==";
    private List<Friend> userIdList;
    private static final String TAG = "MainActivity";

    private Button mUser1, mUser2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rong_yun_test);
        mUser1 = (Button) findViewById(R.id.connect_10010);
        mUser2 = (Button) findViewById(R.id.connect_10086);
        mUser1.setOnClickListener(this);
        mUser2.setOnClickListener(this);

        initUserInfo();
    }
    /**
     * 连接服务器
     **/
    private void connectRongServer(String token) {
        /**
         * 先检测是否运行在主线程
         */
        if (getApplicationInfo().packageName.equals(MyApp.getCurProcessName(getApplicationContext()))) {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {
                @Override
                public void onSuccess(String userId) {
                    if (userId.equals("10010")) {
                        mUser1.setText("用户1连接服务器成功");
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        ToastUtil.ToastShortShow("切换用户登录成功！", getApplicationContext());
                    } else {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        ToastUtil.ToastShortShow("切换用户登录成功！", getApplicationContext());
                    }
                    System.out.println("连接成功！");
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.e(TAG, "connect failure errorCode is : " + errorCode.getValue());
                    System.out.println("连接失败！");
                }

                @Override
                public void onTokenIncorrect() {
                    Log.e(TAG, "token is error ,please check token and app key");
                    System.out.println("token错误！");
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            if(v.getId() == R.id.connect_10010){
                connectRongServer(token1);
            }else if(v.getId() == R.id.connect_10086){
                connectRongServer(token2);
            }
        }
    }

    private void initUserInfo() {
        userIdList = new ArrayList<>();
        userIdList.add(new Friend("10010", "联通", "http://www.51zxw.net/bbs/UploadFile/2013-4/201341122335711220.jpg"));     // 联通图标
        userIdList.add(new Friend("10086", "移动", "http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg"));  // 移动图标
        userIdList.add(new Friend("KEFU144542424649464", "在线客服", "http://img02.tooopen.com/Download/2010/5/22/20100522103223994012.jpg"));
        RongIM.setUserInfoProvider(this, true);
    }

    @Override
    public UserInfo getUserInfo(String s) {
        for (Friend i : userIdList) {
            if (i.getUserId().equals(s)) {
                Log.e(TAG, i.getPortraitUri());
                return new UserInfo(i.getUserId(), i.getName(), Uri.parse(i.getPortraitUri()));
            }
        }
        Log.e(TAG, "UserId is : " + s);
        return null;
    }
}
