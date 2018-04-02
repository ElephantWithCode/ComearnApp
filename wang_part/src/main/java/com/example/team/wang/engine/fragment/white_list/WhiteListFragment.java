package com.example.team.wang.engine.fragment.white_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.team.wang_part.R;
import com.example.team.wang.entity.AppInfo;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

public class WhiteListFragment extends Fragment implements FragmentWhiteListView {

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private WhiteListAdapter mAdapter;
    private ArrayList<AppInfo> mInfos = new ArrayList<>();
    private FragmentWhiteListPresenter mPresenter = new FragmentWhiteListPresenter();
    private boolean mShowSystemApps = false;
    private boolean mCheckboxVisibility = true;

    private static String ARRAY_KEY = "APP_LIST";
    private static String SYSTEM_APP_KEY = "SYSTEM_APP_LIST";

    public WhiteListFragment(){}

    public static WhiteListFragment newInstance(boolean systemApps) {

        Bundle args = new Bundle();
        args.putBoolean(SYSTEM_APP_KEY, systemApps);

        WhiteListFragment fragment = new WhiteListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static WhiteListFragment newInstance(){
        Bundle args = new Bundle();

        WhiteListFragment fragment = new WhiteListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public WhiteListFragment setPresenter(FragmentWhiteListPresenter presenter){
        mPresenter = presenter;
        return this;
    }

    public WhiteListFragment setModel(FragmentWhiteListModel model){
        if (mPresenter != null){
            mPresenter.setModel(model);
        }
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attachView(this);
        if (getArguments() != null) {
            mShowSystemApps = getArguments().getBoolean(SYSTEM_APP_KEY);
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_white_list, container, false);
        mProgressBar = view.findViewById(R.id.act_white_list_pb);
        mRecyclerView = view.findViewById(R.id.act_white_list_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new WhiteListAdapter(getContext(), mInfos, R.layout.item_act_white_list_rv_app_list);
        mAdapter.setCheckboxVisibility(mCheckboxVisibility);
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.updateAppList();

        return view;
    }

    @Override
    public void updateAppList(ArrayList<AppInfo> infos) {
        mInfos.clear();
        mInfos.addAll(infos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void presentLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void stopLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean appsShowType() {
        return mShowSystemApps;
    }

    @Override
    public ArrayList<AppInfo> getInfos() {
        ArrayList<AppInfo> selected = new ArrayList<>();
        for (AppInfo info : mInfos){
            if (info.getAppSeleted()){
                selected.add(info);
            }
        }
        return selected;
    }

    @Override
    public WhiteListFragment setCheckboxVisibility(boolean visibility) {
        mCheckboxVisibility = visibility;
        return this;
    }
}


class WhiteListAdapter extends SuperAdapter<AppInfo>{

    private boolean mCheckboxVisibility = true;

    public WhiteListAdapter(Context context, List<AppInfo> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    public void setCheckboxVisibility(boolean visible){
        mCheckboxVisibility = visible;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, AppInfo item) {
        final AppInfo currentItem = item;
        ((ImageView)holder.findViewById(R.id.act_white_list_rv_app_list_iv_app_icon)).setImageBitmap(currentItem.getAppIcon());
        ((TextView)holder.findViewById(R.id.act_white_list_rv_app_list_tv_app_label)).setText(currentItem.getAppLabel());
        CheckBox checkBox = (CheckBox) holder.findViewById(R.id.act_white_list_rv_app_list_cb_app_selected);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                currentItem.setAppSelected(b);
            }
        });
        checkBox.setChecked(currentItem.getAppSeleted());
        if (mCheckboxVisibility) {
            checkBox.setVisibility(View.VISIBLE);

        }else {
            checkBox.setVisibility(View.GONE);
            holder.findViewById(R.id.act_white_list_rv_app_list_iv_app_icon).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = getContext();
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage(currentItem.getAppPackageName());
                    context.startActivity(intent);
                }
            });
        }
    }
}