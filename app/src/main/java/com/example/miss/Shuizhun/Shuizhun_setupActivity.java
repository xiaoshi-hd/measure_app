package com.example.miss.Shuizhun;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.miss.R;

public class Shuizhun_setupActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button;
    private RadioButton rd_2,rd_4;
    private EditText start,end;

    //存储等级信息
    public static int dengji;
    //存储起终点高程信息
    public static double gaocheng_start,gaocheng_end;
    //region 说明菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shuizhun,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.shuizhun_item1){
            Intent intent1 = new Intent(Shuizhun_setupActivity.this,Shuizhun_explainActivity.class);
            startActivity(intent1);
        }
        return true;
    }
    //endregion
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Activity生命周期","setup 的 OnResume方法调用");

        Shuizhun_resultActivity.juli.clear();
        Shuizhun_resultActivity.gaocha.clear();
        Shuizhun_resultActivity.cezhan.clear();

        Shuizhun_stationActivity.leijishiju = 0;
        Shuizhun_stationActivity.cezhan = "";
        Shuizhun_stationActivity.isnext = false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuizhun_setup);

        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_shuizhun_setup);
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

        Shuizhun_resultActivity.juli.clear();
        Shuizhun_resultActivity.gaocha.clear();
        Shuizhun_resultActivity.cezhan.clear();

        Shuizhun_stationActivity.leijishiju = 0;
        Shuizhun_stationActivity.cezhan = "";
        Shuizhun_stationActivity.isnext = false;

        button = (Button) findViewById(R.id.shuizhun_start);
        rd_2 = (RadioButton)findViewById(R.id.shuizhun_2deng);
        rd_4 = (RadioButton)findViewById(R.id.shuizhun_4deng);
        start = (EditText) findViewById(R.id.shuizhun_gaocheng_start);
        end = (EditText) findViewById(R.id.shuizhun_gaocheng_end);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (rd_2.isChecked()){
                        dengji = 2;
                    }
                    if (rd_4.isChecked()){
                        dengji = 4;
                    }
                    gaocheng_start = Double.parseDouble(start.getText().toString());
                    gaocheng_end = Double.parseDouble(end.getText().toString());
                }
                catch (Exception e){
                    Toast.makeText(Shuizhun_setupActivity.this, "请正确填写全部数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Shuizhun_setupActivity.this,ShuizhunActivity.class);
                startActivity(intent);
            }
        });
    }
}
