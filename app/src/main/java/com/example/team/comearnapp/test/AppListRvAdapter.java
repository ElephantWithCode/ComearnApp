package com.example.team.comearnapp.test;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.team.comearnapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellly on 2017/12/9.
 */

public class  AppListRvAdapter<T> extends RecyclerView.Adapter<AppListRvAdapter.AppListRvViewHolder> {

    public static class AppListRvViewHolder extends RecyclerView.ViewHolder{

        TextView mAppNameTx;
        CheckBox mAppNameCb;

        public AppListRvViewHolder(View itemView) {
            super(itemView);
        }
    }

    private ArrayList<CheckBox> mCheckBoxList = new ArrayList<>();

    private ArrayList<T> mItemList = new ArrayList<>();

    public ArrayList<CheckBox> getCheckBoxList(){
        return mCheckBoxList;
    }

    public AppListRvAdapter(ArrayList<T> tList){
        mItemList = tList;
    }

    @Override
    public AppListRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.item_act_mock_rv_app_list_layout, null);
        AppListRvViewHolder holder = new AppListRvViewHolder(itemView);
        holder.mAppNameTx = itemView.findViewById(R.id.act_mock_rv_app_list_tv_app_name);
        holder.mAppNameCb = itemView.findViewById(R.id.act_mock_rv_app_list_cb_app_name);
        mCheckBoxList.add(holder.mAppNameCb);
        return holder;
    }

    @Override
    public void onBindViewHolder(AppListRvViewHolder holder, int position) {
        holder.mAppNameTx.setText((CharSequence) mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
