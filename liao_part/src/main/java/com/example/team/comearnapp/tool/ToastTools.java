
package com.example.team.comearnapp.tool;

import android.widget.Toast;

import com.example.team.comearnapp.app.AppApplication;


/**
 * Created by XZY on 2017/4/4.
 */

public class ToastTools {
    public static void ToastShow(String s) {
        Toast.makeText(AppApplication.getContext(),s, Toast.LENGTH_SHORT).show();
    }
}