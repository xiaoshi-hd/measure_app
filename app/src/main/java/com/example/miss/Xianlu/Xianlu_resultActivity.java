package com.example.miss.Xianlu;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.miss.R;

public class Xianlu_resultActivity extends AppCompatActivity {
    private Toolbar toolbar;
    TextView mtextView1;
    TextView mtextView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xianlu_result);
        mtextView1 = (TextView) findViewById(R.id.xianlu2_t1);
        mtextView1.setText(String.valueOf(XianluActivity.str));
        mtextView2 = (TextView) findViewById(R.id.xianlu2_t2);
        mtextView2.setText(String.valueOf(XianluActivity.str1));

        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_xianlu_result);
        toolbar.setTitle("线路曲线计算");//设置Toolbar标题
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

