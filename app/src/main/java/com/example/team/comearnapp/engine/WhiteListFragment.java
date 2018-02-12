package com.example.team.comearnapp.engine;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.entity.AppInfo;
import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellly on 2018/2/12.
 */

interface FragmentWhiteListView extends IBaseView{
    void updateAppList(ArrayList<AppInfo> infos);
    void presentLoading();
    void stopLoading();
}

class FragmentWhiteListPresenter extends BasePresenter<FragmentWhiteListView>{
     private FragmentWhiteListModel mModel = new FragmentWhiteListModel();

    public void updateAppList(){
        mView.presentLoading();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mView.updateAppList(mModel.getAppInfos());
                mView.stopLoading();
            }
        });
    }
}

class FragmentWhiteListModel extends BaseModel{
    ArrayList<AppInfo> getAppInfos(){
        return new ArrayList<>();
    }
}
public class WhiteListFragment extends Fragment implements FragmentWhiteListView{

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private WhiteListAdapter mAdapter;
    private ArrayList<AppInfo> mInfos = new ArrayList<>();
    private FragmentWhiteListPresenter mPresenter = new FragmentWhiteListPresenter();

    private static String ARRAY_KEY = "APP_LIST";

    public WhiteListFragment(){}

    public static WhiteListFragment newInstance() {

        Bundle args = new Bundle();

        WhiteListFragment fragment = new WhiteListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter.attachView(this);
        super.onCreate(savedInstanceState);
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

        mPresenter.updateAppList();
        return super.onCreateView(inflater, container, savedInstanceState);
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
}


class WhiteListAdapter extends SuperAdapter<AppInfo>{

    public WhiteListAdapter(Context context, List<AppInfo> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, AppInfo item) {
        ((ImageView)holder.findViewById(R.id.act_white_list_rv_app_list_app_icon)).setImageBitmap(item.getAppIcon());
        ((TextView)holder.findViewById(R.id.act_white_list_rv_app_list_app_label)).setText(item.getAppLabel());
    }
}