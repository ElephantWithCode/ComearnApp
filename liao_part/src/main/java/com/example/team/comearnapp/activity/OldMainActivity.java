//package com.example.team.comearnapp.activity;
//
//import android.app.ActivityOptions;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
//import android.transition.Explode;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.team.comearnapp.R;
//import com.liaoinstan.springview.container.AliHeader;
//import com.liaoinstan.springview.widget.SpringView;
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    private SpringView springView;
//    private TextView iv_good_detai_img;
//    private TextView tv_class_mode;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_main_old);
//        initView();
//        getWindow().setEnterTransition(new Explode().setDuration(1000));
////        getWindow().setExitTransition(new Explode().setDuration(500));
//
//
//        springView=(SpringView) findViewById(R.id.springview);
//        springView.setListener(new SpringView.OnFreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(MainActivity.this,"课堂模式",Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(MainActivity.this, MainActivity.class),
//                                ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
//                        springView.onFinishFreshAndLoad();
//                    }
//                }, 200);
//            }
//
//            @Override
//            public void onLoadmore() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        springView.onFinishFreshAndLoad();
//                    }
//                }, 200);
//            }
//        });
//        springView.setHeader(new AliHeader(this){
//            @Override
//            public int getDragSpringHeight(View rootView) {
//                return super.getDragSpringHeight(rootView);
//            }
//        });   //参数为：logo图片资源，是否显示文字
//        springView.setHeader(new AliHeader(this,  true));   //参数为：logo图片资源，是否显示文字
//        springView.setFooter(new AliHeader(this, R.drawable.act_main_ali, false));   //参数为：logo图片资源，是否显示文字
//        springView.setType(SpringView.Type.OVERLAP);
//    }
//
//    private void initView() {
//        iv_good_detai_img = (TextView) findViewById(R.id.iv_good_detai_img);
//        tv_class_mode = (TextView) findViewById(R.id.tv_class_mode);
//        iv_good_detai_img.setOnClickListener(this);
//        tv_class_mode.setOnClickListener(this);
//
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_good_detai_img:
//                break;
//            case R.id.tv_class_mode:
//                break;
//        }
//    }
//}
