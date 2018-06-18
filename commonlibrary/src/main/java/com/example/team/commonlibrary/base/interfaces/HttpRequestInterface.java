package com.example.team.commonlibrary.base.interfaces;

import com.example.team.commonlibrary.base.util.Retrofit.bean.BaseResponse;
import com.example.team.commonlibrary.base.util.Retrofit.bean.FriendTest;
import com.example.team.commonlibrary.base.util.Retrofit.bean.Group;
import com.example.team.commonlibrary.base.util.Retrofit.bean.LoginResponseData;
import com.example.team.commonlibrary.base.util.MapGenerator;
import com.example.team.commonlibrary.base.util.Retrofit.bean.User;
import com.example.team.commonlibrary.base.util.Retrofit.bean.UserTest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by 邹特强 on 2018/4/2.
 * Retrofit公用网络请求方法接口
 */

public interface HttpRequestInterface {
    /**
     * 登录注册界面
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

/**********************************************************************************************/

    /**
     * 群组相关操作界面
     */

    /**
     * 群组创建的post方法
     */
    String CREATEGROUP_URL = "groups";
    @POST(CREATEGROUP_URL)
    Call<BaseResponse<Group>> createGroupCall(@Body UserTest userTest);

    /**
     * 用户搜索界面;TODO：这个等待cto改url
     * TODO:BaseResponse<List<User>>可能出问题
     */
    String SEARCHUSERS_URL = "selection/users";

    /**
     * @param userName 传入用户名
     */
    @GET(SEARCHUSERS_URL)
    Call<BaseResponse<List<User>>> searchUsersCall(@QueryMap MapGenerator userName);

    /**
     * 群组搜索界面
     */
    String SEARCHGROUPS_URL = "selction/groups";

    /**
     * @param groupName 传入群组名
     */
    @GET(SEARCHGROUPS_URL)
    Call<BaseResponse<List<Group>>> searchGroupsCall(@QueryMap MapGenerator groupName);

    /**
     * 修改群昵称
     */
    String CHANGEGROUPNAME_URL = "groups/{groupId}/name";
    @PUT(CHANGEGROUPNAME_URL)
    Call<BaseResponse<Object>> changeGroupNameCall(@Path("groupId") String groupId, @Body MapGenerator groupName);

    /**
     * 主动退群
     */
    String QUITGROUP_URL = "groups/users/{id}";
    @DELETE(QUITGROUP_URL)
    Call<BaseResponse<BaseResponse<Object>>> quitGroupCall(@Path("id")String userId,@Body MapGenerator quitData);

/************************************************************************************************************************/

    /**
     * 以下为个人中心界面（个人中心界面api都写在这)
     */

    /**
     * 修改用户昵称
     */
    String CHANGENAME_URL = "users/{id}/information";

    /**
     * @param userId      用户的id
     * @param newUserName 用户的新昵称
     */
    @POST(CHANGENAME_URL)
    Call<BaseResponse<Object>> changeUsernameCall(@Path("id") String userId, @Body MapGenerator newUserName);

    /**
     * 发送好友请求
     */
    String ADDFRIEND_URL = "add";
    @POST(ADDFRIEND_URL)
    Call<BaseResponse<Object>> addFriendCall(@Body MapGenerator addFriendData);

    /**
     * 接受好友请求
     */
    String ACCEPTFRIEND_URL = "accept";
    @POST(ACCEPTFRIEND_URL)
    Call<BaseResponse<Object>> acceptFriendCall(@Body MapGenerator acceptData);

    /**
     * 拒绝好友请求
     */
    String REJECTFRIEND_URL = "reject";
    @POST(REJECTFRIEND_URL)
    Call<BaseResponse<Object>> rejectFriendCall(@Body MapGenerator rejectData);

    /**
     * 获取好友列表
     */
    String GETFRIENDLIST_URL = "users/{id}/friends";

    /**
     *
     * @param userId 用户Id
     */
    @GET(GETFRIENDLIST_URL)
    Call<BaseResponse<List<FriendTest>>> getFriendListCall(@Path("id") String userId);

    /**
     * 修改用户信息
     */
    String EDTUSERINFORMATION_URL = "users/{id}";

    /**
     * @param userId 用户Id
     * @param userInfo 更新的用户信息
     */
    @PUT(EDTUSERINFORMATION_URL)
    Call<BaseResponse<Object>> editUserInformationCall(@Path("id")String userId,@Body User userInfo);

    /**
     * 修改并上传用户头像,TODO:没上传过图片，可能会出现问题
     */
    String EDITHEADPORTRAIT_URL = "users/{id}/avatar";
    @Multipart
    @POST(EDITHEADPORTRAIT_URL)
    Call<BaseResponse<String>> editHeadPortraitCall(@Path("id")String userId, @PartMap()MapGenerator file);

    /**
     * 查询个人信息
     */
    String GETUSERINFORMATION_URL= "users/{id}";
    @GET(GETUSERINFORMATION_URL)
    Call<BaseResponse<User>> getUserInfoCall(@Path("id")String userId);

/******************************************************************************************************************/
}
