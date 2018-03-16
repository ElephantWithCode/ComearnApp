package com.example.team.comearnapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team.comearnapp.R;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
 * 已加入的群组信息页面
 * 包含；两个Fragment：综合（ClassDetailComFragment）和信息（ClassDetailInformationFragment）
 *  是群主发起自习活动的入口
 *  是群组更改群名、群公告、群信息、设置白名单、查看验证消息的入口
 * */

public class ClassDetailActivity extends AppCompatActivity {
    TextView notice_tv;
    ViewPager viewPager;
    private boolean optionMenuOn = false;  //标示是否要显示optionmenu
    private Menu aMenu;         //获取optionmenu
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_class_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("演示班级");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);


        //remove
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);
        collapsingToolbar.setTitleEnabled(false);

        //set view pager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new ClassDetailComFragment(), "综合");
        adapter.addFrag(new ClassDetailInformationFragment(), "信息");


        viewPager.setAdapter(adapter);




        // set tab layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
//            bmb.addBuilder(builder);
//        }
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            finish();

        } else if (i == R.id.edit_icon) {
            showBottomSheet();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        aMenu = menu;
        //aMenu.getItem(0).setVisible(false);管理员权限
        return super.onPrepareOptionsMenu(menu);
    }

    private void checkAdmin(){

    }

    //生成菜单
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_icon, menu);
        return true;
    }

    private void showBottomSheet() {
        new QMUIBottomSheet.BottomListSheetBuilder(ClassDetailActivity.this)
                .addItem("更改群名")
                .addItem("更改群公告")
                .addItem("更改群信息")
                .addItem("设置白名单")
                .addItem("查看验证消息")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        switch (position) {
                            case 0:
                                showEditClassNameDialog();
                                break;
                            case 1:
                                showEditNoticeDialog();
                                break;
                            case 2:
                                showEditInformationDialog();
                                break;
                            case 3:
                                //TODO:汪工在这里跳转到设置白名单页面
                                startActivity(new Intent(ClassDetailActivity.this, ClassSettingActivity.class));
                                break;
                            case 4:
                                break;
                            default:
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .build().show();
    }


    private void showEditNoticeDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(ClassDetailActivity.this);
        builder.setTitle("修改群公告")
                .setPlaceholder("在此输入新的群公告")
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
                            Toast.makeText(ClassDetailActivity.this, "新的群公告已设置" , Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            ViewPagerAdapter madpater=(ViewPagerAdapter)viewPager.getAdapter();
                            TextView textView=(TextView) madpater.getItem(0).getView().findViewById(R.id.notice_tv);
                            textView.setText(text);
                        } else {
                            Toast.makeText(ClassDetailActivity.this, "群公告不应为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }
    private void showEditInformationDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(ClassDetailActivity.this);
        builder.setTitle("修改群信息")
                .setPlaceholder("在此输入新的群信息")
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
                            Toast.makeText(ClassDetailActivity.this, "新的群信息已设置" , Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            ViewPagerAdapter madpater=(ViewPagerAdapter)viewPager.getAdapter();
                            TextView textView=(TextView) madpater.getItem(1).getView().findViewById(R.id.class_information_tv);
                            textView.setText(text);
                        } else {
                            Toast.makeText(ClassDetailActivity.this, "群信息不应为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }
    private void showEditClassNameDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(ClassDetailActivity.this);
        builder.setTitle("修改群组名")
                .setPlaceholder("在此输入新的群组名")
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
                            Toast.makeText(ClassDetailActivity.this, "新的群组名已设置" , Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            ViewPagerAdapter madpater=(ViewPagerAdapter)viewPager.getAdapter();
                            TextView textView=(TextView) madpater.getItem(1).getView().findViewById(R.id.class_name_tv);
                            textView.setText(text);
                            getSupportActionBar().setTitle(text);
                        } else {
                            Toast.makeText(ClassDetailActivity.this, "群组名不应为空", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
    }

}
