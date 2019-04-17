package com.example.miss.Shuizhun;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.miss.R;

import java.util.ArrayList;

public class Shuizhun_resultActivity extends AppCompatActivity {
    private Toolbar toolbar;
    //存储距离
    public static ArrayList<Double> juli = new ArrayList<>();
    //存储高差
    public static ArrayList<Double> gaocha = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuizhun_result);

        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_shuizhun_result);
        toolbar.setTitle("水准路线计算");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //endregion

    }
}
