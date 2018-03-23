package com.example.team.comearnapp.entity;

/**
 * Created by Ellly on 2018/2/12.
 */

public class PersonInfo {
    private String mAge;
    private String mName;

    public PersonInfo(String mAge) {
        this.mAge = mAge;
    }

    public PersonInfo(){
    }

    public PersonInfo setName(String name){
        mName = name;
        return this;
    }


    public String getAge() {
        return mAge;
    }

    public String getName() {
        return mName;
    }
}
