package com.example.team.comearnapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.team.comearnapp.R;
import com.example.team.comearnapp.R2;
import com.example.team.commonlibrary.base.util.Retrofit.bean.BaseResponse;
import com.example.team.commonlibrary.base.util.Retrofit.bean.LoginResponseData;
import com.example.team.commonlibrary.base.util.DbUtil;
import com.example.team.commonlibrary.base.util.EmailCheckUtil;
import com.example.team.commonlibrary.base.util.MapGenerator;
import com.example.team.commonlibrary.base.util.Retrofit.RetroHttpUtil;
import com.example.team.commonlibrary.base.util.Retrofit.callback.AbstractLoginHttpCallback;
import com.example.team.commonlibrary.base.util.ToastUtil;
import com.example.team.commonlibrary.base.application.MyApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * @author 邹特强
 *         登录对应活动,TODO：正则表达式记得研究
 */
@Route(path = "/liao_part/login_activity")
public class LoginActivity extends AppCompatActivity {
    @BindView(R2.id.login_head_civ)
    CircleImageView civLoginHeadPortrait;
    @BindView(R2.id.login_account_edt)
    EditText edtLoginAccount;
    @BindView(R2.id.login_password_edt)
    EditText edtLoginPassword;
    @BindView(R2.id.login_bt)
    Button btLogin;
    @BindView(R2.id.login_register_bt)
    Button btRegister;
    @BindView(R2.id.login_eye_img)
    ImageView imgLoginEye;
    @BindView(R2.id.login_forget_password_bt)
    Button btLoginForgetPassword;
    protected String account;
    protected String password;
    /**
     * 眼睛图片是否被点击
     */
    private boolean isEyeImgOpened = false;

    private boolean DEBUG = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        checkLoginState();
        setContentView(R.layout.activity_login);
        initViews();
    }

    /**
     * 检查用户是否已经登陆过，登陆过直接跳转主界面
     */
    private void checkLoginState() {
        if (DbUtil.contains(MyApp.getGlobalContext(), "isLogined")) {
            DbUtil.delete(MyApp.getGlobalContext(), "isLogined");
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    /**
     * 这里尝试用ButterKnife初始化控件
     */
    private void initViews() {
        ButterKnife.bind(this);
        autoFill();
    }

    /**
     * 刚注册完后跳转到登录界面自动填充账号密码
     */
    private void autoFill() {
        Intent registerIntent = getIntent();
        if (registerIntent != null) {
            edtLoginAccount.setText(registerIntent.getStringExtra("account"));
            edtLoginPassword.setText(registerIntent.getStringExtra("password"));
        }
    }

    /**
     * 点击登录按钮响应事件
     */
    @OnClick(R2.id.login_bt)
    void onClickLoginBt() {

        if (DEBUG){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        account = edtLoginAccount.getText().toString().trim();
        password = edtLoginPassword.getText().toString().trim();
        if (!EmailCheckUtil.checkEmail(account)) {
            Toast.makeText(LoginActivity.this, R.string.input_right_email, Toast.LENGTH_SHORT).show();
        } else if (password.length() == 0) {
            ToastUtil.ToastShortShow("密码为空！", LoginActivity.this);
        } else {
            /**
             * 登录方法
             */
            Call<BaseResponse<LoginResponseData>> loginCall = RetroHttpUtil.build().loginCall(MapGenerator.generate().add("email", account).add("password", password));
            RetroHttpUtil.sendRequest(loginCall, new AbstractLoginHttpCallback<BaseResponse<LoginResponseData>>() {
                @Override
                public void onSuccess(BaseResponse<LoginResponseData> result) {
                    System.out.println("用户的cloud_token为:" + result.getData().getCloud_token());
                    System.out.println("用户的id为："+result.getData().getUser().getId());
                    System.out.println("用户的昵称为:"+result.getData().getUser().getUsername());
                    ToastUtil.ToastShortShow("登录成功！", LoginActivity.this);
                    /**
                     * 每次登录时更新token和cloud_token,存储到数据库
                     */
                    DbUtil.setString(MyApp.getGlobalContext(), "token", result.getData().getToken());
                    DbUtil.setString(MyApp.getGlobalContext(), "cloud_token", result.getData().getCloud_token());
                    /**
                     * 存储用户的id
                     */
                    DbUtil.setString(MyApp.getGlobalContext(), "user_id", result.getData().getUser().getId());
                    /**
                     * 记录登录状态
                     */
                    DbUtil.setString(MyApp.getGlobalContext(), "isLogined", "true");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onAccountOrPasswordError() {
                    ToastUtil.ToastShortShow("账号或密码错误！", LoginActivity.this);
                }

                @Override
                public void onNotRegister() {
                    ToastUtil.ToastShortShow("账号未注册！", LoginActivity.this);
                }

                @Override
                public void onFinal() {

                }
            });
        }

    }

    /**
     * 点击注册按钮响应事件
     */
    @OnClick(R2.id.login_register_bt)
    void onClickRegisterBt() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * 点击眼睛图片显示密码
     */
    @OnClick(R2.id.login_eye_img)
    void onClickEyeImg() {
        if (!isEyeImgOpened) {
            imgLoginEye.setImageResource(R.drawable.login_register_eye_open_img);
            edtLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isEyeImgOpened = true;
            edtLoginPassword.setSelection(edtLoginPassword.getText().length());
        } else {
            imgLoginEye.setImageResource(R.drawable.login_register_eye_close_img);
            edtLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isEyeImgOpened = false;
            edtLoginPassword.setSelection(edtLoginPassword.getText().length());
        }
    }

    /**
     * 点击忘记密码按钮
     */
    @OnClick(R2.id.login_forget_password_bt)
    void onClickForgetPasswordBt() {
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
        finish();
    }

}
