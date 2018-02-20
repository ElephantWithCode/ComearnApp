package com.example.team.comearnapp.engine.fragment.class_main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.team.comearnapp.R;

/**
 * Created by Ellly on 2018/2/20.
 */

public class ClassMainFragment extends Fragment {

    public static final String TAG = "CMF";
    private TextView mQuitBtn;

    public static ClassMainFragment newInstance() {

        Bundle args = new Bundle();

        ClassMainFragment fragment = new ClassMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_class_main, container, false);

        mQuitBtn = view.findViewById(R.id.frag_class_main_btn_quit);
        mQuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "quit button clicked");

            }
        });

        return view;
    }
}
