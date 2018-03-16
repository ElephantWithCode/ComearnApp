package com.example.team.comearnapp.tool.RecyclerViewCommonTool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.team.comearnapp.R;


/**
 * Created by Ellly on 2017/8/30.
 */

public class CommonLastViewHolder extends RecyclerView.ViewHolder {

    private CommonLastViewHolder(View itemView) {
        super(itemView);
    }

    public static CommonLastViewHolder newInstance(Context context) {
        View essentialView = LayoutInflater.from(context).inflate(R.layout.common_last_view_holder_layout, null);
        return new CommonLastViewHolder(essentialView);
    }
}
