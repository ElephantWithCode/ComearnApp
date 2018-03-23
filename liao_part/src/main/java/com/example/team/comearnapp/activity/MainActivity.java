package com.example.team.comearnapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.team.comearnapp.R;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import butterknife.ButterKnife;
public class MainActivity extends AppCompatActivity {
    /**
     * 主页面
     * 包含两个Fragment：MainRecycleViewFragment
     * 此活动包含侧滑栏（给汪工）
     * 此活动的菜单可跳转到SearchActivity（加群组好友）和创建群组（待完成）
     * */


    MaterialViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        setTitle("");
        ButterKnife.bind(this);

        mViewPager=(MaterialViewPager)findViewById(R.id.materialViewPager);
        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.drawer);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO：汪工在这里实现侧滑栏
                    Toast.makeText(getApplicationContext(), "我是侧滑栏，啦啦啦！", Toast.LENGTH_SHORT).show();
                }
            });
        }

        

        mViewPager=(MaterialViewPager)findViewById(R.id.materialViewPager);



        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    //case 0:
                    //    return MainRecyclerViewFragment.newInstance();
                    default:
                        return MainRecyclerViewFragment.newInstance();
                }
            }
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "课堂模式";
                    case 1:
                        return "自习模式";
                }
                return "";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "http://img.bizhi.sogou.com/images/2013/05/15/338856.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.green,
                                "http://www.xidian.edu.cn/_mediafile/xadzkjdx2/2015/05/29/2lvaaswucd.jpg");
                }

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        final View logo = findViewById(R.id.logo_white);
        if (logo != null) {
            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.notifyHeaderChanged();
                    Toast.makeText(getApplicationContext(), "骚年，学习不？", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //生成菜单
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_icon, menu);
        return true;
    }

    //标题栏按钮点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.add_icon) {
            showBottomSheet();

        }
        return super.onOptionsItemSelected(item);
    }

    //＋按钮
    private void showBottomSheet() {
        new QMUIBottomSheet.BottomListSheetBuilder(MainActivity.this)
                .addItem("创建群组")
                .addItem("加好友/群组")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                Intent intent=new Intent(MainActivity.this,SearchActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .build().show();
    }

}




