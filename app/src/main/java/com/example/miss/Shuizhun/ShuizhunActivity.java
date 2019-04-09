package com.example.miss.Shuizhun;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.miss.Daoxian.DaoxianActivity;
import com.example.miss.R;
import com.example.miss.Xianlu.Xianlu_exampleActivity;
import com.example.miss.Xianlu.Xianlu_explainActivity;

public class ShuizhunActivity extends AppCompatActivity {
    private Toolbar toolbar;

    //region 说明菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shuizhun,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.shuizhun_item1){
            Intent intent = new Intent(ShuizhunActivity.this,Xianlu_explainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.shuizhun_item2){
            Intent intent = new Intent(ShuizhunActivity.this,Xianlu_exampleActivity.class);
            startActivity(intent);
        }
        return true;
    }
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuizhun);
        toolbar = (Toolbar)findViewById(R.id.toolbar_shuizhun);
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
    }
}
