package com.example.team.comearnlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Ellly on 2018/2/13.
 */

public class ConvertTools {
    public static Bitmap drawableToBitmap(Drawable drawable){
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    public static Drawable bitmapToDrawable(Context context, Bitmap bitmap){
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public static String parseTime(int time){
        if (time < 10) {
            return "0" + String.valueOf(time);
        }else {
            return String.valueOf(time);
        }
    }
}
