package com.example.team.commonlibrary.base.interfaces;

import com.example.team.commonlibrary.base.util.Retrofit.bean.BaseResponse;
import com.example.team.commonlibrary.base.util.Retrofit.bean.Group;
import com.example.team.commonlibrary.base.util.Retrofit.bean.LoginResponseData;
import com.example.team.commonlibrary.base.util.MapGenerator;
import com.example.team.commonlibrary.base.util.Retrofit.bean.User;
import com.example.team.commonlibrary.base.util.Retrofit.bean.UserTest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 邹特强 on 2018/4/2.
 * Retrofit公用网络请求方法接口
 */

public interface HttpRequestInterface {
    /**
     * 登录界面
     */

    String LOGIN_URL = "sign_in";

    /**
     * 登录的post方法
     *
     * @return 返回LoginResponseData
     */
    @POST(LOGIN_URL)
    Call<BaseResponse<LoginResponseData>> loginCall(@Body MapGenerator postData);

    /**
     * 注册界面
     */
    String REGISTER_URL = "sign_up";

    /**
     * 注册的post方法
     *
     * @return 这里因为不需要获取data, 所以用Object作为data类型
     * 取值时只取code和msg
     */
    @POST(REGISTER_URL)
    Call<BaseResponse<Object>> registerCall(@Body MapGenerator registerData);

    /**
     * 检测用户是否注册接口
     */
    String REGISTER_CHECK_URL = "registion";

    /**
     * 检测账号是否注册的post方法
     *
     * @return 只需获取msg
     */
    @POST(REGISTER_CHECK_URL)
    Call<BaseResponse<Object>> registerCheckCall(@Body MapGenerator regsiterCheckData);

    /**
     * 获取验证码接口
     */
    String VERIFICATION_CODE_URL = "code";

    /**
     * 获取验证码的post方法
     *
     * @return 这里也不需要获取data，所以返回BaseResponse<Object></Object>
     */
    @POST(VERIFICATION_CODE_URL)
    Call<BaseResponse<Object>> verificationCodeCall(@Body MapGenerator verificationCodeData);

    /**
     * 忘记密码界面
     */
    String FORGETPASSWORD_URL = "reset";

    /**
     * 修改密码的post方法
     */
    @POST(FORGETPASSWORD_URL)
    Call<BaseResponse<Object>> forgetPasswordCall(@Body MapGenerator foregetPasswordData);

    /**
     * 群组相关操作界面
     */
    String CREATEGROUP_URL = "groups";

    /**
     * 群组创建的post方法
     */
    @POST(CREATEGROUP_URL)
    Call<BaseResponse<Group>> createGroupCall(@Body UserTest userTest);

    /**
     * 用户搜索界面;TODO：这个等待cto改url
     * TODO:BaseResponse<List<User>>可能出问题
     */
    String SEARCHUSERS_URL = "users/{name}";
    @GET(SEARCHUSERS_URL)
    Call<BaseResponse<List<User>>> searchUsersCall(@Path("name") String nickname);

    /**
     * 群组搜索界面
     */
    String SEARCHGROUPS_URL = "groups/{groupName}";
    @GET(SEARCHGROUPS_URL)
    Call<BaseResponse<List<Group>>> searchGroupsCall(@Path("groupName")String groupName);

    /**
     * 以下为个人中心界面（个人中心界面api都写在这)
     */
    String CHANGENAME_URL = "users/{id}/information";
    @POST(CHANGENAME_URL)
    Call<BaseResponse<Object>> changeUsernameCall(@Path("id")String newUsername);
}
