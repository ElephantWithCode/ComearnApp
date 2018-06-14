package com.example.team.comearnapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.util.RecyclerViewCommonTool.CommonAdapter;
import com.example.team.comearnapp.util.RecyclerViewCommonTool.ViewHolder;
import com.example.team.commonlibrary.base.util.MapGenerator;
import com.example.team.commonlibrary.base.util.Retrofit.RetroHttpUtil;
import com.example.team.commonlibrary.base.util.Retrofit.bean.BaseResponse;
import com.example.team.commonlibrary.base.util.Retrofit.callback.AbstractCommonHttpCallback;
import com.example.team.commonlibrary.base.util.ToastUtil;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;

/**
 * 已加入的群组信息页面的综合Fragment
 * 可以修改本群昵称
 * 是查看考勤记录（CheckActivity）的入口
 * 可以设置群组为特别关注
 * 是自习活动的入口
 * TODO:廖姐姐写的代码是真的丑，命名规范什么的完全没有在意，onCreatView不能积累太多代码没有注意，low
 * */
public class ClassDetailComFragment extends Fragment implements View.OnClickListener {
//    RecyclerListAdapter mListAdapter;
    private CommonAdapter adapter;

    static class check{

    }

    /**
     * 群组名
     */
    TextView tvGroupName;
    SwitchButton focus_sb;
    RecyclerView mRecyclerView;
    public ClassDetailComFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.act_class_detail_fragment_com, container, false);
        TextView comment_text = (TextView) view.findViewById(R.id.comment_text);
        focus_sb = (SwitchButton) view.findViewById(R.id.focus_sb);
        ImageView check_record_iv = (ImageView) view.findViewById(R.id.check_record_iv);
        ImageView change_name_iv = (ImageView) view.findViewById(R.id.change_name_iv);
        tvGroupName = (TextView) view.findViewById(R.id.group_name_tv);
        tvGroupName.setText(getActivity().getIntent().getStringExtra("groupName"));
        TextView notice_tv = (TextView) view.findViewById(R.id.notice_tv);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.act_class_detail_activity_rv);
        change_name_iv.setOnClickListener(this);
        check_record_iv.setOnClickListener(this);


        focus_sb.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getActivity(), "已将该群组设为特别关注", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "已将该群组取消特别关注", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        DividerItemDecoration mDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_bg));
        mRecyclerView.setHasFixedSize(true);

        ArrayList<CheckActivity.check> checksArrayList=new ArrayList<CheckActivity.check>();
        checksArrayList.add(new CheckActivity.check());


        adapter=new CommonAdapter<CheckActivity.check>(getContext(), R.layout.act_class_detail_activity_item, checksArrayList){
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }

            @Override
            public void convert(ViewHolder holder, CheckActivity.check mCheck) {

            }
        };

        mRecyclerView.setAdapter(adapter);






        return view;
    }


    @Override
    public void onClick(View var1) {
        int i = var1.getId();
        if (i == R.id.change_name_iv) {
            showEditNameDialog();

        } else if (i == R.id.check_record_iv) {
            Toast.makeText(getActivity(), "考勤界面", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getContext(), CheckActivity.class);
            startActivity(intent);

        }
    }


    private void showEditNameDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("标题")
                .setPlaceholder("在此输入您的昵称")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        if (text != null && text.length() > 0) {
                            Call<BaseResponse<Object>> changeGroupNameCall = RetroHttpUtil.build().changeGroupNameCall(getActivity().getIntent().getStringExtra("groupId"), MapGenerator.generate().add("groupName",text.toString()));
                            RetroHttpUtil.sendRequest(changeGroupNameCall, new AbstractCommonHttpCallback<BaseResponse<Object>>() {
                                @Override
                                public void onSuccess(BaseResponse<Object> result) {
                                    tvGroupName.setText(builder.getEditText().getText());
                                    ToastUtil.ToastShortShow("修改成功",getActivity());
                                }

                                @Override
                                public void onFail() {
                                    ToastUtil.ToastShortShow("修改失败",getActivity());
                                }
                            });
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "请填入昵称", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }
}
