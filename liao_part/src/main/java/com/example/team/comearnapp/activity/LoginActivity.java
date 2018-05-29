package com.example.team.comearnapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.team.comearnapp.R;
import com.example.team.comearnapp.R2;
import com.example.team.comearnapp.bean.BaseResponse;
import com.example.team.comearnapp.bean.LoginResponseData;
import com.example.team.comearnapp.util.DbUtil;
import com.example.team.comearnapp.util.EmailCheckUtil;
import com.example.team.comearnapp.util.MapGenerator;
import com.example.team.comearnapp.util.Retrofit.RetroHttpUtil;
import com.example.team.comearnapp.util.Retrofit.callback.AbstractLoginHttpCallback;
import com.example.team.comearnapp.util.ToastUtil;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

/**
 * @author 邹特强
 *         登录对应活动,TODO：正则表达式记得研究
 */
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initViews();
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
        account = edtLoginAccount.getText().toString().trim();
        password = edtLoginPassword.getText().toString().trim();
        if (!EmailCheckUtil.checkEmail(account)) {
            Toast.makeText(LoginActivity.this, R.string.input_right_email, Toast.LENGTH_SHORT).show();
        } else if (password.length() == 0) {
            ToastUtil.ToastShortShow("密码为空！",LoginActivity.this);
        } else {
            /**
             * 登录方法
             */
            Call<BaseResponse<LoginResponseData>> loginCall = RetroHttpUtil.build().loginCall(MapGenerator.generate().add("email", account).add("password", password));
            RetroHttpUtil.sendRequest(loginCall, new AbstractLoginHttpCallback<BaseResponse<LoginResponseData>>() {
                @Override
                public void onSuccess(BaseResponse<LoginResponseData> result) {
                    ToastUtil.ToastShortShow("登录成功！", LoginActivity.this);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
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