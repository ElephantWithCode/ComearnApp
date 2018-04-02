package com.example.team.wang.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ellly on 2018/2/12.
 */

public class AppInfo implements Parcelable{

    private Bitmap mAppIcon;
    private String mAppLabel;
    private String mAppPackageName;
    private boolean mAppSelected;

    public AppInfo(Bitmap mAppIcon, String mAppLabel, String mAppPackageName){
        this.mAppIcon = mAppIcon;
        this.mAppLabel = mAppLabel;
        this.mAppPackageName = mAppPackageName;
        this.mAppSelected = false;
    }

    public AppInfo(){}

    protected AppInfo(Parcel in) {
        mAppIcon = in.readParcelable(Bitmap.class.getClassLoader());
        mAppLabel = in.readString();
        mAppPackageName = in.readString();
        mAppSelected = in.readByte() != 0;
    }

    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>() {
        @Override
        public AppInfo createFromParcel(Parcel in) {
            return new AppInfo(in);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };

    public Bitmap getAppIcon() {
        return mAppIcon;
    }

    public String getAppLabel() {
        return mAppLabel;
    }

    public String getAppPackageName() {
        return mAppPackageName;
    }

    public boolean getAppSeleted(){return mAppSelected;}

    public void setAppSelected(boolean selected){
        this.mAppSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mAppIcon, i);
        parcel.writeString(mAppLabel);
        parcel.writeString(mAppPackageName);
        parcel.writeByte((byte) (mAppSelected ? 1 : 0));
    }
}
