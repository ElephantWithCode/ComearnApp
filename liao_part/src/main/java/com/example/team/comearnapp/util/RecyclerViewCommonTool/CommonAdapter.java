package com.example.team.comearnapp.util.RecyclerViewCommonTool;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Ellly on 2017/8/5.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder>  {


    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
//    protected OnListFragmentInteractionListener mListener;

    public CommonAdapter(Context context, int layoutId, List<T> datas){
        mContext = context;
        mLayoutId = layoutId;
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
//        mListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ViewHolder holder = ViewHolder.get(mContext, viewGroup, mLayoutId,-1);//default value is -1
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
//        viewHolder.updatePosition(position);
        convert(viewHolder, mDatas.get(position));
        /*viewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    int position = viewHolder.getAdapterPosition();
                    mListener.onListFragmentInteraction(mDatas.get(position));
                }
            }
        });*/
    }
    public abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
