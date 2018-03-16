package com.example.team.comearnapp.app;

import android.app.Application;
import android.content.Context;


public class AppApplication extends Application {
	private static AppApplication mAppApplication;
	private static Context mContext;

	private boolean isNightMode = false;

	public void setNightMode(boolean nightMode){
		isNightMode = nightMode;
	}
	public boolean isNightMode(){
		return isNightMode;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mContext = getApplicationContext();
		mAppApplication = this;

	}

	/**
	 * 获取Application
	 */
	public static AppApplication getApp() {
		return mAppApplication;
	}

	/**
	 * 获取context
	 */
	public static Context getContext() {
		return mContext;
	}

}