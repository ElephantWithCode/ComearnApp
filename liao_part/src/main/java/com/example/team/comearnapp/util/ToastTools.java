
package com.example.team.comearnapp.util;

import android.widget.Toast;

import com.example.team.commonlibrary.base.MyApp;


/**
 * Created by XZY on 2017/4/4.
 */

public class ToastTools {
    public static void ToastShow(String s) {
        Toast.makeText(MyApp.getGlobalContext(),s, Toast.LENGTH_SHORT).show();
    }
}