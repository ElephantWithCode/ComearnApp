package com.example.team.commonlibrary.base.util.Retrofit.bean;

import java.util.List;

/**
 * Created by 邹特强 on 2018/6/10.
 */

public class UserTest {

    /**
     * uuid : sdfwer234sd34sdf
     * groupName : pjmike
     * groupInformation : pjmike
     * userTemp : [{"id":"sdfdsfsdf"},{"id":"sdfdsfsdf"}]
     */

    private String uuid;
    private String groupName;
    private String groupInformation;
    private List<UserTempBean> userTemps;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupInformation() {
        return groupInformation;
    }

    public void setGroupInformation(String groupInformation) {
        this.groupInformation = groupInformation;
    }

    public List<UserTempBean> getUserTemps() {
        return userTemps;
    }

    public void setUserTemps(List<UserTempBean> userTemps) {
        this.userTemps = userTemps;
    }

    public static class UserTempBean {
        /**
         * id : sdfdsfsdf
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
