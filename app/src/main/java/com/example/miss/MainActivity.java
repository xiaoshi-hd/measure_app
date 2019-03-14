package com.example.miss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button_daoxian;//声明控件
    Button button_shuizhun;
    Button button_zuobiao;
    Button button_dadi;
    Button button_xianlu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_daoxian = (Button) findViewById(R.id.main_b_daoxian);//找到控件
        button_shuizhun = (Button) findViewById(R.id.main_b_shuizhun);
        button_zuobiao = (Button) findViewById(R.id.main_b_zuobiao);
        button_dadi = (Button) findViewById(R.id.main_b_dadi);
        button_xianlu = (Button) findViewById(R.id.main_b_xianlu);
        setListeners();
    }
    void  setListeners() {//为控件设置监听事件
        OnClick onClick = new OnClick();//实例化自己声明的类
        button_daoxian.setOnClickListener(onClick);

        button_xianlu.setOnClickListener(onClick);
    }
    //由于经常用到点击事件，为了减少重复代码，我们创建一个类来实现点击事件(因为监听事件传入的是一个类)
    private class OnClick implements View.OnClickListener{//这是一个在类里面定义的类，被称为内部类
        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (view.getId()){
                case R.id.main_b_daoxian:
                    intent = new Intent(MainActivity.this,DaoxianActivity.class);
                    break;

                case R.id.main_b_xianlu:
                    intent = new Intent(MainActivity.this,XianluActivity.class);
                    break;

            }
            startActivity(intent);
        }
    }
}
