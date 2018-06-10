package com.example.team.commonlibrary.base.util.Retrofit.bean;

import com.example.team.commonlibrary.base.util.Retrofit.bean.User;

import java.util.List;

/**
 * Created by 邹特强 on 2018/6/5.
 */

public class Group {
    /**
     * 群组对应的id
     */
    String groupId;

    /**
     * 群组对应的群组名
     */
    String groupName;
    /**
     * 群组成员列表
     */
    List<User> users;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
