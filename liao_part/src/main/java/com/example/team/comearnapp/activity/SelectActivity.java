package com.example.team.comearnapp.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.team.comearnapp.R;
import com.example.team.comearnapp.adpater.CharacterParser;
import com.example.team.comearnapp.adpater.CityAdapter;
import com.example.team.comearnapp.adpater.PinyinComparator;
import com.example.team.comearnapp.bean.City;
import com.example.team.comearnapp.ui.ClearEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
/**
 * 添加与删除活动
 * */
public class SelectActivity extends AppCompatActivity implements SideBar.OnTouchingLetterChangedListener, AdapterView.OnItemClickListener, TextWatcher {

    ClearEditText mClearEditText;

    StickyListHeadersListView mStickListHeadersListView;

    TextView mDialog;

    SideBar mSideBar;

    ProgressBar mProgressBar;

    QMUIRoundButton selectAll;

    QMUIRoundButton selectReverse;

    QMUIRoundButton confirm;

    QMUIRoundButton cancel;

    LinearLayout select_layout;


    private CityAdapter cityAdapter;
    //汉子转换成拼音的类
    private CharacterParser characterParser;
    //数据源
    private List<City> cities = new ArrayList<>();
    //根据拼音来排列listView里的数据类
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_select);
        ButterKnife.bind(this);

        mClearEditText = findViewById(R.id.clearEditText);

        mDialog = findViewById(R.id.dialog);

        mSideBar = findViewById(R.id.sideBar);

        mProgressBar = findViewById(R.id.progressBar);

        mStickListHeadersListView = findViewById(R.id.stickListHeadersListView);

        selectAll = findViewById(R.id.select_all);

        selectReverse = findViewById(R.id.select_reverse);

        select_layout = findViewById(R.id.select_layout);

        cancel = findViewById(R.id.cancel);

        confirm = findViewById(R.id.confirm);

        OnClickListenerCompat listener = new OnClickListenerCompat();

        selectAll.setOnClickListener(listener);

        selectReverse.setOnClickListener(listener);

        confirm.setOnClickListener(listener);

        cancel.setOnClickListener(listener);

        StatusBarUtil.setColor(SelectActivity.this, getResources().getColor(R.color.green), 50);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("1602019班");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        mSideBar.setTextView(mDialog);
        mSideBar.setOnTouchingLetterChangedListener(this);
        //mStickListHeadersListView的点击事件
        mStickListHeadersListView.setOnItemClickListener(this);
        cityAdapter = new CityAdapter(cities, SelectActivity.this);
        mStickListHeadersListView.setAdapter(cityAdapter);
        //输入框搜索过滤
        mClearEditText.addTextChangedListener(this);


        new MyAsyncTask(this).execute("city.json");
    }


    /**
     * 输入框过滤更新列表
     *
     * @param s
     */
    private void filterData(String s) {
        List<City> filterDataList = new ArrayList<>();
        if (TextUtils.isEmpty(s)) {
            filterDataList = cities;
        } else {
            filterDataList.clear();
            for (City sortModel : cities) {
                String name = sortModel.getCity();
                if (name.indexOf(s.toString()) != -1 || characterParser.getSelling(name).startsWith(s.toString())) {
                    filterDataList.add(sortModel);
                }
            }
        }

        //根据a~z进行排序
        Collections.sort(filterDataList, pinyinComparator);
        cityAdapter.updateList(filterDataList);
    }

    @Override
    public void onTouchingLetterChanged(String s) {
        //该字母首次出现的位置
        int position = cityAdapter.getPositionForSection(s.charAt(0));
        if (position != -1) {
            mStickListHeadersListView.setSelection(position);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        City city = ((City) cityAdapter.getItem(position));
//        CityAdapter.ViewHolder holder = (CityAdapter.ViewHolder) view.getTag();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
        filterData(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public class OnClickListenerCompat implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            onViewClicked(view);
        }
    }

    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.select_all) {
            cityAdapter.setSelectAll(cities);
            cityAdapter.notifyDataSetChanged();

        } else if (i == R.id.select_reverse) {
            cityAdapter.setSelectReverse(cities);
            cityAdapter.notifyDataSetChanged();

        } else if (i == R.id.confirm) {
            showDeleteDialog();

        } else if (i == R.id.cancel) {
            cityAdapter.setSelect(false);
            cityAdapter.updateList(cities);
            select_layout.setVisibility(View.INVISIBLE);

        }
    }

    class MyAsyncTask extends AsyncTask<Object, Integer, String> {
        private Context context;

        public MyAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Object... params) {
            /**
             * 获取城市数据
             */
            try {
                InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open((String) params[0]));
                BufferedReader bufReader = new BufferedReader(inputReader);
                String line = "";
                String result = "";
                while ((line = bufReader.readLine()) != null)
                    result += line;
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("")) {
                //JSON数据转List对象
                cities = new Gson().fromJson(s, new TypeToken<ArrayList<City>>() {
                }.getType());
                for (City city : cities) {
                    String pinyin = characterParser.getSelling(city.getCity());
                    String sortString = pinyin.substring(0, 1).toUpperCase();
                    city.setSortLetter(sortString);
                }
                //根据a-z 排序
                Collections.sort(cities, pinyinComparator);
                mProgressBar.setVisibility(View.GONE);
                cityAdapter.initIsSelected(cities);
                cityAdapter.updateList(cities);
            }
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

    //生成菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_icon, menu);
        return true;
    }

    private void showBottomSheet() {
        new QMUIBottomSheet.BottomListSheetBuilder(SelectActivity.this)
                .addItem("邀请好友")
                .addItem("设置管理员")
                .addItem("删除成员")
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        switch (position) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                select_layout.setVisibility(View.VISIBLE);
                                cityAdapter.setSelect(true);
                                cityAdapter.updateList(cities);
                                break;
                            default:
                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .build().show();
    }
    private void showDeleteDialog() {
        new QMUIDialog.MessageDialogBuilder(SelectActivity.this)
                .setTitle("删除好友")
                .setMessage("确定要删除吗？")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "删除", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        Toast.makeText(SelectActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

}
