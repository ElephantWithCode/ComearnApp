package com.example.team.comearnapp.test;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellly on 2018/1/27.
 */

public class PackageInformationExtractor {
    private List<PackageInfo> mPackages;
    private int mSystemFlag = 0;

    public PackageInformationExtractor(List<PackageInfo> packageInfo){
        mPackages = packageInfo;
    }

    public void switchScope(){
        if (mSystemFlag == 0){
            mSystemFlag = 1;
        }else {
            mSystemFlag = 0;
        }
    }

    public ArrayList<String> getAppNames(PackageManager manager){
        ArrayList<String> names = new ArrayList<>();
        for (PackageInfo pi : mPackages){
            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == mSystemFlag){
                names.add(pi.applicationInfo.loadLabel(manager).toString());
            }
        }
        return names;
    }

    public ArrayList<String> getPackageNames(){
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < mPackages.size(); i++) {
            PackageInfo packageInfo = mPackages.get(i);
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) ==  mSystemFlag) {
                names.add(packageInfo.packageName);
            }
        }
        return names;
    }


}
