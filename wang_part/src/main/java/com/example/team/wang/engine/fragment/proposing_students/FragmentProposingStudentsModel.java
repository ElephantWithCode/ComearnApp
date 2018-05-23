package com.example.team.wang.engine.fragment.proposing_students;

import com.example.team.wang.entity.PersonInfo;
import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;

import java.util.ArrayList;

/**
 * Created by Ellly on 2018/2/20.
 */

public class FragmentProposingStudentsModel extends BaseModel {
    public ArrayList<PersonInfo> receiveQuitRequests(){
        ArrayList<PersonInfo> personInfos = new ArrayList<>();
        personInfos.add(new PersonInfo().setName("用户名"));
        personInfos.add(new PersonInfo().setName("用户名"));
        personInfos.add(new PersonInfo().setName("用户名"));
        personInfos.add(new PersonInfo().setName("用户名"));
        personInfos.add(new PersonInfo().setName("用户名"));
        return personInfos;
    }
}
