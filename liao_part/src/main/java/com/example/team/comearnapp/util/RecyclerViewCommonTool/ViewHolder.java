package com.example.team.comearnapp.util.RecyclerViewCommonTool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ellly on 2017/8/4.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;
    private int mViewType;


    public ViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }

    public ViewHolder(Context context, View itemView, ViewGroup parent, int viewType) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
        mViewType=viewType;
    }

    /**
     * 获取Item
     * @param context 上下文
     * @param parent 父布局
     * @param layoutId
     * @return 返回一个控件
     **/
    public static ViewHolder get(Context context, ViewGroup parent, int layoutId){
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(context, itemView, parent);
        return holder;
    }

    /**
     * 获取Item
     * @param context 上下文
     * @param parent 父布局
     * @param layoutId
     * @param viewType 类型
     * @return 返回一个控件
     **/
    public static ViewHolder get(Context context, ViewGroup parent, int layoutId, int viewType){
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder holder = new ViewHolder(context, itemView, parent,viewType);
        return holder;
    }





    /**
     * 获取Item
     * @return 返回一个控件
     **/
    public View getItemView(){
        return mConvertView;
    }


    /**
    * 通过view的id获取对应的控件，如果没有则加入views中
    * @param viewId 控件的id
    * @return 返回一个控件
    **/

    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}
