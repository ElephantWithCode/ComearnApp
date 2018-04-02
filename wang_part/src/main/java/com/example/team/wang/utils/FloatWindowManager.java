package com.example.team.wang.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.team.comearnlib.utils.ToastTools;

/**
 * Created by Ellly on 2018/3/25.
 */

public class FloatWindowManager {

    private Context mContext;
    private AppCompatActivity mActivity;

    private WindowManager mManager;
    private WindowManager.LayoutParams mParams;

    private View mView;

    private int mLayoutId;

    public FloatWindowManager(Context mContext) {
        this.mContext = mContext;
        mManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    }

    public FloatWindowManager(AppCompatActivity mActivity){
        this.mActivity = mActivity;
        this.mContext = mActivity;
        mManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
    }


    public FloatWindowManager setContentLayout(@LayoutRes int layoutId){
        mLayoutId = layoutId;
        mView = LayoutInflater.from(mContext).inflate(layoutId, null);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                Log.d("_FWM", "On Window Click");
            }
        });

        mParams = createLayoutParams();

        return this;
    }

    public void hideNavigationBar(){
        View decorView = mActivity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void cancel(){
        mManager.removeView(mView);
    }

    public void show(){
        if (Build.VERSION.SDK_INT >= 23){
            if (Settings.canDrawOverlays(mContext)){
                mManager.addView(mView, mParams);
            }else{
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                mContext.startActivity(intent);
            }
        }else {
            mManager.addView(mView, mParams);
        }
    }

    @NonNull
    private WindowManager.LayoutParams createLayoutParams() {
        WindowManager.LayoutParams mParams;
        mParams = new WindowManager.LayoutParams();
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mParams.format = PixelFormat.RGBA_8888;
//        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        mParams.flags = WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_FULLSCREEN;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.x = 0;
        mParams.y = 0;
        return mParams;
    }


}
