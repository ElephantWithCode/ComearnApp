package com.example.team.comearnapp.engine.fragment.proposing_students;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.entity.PersonInfo;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ellly on 2018/2/20.
 */

public class ProposingStudentsFragment extends Fragment implements FragmentProposingStudentsView{

    private ProposingStudentsAdapter mAdapter;
    private RecyclerView mRecyView;

    private ArrayList<PersonInfo> mInfos = new ArrayList<>();
    private ProgressBar mProgressBar;

    private FragmentProposingStudentsPresenter mPresenter = new FragmentProposingStudentsPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.attachView(this);

    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proposing_students_list, container, false);

        mProgressBar = view.findViewById(R.id.act_proposing_students_list_pb);

        mRecyView = view.findViewById(R.id.act_proposing_students_list_rv);
        mRecyView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mAdapter = new ProposingStudentsAdapter(getContext(), mInfos, R.layout.item_proposing_students_rv_student);

        mRecyView.setAdapter(mAdapter);

        mPresenter.updateList();

        return view;
    }

    public static ProposingStudentsFragment newInstance() {

        Bundle args = new Bundle();

        ProposingStudentsFragment fragment = new ProposingStudentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void updateList(ArrayList<PersonInfo> list) {
        ArrayList<PersonInfo> tmp = new ArrayList<>();
        for (PersonInfo info : list){
            if (!mInfos.contains(info)){    //并没有什么软用。
                tmp.add(info);
            }
        }
        mInfos.addAll(tmp);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void presentLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {
        mProgressBar.setVisibility(View.GONE);
    }


}

class ProposingStudentsAdapter extends SuperAdapter<PersonInfo>{

    public ProposingStudentsAdapter(Context context, List<PersonInfo> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, PersonInfo item) {
        ((TextView) holder.findViewById(R.id.frag_proposing_students_tv_student_name))
                .setText(item.getName());
    }
}
