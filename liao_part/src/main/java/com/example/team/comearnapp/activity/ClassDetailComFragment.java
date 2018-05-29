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
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;

/**
 * 已加入的群组信息页面的综合Fragment
 * 可以修改本群昵称
 * 是查看考勤记录（CheckActivity）的入口
 * 可以设置群组为特别关注
 * 是自习活动的入口
 * */
public class ClassDetailComFragment extends Fragment implements View.OnClickListener {
//    RecyclerListAdapter mListAdapter;
    private CommonAdapter adapter;

    static class check{

    }
    TextView user_name_tv;
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
        user_name_tv = (TextView) view.findViewById(R.id.user_name_tv);
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
                            Toast.makeText(getActivity(), "您的昵称: " + text, Toast.LENGTH_SHORT).show();
                            user_name_tv.setText(text);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "请填入昵称", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }
}
