package com.example.team.monitorlib.tools;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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

    public void setToExtractList(List<PackageInfo> packageInfos){
        mPackages = packageInfos;
    }

    public PackageInformationExtractor(){}

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

    public ArrayList<Drawable> getAppIcons(PackageManager manager){
        ArrayList<Drawable> icons = new ArrayList<>();
        for (PackageInfo pi : mPackages){
            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == mSystemFlag){
                icons.add(pi.applicationInfo.loadIcon(manager));
            }
        }
        return icons;
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
