package com.example.miss;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.Dadi.DadiActivity;
import com.example.miss.Daoxian.DaoxianActivity;
import com.example.miss.Shuizhun.ShuizhunActivity;
import com.example.miss.Xianlu.XianluActivity;
import com.example.miss.Zuobiao.ZuobiaoActivity;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    Button button_daoxian;//声明控件
    Button button_shuizhun;
    Button button_zuobiao;
    Button button_dadi;
    Button button_xianlu;

    private DrawerLayout drawerLayout;//声明左侧菜单
    private ListView listView;
    private ArrayList<String>menuList;
    private ArrayAdapter<String>adapter;
    //region左侧菜单

    //endregion
    //region右侧菜单
    public boolean onCreateOptionsMenu(Menu menu) {//创建菜单
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {//菜单选择事件
        if (item.getItemId() == R.id.main_item){
            Intent intent = new Intent(MainActivity.this,Main_exampleActivity.class);
            startActivity(intent);
        }
        return true;
    }
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//使用activity_main布局

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
        button_shuizhun.setOnClickListener(onClick);
        button_zuobiao.setOnClickListener(onClick);
        button_dadi.setOnClickListener(onClick);
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
                case R.id.main_b_shuizhun:
                    intent = new Intent(MainActivity.this, ShuizhunActivity.class);
                    break;
                case R.id.main_b_zuobiao:
                    intent = new Intent(MainActivity.this,ZuobiaoActivity.class);
                    break;
                case R.id.main_b_dadi:
                    intent = new Intent(MainActivity.this,DadiActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
