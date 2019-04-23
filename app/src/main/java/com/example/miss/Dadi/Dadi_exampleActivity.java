package com.example.miss.Dadi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.miss.R;

public class Dadi_exampleActivity extends AppCompatActivity {
    private Toolbar toolbar;/////
    public static boolean example_zheng = false,example_fan = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dadi_example);

        Button button_zheng = (Button) findViewById(R.id.dadi_example_button_zheng);
        button_zheng.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                example_zheng = true;
                finish();
            }
        });
        Button button_fan = (Button) findViewById(R.id.dadi_example_button_fan);
        button_fan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                example_fan = true;
                finish();
            }
        });

        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_dadi_example);
        toolbar.setTitle("大地主题解算");//设置Toolbar标题
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
