package com.example.team.comearnapp.bean;

/**
 * Created by Chliao on 2018/2/19.
 */

public class Class{
    private String mClassName;
    private String mClassBrief;
    private int mClassHeader;
    private long mClassId;
    private  boolean isStudying;

    public Class(String mClassName,String mClassBrief,int mClassHeader,long mClassId,boolean isStudying){
        this.mClassName=mClassName;
        this.mClassBrief=mClassBrief;
        this.mClassHeader=mClassHeader;
        this.mClassId=mClassId;
        this.isStudying=isStudying;
    }

    public int getmClassHeader() {
        return mClassHeader;
    }

    public long getmClassId() {
        return mClassId;
    }

    public String getmClassBrief() {
        return mClassBrief;
    }

    public String getmClassName() {
        return mClassName;
    }

    public void setmClassBrief(String mClassBrief) {
        this.mClassBrief = mClassBrief;
    }

    public void setmClassHeader(int mClassHeader) {
        this.mClassHeader = mClassHeader;
    }

    public void setmClassId(long mClassId) {
        this.mClassId = mClassId;
    }

    public void setmClassName(String mClassName) {
        this.mClassName = mClassName;
    }

    public boolean isStudying() {
        return isStudying;
    }

    public void setStudying(boolean studying) {
        isStudying = studying;
    }
}

