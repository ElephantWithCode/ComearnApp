package com.example.team.comearnapp.engine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.team.comearnapp.R;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by 邹特强 on 2018/6/3.
 * 仅供融云测试
 */

public class FriendFragment extends Fragment {

    public static FriendFragment instance = null;   // 单例模式
    private View mView;

    public static FriendFragment getInstance() {
        if (instance == null) {
            instance = new FriendFragment();
        }
        return instance;
    }

    private Button mButton_Friend;
    private Button mButton_Customer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_friend, container, false);

        mButton_Friend = (Button) mView.findViewById(R.id.friend);
        mButton_Friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(getActivity(), "10086", "私人聊天");
                }
            }
        });

        mButton_Customer = (Button) mView.findViewById(R.id.customer);
        mButton_Customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startConversation(getActivity(),
                            Conversation.ConversationType.APP_PUBLIC_SERVICE, "KEFU145793828389012", "在线客服--夜雨");
                }
            }
        });
        return mView;
    }
}

