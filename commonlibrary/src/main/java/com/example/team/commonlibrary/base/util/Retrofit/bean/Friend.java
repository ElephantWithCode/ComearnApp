package com.example.team.commonlibrary.base.util.Retrofit.bean;

/**
 * Created by 邹特强 on 2018/6/3.
 * 仅供融云测试
 */

public class Friend {
    private String userId;
    private String name;
    private String portraitUri;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortraitUri() {
        return portraitUri;
    }

    public void setPortraitUri(String portraitUri) {
        this.portraitUri = portraitUri;
    }

    public Friend(String userId, String name, String portraitUri) {
        this.userId = userId;
        this.name = name;
        this.portraitUri = portraitUri;
    }
}
