package com.example.team.comearnapp.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.bean.Class;
import com.example.team.comearnapp.tool.RecyclerViewCommonTool.CommonAdapter;
import com.example.team.comearnapp.tool.RecyclerViewCommonTool.ViewHolder;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.ArrayList;

public class MoreClassActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    private CommonAdapter adapter;
    /**
     * 群助手页面
     * 是课堂群组（ClassDetailActivity）的入口
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_more_class);
        StatusBarUtil.setColor(MoreClassActivity.this, getResources().getColor(R.color.green), 50);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("群助手");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.act_more_class_rv);
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MoreClassActivity.this));
        DividerItemDecoration mDecoration = new DividerItemDecoration(MoreClassActivity.this, DividerItemDecoration.VERTICAL);
        mDecoration.setDrawable(ContextCompat.getDrawable(MoreClassActivity.this, R.drawable.divider_bg));
        mRecyclerView.setHasFixedSize(true);
        ArrayList<Class> categoryChapters=new ArrayList<Class>();
        categoryChapters.add(new Class("16级通信1班","学习本无底，前进莫徬徨。",R.drawable.books,1,true));
        categoryChapters.add(new Class("17级计科3班","我们一定要给自己提出这样的任务：第一，学习，第二是学习，第三还是学习。 —— 列宁\n",R.drawable.books,1,false));
        categoryChapters.add(new Class("15级经管1班","现在，我怕的并不是那艰苦严峻的生活，而是不能再学习和认识我迫切想了解的世界。对我来说，不学习，毋宁死",R.drawable.books,1,false));

        adapter=new CommonAdapter<Class>(MoreClassActivity.this, R.layout.act_main_class_mode_item, categoryChapters){
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, int position) {
                super.onBindViewHolder(viewHolder, position);
            }

            @Override
            public void convert(ViewHolder holder, Class mClass) {
                TextView class_name_tv = holder.getView(R.id.act_class_mode_rv_class_name_tv);
                TextView class_brief_tv = holder.getView(R.id.act_class_mode_rv_class_brief_tv);
                ImageView class_header_iv = holder.getView(R.id.act_class_mode_rv_class_header_iv);
                class_name_tv.setText(mClass.getmClassName());
                class_brief_tv.setText(mClass.getmClassBrief());
                class_header_iv.setImageDrawable(getResources().getDrawable(mClass.getmClassHeader()));
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MoreClassActivity.this,ClassDetailActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(adapter);
        
    }


    //生成菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            finish();

        } else if (i == R.id.about_icon) {
            showMessageNegativeDialog();

        }
        return super.onOptionsItemSelected(item);
    }

    private void showMessageNegativeDialog() {
        new QMUIDialog.MessageDialogBuilder(this)
                .setMessage("未被设置为特别关注的群组会被收入进群助手")
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
