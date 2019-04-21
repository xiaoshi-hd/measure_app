package com.example.miss.Zuobiao;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.miss.R;

public class Zuobiao_exampleActivity extends AppCompatActivity {
    private Toolbar toolbar;
    public static boolean example_dadi_zhijiao = false,example_zhijiao_dadi = false;
    public static boolean example_dadi_gaosi = false,example_gaosi_dadi = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuobiao_example);

        Button button_dadi_zhijiao = (Button) findViewById(R.id.zuobiao_example_text_dadi_zhijiao);
        button_dadi_zhijiao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                example_dadi_zhijiao = true;
                finish();
            }
        });
        Button button_zhijiao_dadi = (Button) findViewById(R.id.zuobiao_example_text_zhijiao_dadi);
        button_zhijiao_dadi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                example_zhijiao_dadi = true;
                finish();
            }
        });Button button_dadi_gaosi = (Button) findViewById(R.id.zuobiao_example_text_dadi_gaosi);
        button_dadi_gaosi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                example_dadi_gaosi = true;
                finish();
            }
        });
        Button button_gaosi_dadi = (Button) findViewById(R.id.zuobiao_example_text_gaosi_dadi);
        button_gaosi_dadi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                example_gaosi_dadi = true;
                finish();
            }
        });

        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_zuobiao_example);
        toolbar.setTitle("坐标转换");//设置Toolbar标题
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
