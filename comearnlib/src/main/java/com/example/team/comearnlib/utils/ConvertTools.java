package com.example.team.comearnlib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.Calendar;

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

    public static String pickNumberFromString(String str){
        str = str.trim();
        StringBuilder str2 = new StringBuilder();
        if(!"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    str2.append(str.charAt(i));
                }
            }
        }
        return str2.toString();
    }

    public static long getTimeInMillisFromAssignedHourAndMinute(String time){
        Calendar calendar = Calendar.getInstance();
        String[] hourAndMinute = time.split(":");
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE),
                Integer.parseInt(hourAndMinute[0]),
                Integer.parseInt(hourAndMinute[1]),
                0);
        Log.d("CMF", "original: " + calendar.toString());
        return calendar.getTimeInMillis();
    }

    public static Calendar constructFromTimeInMilis(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

}
