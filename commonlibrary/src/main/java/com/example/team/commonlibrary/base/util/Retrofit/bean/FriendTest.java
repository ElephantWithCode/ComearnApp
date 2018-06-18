package com.example.team.commonlibrary.base.util.Retrofit.bean;

/**
 * Created by 邹特强 on 2018/6/18.
 * 仅用于好友列表测试
 */

public class FriendTest {

    /**
     * userUuid : 发起人id
     * userAlias : 发起人用户名
     * userAatar : 发起人头像
     * friendUuid : 朋友id
     * friendAlias : 朋友用户名
     * friendAvatar : 朋友头像
     * msg : 附加消息
     * remark : 备注
     * status : 添加好友时的状态,好友添加成功为0，被拒绝为1,默认为1
     */

    private String userUuid;
    private String userAlias;
    private String userAatar;
    private String friendUuid;
    private String friendAlias;
    private String friendAvatar;
    private String msg;
    private String remark;
    private String status;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public String getUserAatar() {
        return userAatar;
    }

    public void setUserAatar(String userAatar) {
        this.userAatar = userAatar;
    }

    public String getFriendUuid() {
        return friendUuid;
    }

    public void setFriendUuid(String friendUuid) {
        this.friendUuid = friendUuid;
    }

    public String getFriendAlias() {
        return friendAlias;
    }

    public void setFriendAlias(String friendAlias) {
        this.friendAlias = friendAlias;
    }

    public String getFriendAvatar() {
        return friendAvatar;
    }

    public void setFriendAvatar(String friendAvatar) {
        this.friendAvatar = friendAvatar;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
