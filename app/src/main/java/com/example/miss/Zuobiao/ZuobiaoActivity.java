package com.example.miss.Zuobiao;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.Dadi.DadiActivity;
import com.example.miss.R;
import com.example.miss.Xianlu.Xianlu_exampleActivity;
import com.example.miss.Xianlu.Xianlu_explainActivity;

public class ZuobiaoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RadioButton rd_Krassovsky,rd_ICGG_1975,rd_WGS_84,rd_CGCS2000;
    private EditText ed11,ed12,ed21,ed22,ed31,ed32;
    private TextView t1,t2;
    private Button button;
    private double a, b, c, f, e1, e2;//基本椭球参数
    //region 说明菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.zuobiao,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.zuobiao_item1){
            Intent intent = new Intent(ZuobiaoActivity.this,Zuobiao_explainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.zuobiao_item2){
            Intent intent = new Intent(ZuobiaoActivity.this,Zuobiao_exampleActivity.class);
            startActivity(intent);
        }
        return true;
    }
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuobiao);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        //region 找到控件
        rd_Krassovsky = (RadioButton) findViewById(R.id.zuobiao_Krassovsky);
        rd_ICGG_1975 = (RadioButton) findViewById(R.id.zuobiao_ICGG1975);
        rd_WGS_84 = (RadioButton) findViewById(R.id.zuobiao_WGS_84);
        rd_CGCS2000 = (RadioButton) findViewById(R.id.zuobiao_CGCS2000);

        ed11 = (EditText) findViewById(R.id.zuobiao_ed11);
        ed12 = (EditText) findViewById(R.id.zuobiao_ed12);
        ed21 = (EditText) findViewById(R.id.zuobiao_ed21);
        ed22 = (EditText) findViewById(R.id.zuobiao_ed22);
        ed31 = (EditText) findViewById(R.id.zuobiao_ed31);
        ed32 = (EditText) findViewById(R.id.zuobiao_ed32);

        t1 = (TextView)findViewById(R.id.zuobiao_t1) ;
        t2 = (TextView)findViewById(R.id.zuobiao_t2) ;

        button = (Button)findViewById(R.id.dadi_button);
        //endregion

        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_zuobiao);
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

        //region 下拉菜单
        Resources res =getResources();
        String[] choose = res.getStringArray(R.array.translate);//将translate中内容添加到数组choose中
        final Spinner spinner = (Spinner) findViewById(R.id.spacer);//获取到spacer
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,choose);//创建Arrayadapter适配器
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//通过此方法为下拉列表设置点击事件
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text = spinner.getItemAtPosition(i).toString();
                //Toast.makeText(ZuobiaoActivity.this,"你选择了" + text,Toast.LENGTH_SHORT).show();
                //Log.d("Activity生命周期", String.valueOf(i));
                if (i == 0){
                    t1.setText("大地坐标");
                    t2.setText("空间直角坐标");
                    ed11.setHint("B(dms)");
                    ed21.setHint("H(dms)");
                    ed31.setHint("L(m)");
                    ed12.setHint("X(m)");
                    ed22.setHint("Y(m)");
                    ed32.setHint("Z(m)");
                }
                else if (i == 1){
                    t1.setText("空间直角坐标");
                    t2.setText("大地坐标");
                    ed11.setHint("X(m)");
                    ed21.setHint("Y(m)");
                    ed31.setHint("Z(m)");
                    ed12.setHint("B(dms)");
                    ed22.setHint("H(dms)");
                    ed32.setHint("L(m)");
                }
                else if (i == 2){
                    t1.setText("大地坐标");
                    t2.setText("高斯平面坐标");
                    ed11.setHint("B(dms)");
                    ed21.setHint("H(dms)");
                    ed31.setHint("L(m)");
                    ed12.setHint("x(m)");
                    ed22.setHint("y(m)");
                    ed32.setHint("H(m)");
                }
                else if (i == 3){
                    t1.setText("高斯平面坐标");
                    t2.setText("大地坐标");
                    ed11.setHint("x(m)");
                    ed21.setHint("y(m)");
                    ed31.setHint("H(m)");
                    ed12.setHint("B(dms)");
                    ed22.setHint("H(dms)");
                    ed32.setHint("L(m)");
                }
                else if (i == 4){

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //endregion
    }
}
