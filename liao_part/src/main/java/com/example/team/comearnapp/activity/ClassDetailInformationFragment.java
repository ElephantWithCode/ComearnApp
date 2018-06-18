package com.example.team.comearnapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.team.comearnapp.R;

/**
 * 已加入的群组信息页面的信息Fragment
 * 是查看管理员（SelectActivity）、群成员（SelectActivity）、白名单（汪工待完成）的入口
 * */
public class ClassDetailInformationFragment extends Fragment implements View.OnClickListener{


    private android.widget.TextView class_name_tv;
    private android.widget.TextView class_information_tv;
    private android.widget.TextView recommend_count;
    private android.widget.TextView read_count;
    private android.widget.TextView fans_count;
    private android.widget.ImageView arrow_manager;
    private android.widget.ImageView arrow_member;
    private android.widget.ImageView arrow_white_list;
    private com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton exit_class;
    public ClassDetailInformationFragment() {
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
        View root = inflater.inflate(R.layout.act_class_detail_fragment_information, container, false);
        this.arrow_white_list = (ImageView) root.findViewById(R.id.arrow_white_list);
        this.arrow_member= (ImageView) root.findViewById(R.id.arrow_member);
        this.arrow_manager = (ImageView) root.findViewById(R.id.arrow_manager);
        this.fans_count = (TextView) root.findViewById(R.id.fans_count);
        this.read_count = (TextView) root.findViewById(R.id.read_count);
        this.recommend_count = (TextView) root.findViewById(R.id.recommend_count);
        this.class_name_tv = (TextView) root.findViewById(R.id.class_name_tv);
        this.class_information_tv = (TextView) root.findViewById(R.id.class_information_tv);
        this.exit_class= (com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton) root.findViewById(R.id.exit_class);
        arrow_white_list.setOnClickListener(this);
        arrow_member.setOnClickListener(this);
        arrow_manager.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View var1) {
        int i = var1.getId();
        if (i == R.id.arrow_white_list) {//TODO:汪工在这里跳转到白名单页面
            ARouter.getInstance().build("/wang_part/white_list").navigation();
        } else if (i == R.id.arrow_member) {
            Intent intent = new Intent(getContext(), SelectActivity.class);
            intent.putExtra("mode","member");
            intent.putExtra("permission","creator");
            //三种权限：creator(+可设置和删除成员),admin(+可删除成员),visitor
            startActivity(intent);

        } else if (i == R.id.arrow_manager) {
            Intent intent2 = new Intent(getContext(), SelectActivity.class);
            intent2.putExtra("mode","manager");
            intent2.putExtra("permission","creator");
            startActivity(intent2);

        } else if (i == R.id.exit_class) {
            //Todo:开始邹工的表演

        }
    }
}
