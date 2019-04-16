package com.example.miss;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.miss.Dadi.DadiActivity;
import com.example.miss.Daoxian.DaoxianActivity;
import com.example.miss.Shuizhun.ShuizhunActivity;
import com.example.miss.Xianlu.XianluActivity;
import com.example.miss.Zuobiao.ZuobiaoActivity;
import com.example.miss.tools.dms_translateActivity;
import com.example.miss.tools.fangweijiaoActivity;
import com.example.miss.tools.zuobiaofanActivity;
import com.example.miss.tools.zuobiaozhengActivity;

import java.util.ArrayList;

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
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    //region右侧菜单
    public boolean onCreateOptionsMenu(Menu menu) {//创建菜单
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {//菜单选择事件
        if (item.getItemId() == R.id.main_item){
            Intent intent = new Intent(MainActivity.this,Main_exaplainActivity.class);
            startActivity(intent);
        }
        return true;
    }
    //endregion
    //region左侧菜单
    private class DrawerItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            selectItem(position);
        }
    }
    private void selectItem (int position){//传入标签位置
        if (position == 0){
            Toast.makeText(MainActivity.this, "你选择了度分秒转换", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,dms_translateActivity.class);
            startActivity(intent);
        }
        else if (position == 1){
            Toast.makeText(MainActivity.this, "你选择了方位角计算", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,fangweijiaoActivity.class);
            startActivity(intent);
        }
        else if (position == 2){
            Toast.makeText(MainActivity.this, "你选择了坐标正算", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,zuobiaozhengActivity.class);
            startActivity(intent);
        }
        else if (position == 3){
            Toast.makeText(MainActivity.this, "你选择了坐标反算", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,zuobiaofanActivity.class);
            startActivity(intent);
        }
    }
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//使用activity_main布局

        //region左侧菜单
        //region左侧菜单显示出来
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.activity_drawerlayout) ;
        listView = (ListView)findViewById(R.id.left_listview);

        menuList = new ArrayList<String>();
        menuList.add("度分秒转化");
        menuList.add("方位角计算");
        menuList.add("坐标正算");
        menuList.add("坐标反算");
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,menuList);
        listView.setAdapter(adapter);
        //endregion
        //region左侧菜单设置监听事件
        listView.setOnItemClickListener(new DrawerItemClickListener());
        //endregion
        //region左侧抽屉和图标联系在一起
        toolbar.setTitle("测量程序");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用

        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle("测量工具箱");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                toolbar.setTitle("测量程序");
            }
        };
        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);
        //endregion
        //endregion

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
