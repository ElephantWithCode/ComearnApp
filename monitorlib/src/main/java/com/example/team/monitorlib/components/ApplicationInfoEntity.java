package com.example.team.monitorlib.components;

import android.graphics.drawable.Drawable;

/**
 * Created by Ellly on 2018/1/30.
 */

public class ApplicationInfoEntity {
    private String mPackageName;
    private String mAppLabel;
    private Drawable mAppIcon;

    public ApplicationInfoEntity(String mPackageName, String mAppLabel, Drawable mAppIcon) {
        this.mPackageName = mPackageName;
        this.mAppLabel = mAppLabel;
        this.mAppIcon = mAppIcon;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public String getAppLabel() {
        return mAppLabel;
    }

    public Drawable getAppIcon() {
        return mAppIcon;
    }
}
