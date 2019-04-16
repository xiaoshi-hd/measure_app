package com.example.miss.Shuizhun;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.miss.Caculate;
import com.example.miss.Daoxian.DaoxianActivity;
import com.example.miss.R;
import com.example.miss.Xianlu.Xianlu_exampleActivity;
import com.example.miss.Xianlu.Xianlu_explainActivity;

public class ShuizhunActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button;
    private EditText hou_black_shang,hou_black_zhong,hou_black_xia,hou_red_zhong,
            qian_black_shang,qian_black_xia,qian_black_zhong,qian_red_zhong;
    private EditText editText_station,editText_K_hou,editText_K_qian;
    private TextView lastStation,leijicha;
    private RadioButton rd_2,rd_4;

    private String station;
    private double k1,k2;
    private double hhs,hhz,hhx;//后黑上，中，下
    private double qhs,qhz,qhx;//前黑上，中，下
    private double hhongz,qhongz;//后红中，前红中

    //region 说明菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shuizhun,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.shuizhun_item1){
            Intent intent1 = new Intent(ShuizhunActivity.this,Shuizhun_explainActivity.class);
            startActivity(intent1);
        }
        return true;
    }
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuizhun);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        //region 找到控件
        hou_black_shang = (EditText)findViewById(R.id.shuizhun_hou_black_shang);
        hou_black_zhong = (EditText)findViewById(R.id.shuizhun_hou_black_zhong);
        hou_black_xia = (EditText)findViewById(R.id.shuizhun_hou_black_xia);
        hou_red_zhong = (EditText)findViewById(R.id.shuizhun_hou_red_zhong);
        qian_black_shang = (EditText)findViewById(R.id.shuizhun_qian_black_shang);
        qian_black_xia = (EditText)findViewById(R.id.shuizhun_qian_black_xia);
        qian_black_zhong = (EditText)findViewById(R.id.shuizhun_qian_black_zhong);
        qian_red_zhong = (EditText)findViewById(R.id.shuizhun_qian_red_zhong);
        editText_station = (EditText)findViewById(R.id.shuizhun_station);
        editText_K_hou = (EditText)findViewById(R.id.shuizhun_K_hou);
        editText_K_qian = (EditText)findViewById(R.id.shuizhun_K_qian);
        lastStation = (TextView)findViewById(R.id.shuizhun_lastStation);
        leijicha = (TextView)findViewById(R.id.shuizhun_leijicha);
        rd_2 = (RadioButton)findViewById(R.id.shuizhun_2deng);
        rd_4 = (RadioButton)findViewById(R.id.shuizhun_4deng);
        //endregion

        //region 标题栏
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
        //endregion

        button = (Button) findViewById(R.id.shuizhun_jisuan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //region 传入数据
                //hhs = Double.parseDouble(hou_black_shang.getText().toString());
                //hou_black_xia.setText(String.valueOf(Caculate.Round(hhs,6)));
                try{
                    station = (editText_station.getText().toString());
                    k1 = Double.parseDouble(editText_K_qian.getText().toString());
                    k2 = Double.parseDouble(editText_K_hou.getText().toString());
                    hhs = Double.parseDouble(hou_black_shang.getText().toString());
                    hhx = Double.parseDouble(hou_black_xia.getText().toString());
                    hhz = Double.parseDouble(hou_black_zhong.getText().toString());
                    qhz = Double.parseDouble(qian_black_zhong.getText().toString());
                    qhs = Double.parseDouble(qian_black_shang.getText().toString());
                    qhx = Double.parseDouble(qian_black_xia.getText().toString());
                    qhongz = Double.parseDouble(qian_red_zhong.getText().toString());
                    hhongz = Double.parseDouble(hou_red_zhong.getText().toString());
                }
                catch (Exception e){
                    throw e;
                }

                //endregion

                Intent intent = new Intent(ShuizhunActivity.this,Shuizhun_stationActivity.class);
                startActivity(intent);
            }
        });
    }
}
