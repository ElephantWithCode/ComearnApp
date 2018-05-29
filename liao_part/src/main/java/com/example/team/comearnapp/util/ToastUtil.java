package com.example.team.comearnapp.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 邹特强 on 2018/4/2.
 * @author 邹特强
 */

public class ToastUtil {
    /**
     * Toast短时间显示
     */
    public static void ToastShortShow(String s, Context mContext) {
        Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast长时间显示
     */
    public static void ToastLongShow(String s,Context mContext){
        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
    }
}
