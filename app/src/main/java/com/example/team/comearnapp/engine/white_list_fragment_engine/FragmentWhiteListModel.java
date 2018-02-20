package com.example.team.comearnapp.engine.white_list_fragment_engine;

import android.content.Context;

import com.example.team.comearnapp.entity.AppInfo;
import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;
import com.example.team.comearnlib.utils.ConvertTools;
import com.example.team.monitorlib.components.AppMonitor;
import com.example.team.monitorlib.components.ApplicationInfoEntity;

import java.util.ArrayList;

/**
 * Created by Ellly on 2018/2/20.
 */
public class FragmentWhiteListModel extends BaseModel {
    private AppMonitor mMonitor = new AppMonitor();

    public void attach(Context context){mMonitor.attach(context);}

    public void detach(){mMonitor.detach();}

    public ArrayList<AppInfo> getAppInfos(){
        return adaptType(mMonitor.getAllInformationList());
    }

    public void switchQueryScope(){mMonitor.switchQueryScope();}

    protected ArrayList<AppInfo> adaptType(ArrayList<ApplicationInfoEntity> entities){
        ArrayList<AppInfo> appInfos = new ArrayList<>();
        for (ApplicationInfoEntity entity : entities){
            AppInfo info = new AppInfo(ConvertTools.drawableToBitmap(entity.getAppIcon()), entity.getAppLabel(), entity.getPackageName());
            appInfos.add(info);
        }
        return appInfos;
    }

    private ArrayList adapteType(ArrayList<AppInfo> infos){
        //TODO 准备用来适应后端的数据结构。
        return infos;
    }
}
