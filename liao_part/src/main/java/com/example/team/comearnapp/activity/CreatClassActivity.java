package com.example.team.comearnapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.team.comearnapp.R;
import com.jaeger.library.StatusBarUtil;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * 创建群组页面
 * */
public class CreatClassActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton rb_Class;
    private RadioButton rb_ZiXi;
    private QMUIRoundButton creat_class_btn;
    private QMUIRoundButton face2face_btn;
    private MaterialEditText class_name;
    private MaterialEditText class_information;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_class);
        StatusBarUtil.setColor(CreatClassActivity.this, getResources().getColor(R.color.green), 50);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("创建群组");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        creat_class_btn=(QMUIRoundButton)findViewById(R.id.creat_class_btn);
        face2face_btn=(QMUIRoundButton)findViewById(R.id.face2face_btn);
        class_name=(MaterialEditText)findViewById(R.id.class_name_met) ;
        class_information=(MaterialEditText)findViewById(R.id.class_information_met) ;
        creat_class_btn.setOnClickListener(this);
        face2face_btn.setOnClickListener(this);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View var1) {
        if (var1.getId()==R.id.creat_class_btn) {

            if(class_name.getText().length()==0||class_information.getText().length()==0){
                Toast.makeText(getApplicationContext(), "请完善信息", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "创建成功", Toast.LENGTH_SHORT).show();
                finish();
            }

        }else if(var1.getId()==R.id.face2face_btn){

            if(class_name.getText().length()==0||class_information.getText().length()==0){
                Toast.makeText(getApplicationContext(), "请完善信息", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "开始面对面建群", Toast.LENGTH_SHORT).show();
                finish();
            }

        }


    }
}
