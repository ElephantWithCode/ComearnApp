package com.example.team.comearnapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.bean.SearchUser;
import com.example.team.comearnapp.tool.RecyclerViewCommonTool.CommonAdapter;
import com.example.team.comearnapp.tool.RecyclerViewCommonTool.ViewHolder;
import com.example.team.comearnapp.ui.ClearEditText;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SearchFragment extends Fragment {
    private static String ARG_PARAM = "param_key";
    private String mParam;
    private Activity mActivity;

    ClearEditText mClearEditText;

    QMUIGroupListView mAboutGroupListView;

    RecyclerView mRecyclerView;
    private CommonAdapter adapter_user;
    private CommonAdapter adapter_class;


    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance(String str) {
        SearchFragment fragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM, str);
        fragment.setArguments(bundle);   //设置参数
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.act_search_fragment_search, container, false);
        ButterKnife.bind(this, root);

        mClearEditText = root.findViewById(R.id.clearEditText);

        mAboutGroupListView = root.findViewById(R.id.about_list);

        mRecyclerView = root.findViewById(R.id.act_search_rv);


//        TextView view = root.findViewById(R.id.button);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SearchResultActivity sActivity=(SearchResultActivity)getActivity();
//                sActivity.switchClassImfomation();
//            }
//        });


        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        DividerItemDecoration mDecoration = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        mDecoration.setDrawable(ContextCompat.getDrawable(mActivity, R.drawable.divider_bg));
        mRecyclerView.setHasFixedSize(true);

        ArrayList<SearchUser> checksArrayList=new ArrayList<SearchUser>();
        checksArrayList.add(new SearchUser());
        adapter_user=new CommonAdapter<SearchUser>(mActivity, R.layout.user_item, checksArrayList){
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }

            @Override
            public void convert(ViewHolder holder, SearchUser user) {
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mActivity,ClassDetailActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };

        adapter_class=new CommonAdapter<SearchUser>(mActivity, R.layout.class_item, checksArrayList){
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }

            @Override
            public void convert(ViewHolder holder, SearchUser user) {


                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mActivity,ClassDetailActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };

        // 搜索框的键盘搜索键点击回调
        mAboutGroupListView.setVisibility(View.GONE);
        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    mAboutGroupListView.setVisibility(View.GONE);
                } else {
                    mAboutGroupListView.setVisibility(View.VISIBLE);
                }
            }
        });


        QMUIGroupListView.newSection(mActivity)
                .addItemView(mAboutGroupListView.createItemView("查找学呗用户"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAboutGroupListView.setVisibility(View.GONE);
                        mRecyclerView.setAdapter(adapter_user);
                    }
                })
                .addItemView(mAboutGroupListView.createItemView("查找班级群组"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAboutGroupListView.setVisibility(View.GONE);
                        mRecyclerView.setAdapter(adapter_class);
                    }
                })
                .addItemView(mAboutGroupListView.createItemView("查找自习群组"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAboutGroupListView.setVisibility(View.GONE);
                        mRecyclerView.setAdapter(adapter_class);
                    }
                })
                .addTo(mAboutGroupListView);



        return root;



    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mParam = getArguments().getString(ARG_PARAM);  //获取参数
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
