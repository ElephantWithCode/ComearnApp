package com.example.team.comearnlib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Ellly on 2017/11/27.
 */

public class ToastTools {

    public static void showToast(Context context, String content){
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
