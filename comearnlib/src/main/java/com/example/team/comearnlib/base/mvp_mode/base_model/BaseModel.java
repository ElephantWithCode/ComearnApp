package com.example.team.comearnlib.base.mvp_mode.base_model;

import com.example.team.commonlibrary.base.application.MyApp;
import com.example.team.commonlibrary.base.util.DbUtil;

/**
 * Created by Ellly on 2017/11/25.
 */

public class BaseModel {
    public String getThisUerId(){
        return DbUtil.getString(MyApp.getGlobalContext(), "user_id", "");
    }
}
