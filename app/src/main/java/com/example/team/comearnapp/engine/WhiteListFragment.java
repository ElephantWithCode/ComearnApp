package com.example.team.comearnapp.engine;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.entity.AppInfo;
import com.example.team.comearnlib.base.mvp_mode.base_model.BaseModel;
import com.example.team.comearnlib.base.mvp_mode.base_presenter.BasePresenter;
import com.example.team.comearnlib.base.mvp_mode.base_view.IBaseView;
import com.example.team.comearnlib.utils.ConvertTools;
import com.example.team.monitorlib.components.AppMonitor;
import com.example.team.monitorlib.components.ApplicationInfoEntity;

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
    boolean appsShowType();
}

class FragmentWhiteListPresenter extends BasePresenter<FragmentWhiteListView>{

    public static final String TAG = "WLF";
    private FragmentWhiteListModel mModel = new FragmentWhiteListModel();
    private Handler mUpdateHandler = new Handler();

    public FragmentWhiteListPresenter(){

    }

    @Override
    public void attachView(FragmentWhiteListView view) {
        super.attachView(view);
        mModel.attach(mView.getContext());
    }

    @Override
    public void detachView() {
        super.detachView();
        mModel.detach();
    }

    public void updateAppList(){
        mView.presentLoading();
        new Thread(){
            @Override
            public void run() {
                if (mView.appsShowType()){
                    mModel.switchQueryScope();
                }
                final ArrayList<AppInfo> appInfos = mModel.getAppInfos();
                mUpdateHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mView.updateAppList(appInfos);
                        mView.stopLoading();
                    }
                }, 0);
            }
        }.start();
    }
}

class FragmentWhiteListModel extends BaseModel{
    private AppMonitor mMonitor = new AppMonitor();

    public void attach(Context context){mMonitor.attach(context);}

    public void detach(){mMonitor.detach();}

    public ArrayList<AppInfo> getAppInfos(){
        return adaptType(mMonitor.getAllInformationList());
    }

    public void switchQueryScope(){mMonitor.switchQueryScope();}

    private ArrayList<AppInfo> adaptType(ArrayList<ApplicationInfoEntity> entities){
        ArrayList<AppInfo> appInfos = new ArrayList<>();
        for (ApplicationInfoEntity entity : entities){
            AppInfo info = new AppInfo(ConvertTools.drawableToBitmap(entity.getAppIcon()), entity.getAppLabel(), entity.getPackageName());
            appInfos.add(info);
        }
        return appInfos;
    }
}
public class WhiteListFragment extends Fragment implements FragmentWhiteListView{

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private WhiteListAdapter mAdapter;
    private ArrayList<AppInfo> mInfos = new ArrayList<>();
    private FragmentWhiteListPresenter mPresenter = new FragmentWhiteListPresenter();
    private boolean mShowSystemApps = false;

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
}


class WhiteListAdapter extends SuperAdapter<AppInfo>{

    public WhiteListAdapter(Context context, List<AppInfo> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, AppInfo item) {
        final AppInfo currentItem = item;
        ((ImageView)holder.findViewById(R.id.act_white_list_rv_app_list_iv_app_icon)).setImageBitmap(currentItem.getAppIcon());
        ((TextView)holder.findViewById(R.id.act_white_list_rv_app_list_tv_app_label)).setText(currentItem.getAppLabel());
        ((CheckBox)holder.findViewById(R.id.act_white_list_rv_app_list_cb_app_selected)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                currentItem.setAppSelected(b);
            }
        });
        ((CheckBox)holder.findViewById(R.id.act_white_list_rv_app_list_cb_app_selected)).setChecked(currentItem.getAppSeleted());
    }
}