package com.example.team.wang.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.team.wang_part.R;
import com.example.team.wang.entity.GroupInfo;

/**
 * Created by Ellly on 2018/2/11.
 * 自身初始化了上下padding
 */

public class GroupInfoPresentView extends RelativeLayout {

    public static final int PADDING_TOP_AND_BOTTOM = 35;
    private TextView mGroupName;

    public GroupInfoPresentView(Context context) {
        super(context);
        init();
    }

    public GroupInfoPresentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GroupInfoPresentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        View.inflate(getContext(), R.layout.activity_personal_info_group_info, this);

        mGroupName = findViewById(R.id.act_personal_info_group_info_txt_group_name);
        mGroupName.setTextSize(16);

        setPadding(0, PADDING_TOP_AND_BOTTOM, 0, PADDING_TOP_AND_BOTTOM);
    }

    public void setGroupInfo(GroupInfo info){
        mGroupName.setText(info.getGroupName());
    }

    /**
     * 设置点击事件：跳转到群组信息界面。
     * //TODO 现在仍未有具体的model，保留。
dd     */
    public void startActivityOn(){
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
