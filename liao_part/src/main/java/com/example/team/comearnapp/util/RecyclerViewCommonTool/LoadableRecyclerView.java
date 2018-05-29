package com.example.team.comearnapp.util.RecyclerViewCommonTool;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.team.comearnapp.util.ToastTools;


/**
 * Created by Ellly on 2017/8/23.
 */

public abstract class LoadableRecyclerView extends RecyclerView {
    private long lastScrollItemPosition = 0;
    private LinearLayoutManager mLayoutManager;
    private LoadableRecyclerView mSelf;
    public LoadableRecyclerView(Context context) {
        super(context);
        mSelf = this;
    }

    public LoadableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (getLayoutManager() instanceof LinearLayoutManager) {
            mLayoutManager = (LinearLayoutManager) getLayoutManager();
        }
        if (mLayoutManager == null){
            ToastTools.ToastShow("未添加LayoutManager");
            return;
        }
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastScrollItemPosition + 2 > mLayoutManager.getItemCount()){
                    onBottomRefreshData(mSelf);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastScrollItemPosition = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    public abstract void onBottomRefreshData(LoadableRecyclerView recyclerview);
}
