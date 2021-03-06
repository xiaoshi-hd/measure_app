package com.example.miss.Shuizhun;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.Caculate;
import com.example.miss.Daoxian.DaoxianActivity;
import com.example.miss.R;
import com.example.miss.Xianlu.Xianlu_exampleActivity;
import com.example.miss.Xianlu.Xianlu_explainActivity;

public class ShuizhunActivity extends AppCompatActivity {

    //region 定义控件变量
    private Toolbar toolbar;
    private Button button;

    private EditText hou_black_shang,hou_black_zhong,hou_black_xia,hou_red_zhong,
            qian_black_shang,qian_black_xia,qian_black_zhong,qian_red_zhong;
    private EditText editText_station,editText_K_hou,editText_K_qian;

    private TextView lastStation,leijicha_text;
    //endregion

    //region 定义计算变量
    private String cezhan;
    private double k1,k2;//后尺，前尺
    private double hhs,hhz,hhx;//后黑上，中，下
    private double qhs,qhz,qhx;//前黑上，中，下
    private double hhongz,qhongz;//后红中，前红中

    private double qianshiju,houshiju,shicha,leijicha;//视距
    private double hou_black_red,qian_black_red,houjianqian,heijianhong;//高差之差
    private double gaocha_black,gaocha_red,gaocha_avg;//高差
    //endregion

    protected void onResume() {
        super.onResume();
        Log.i("Activity生命周期","水准的OnResume方法调用");

        lastStation.setText("上一测站:    "+ Shuizhun_stationActivity.cezhan);
        leijicha_text.setText("后前视距累积差:   "+ Shuizhun_stationActivity.leijishiju);
        if (Shuizhun_stationActivity.isnext){
            //region 清空控件
            hou_black_shang.setText("");
            hou_black_zhong.setText("");
            hou_black_xia.setText("");
            hou_black_shang.setText("");
            qian_black_zhong.setText("");
            qian_black_xia.setText("");
            qian_black_shang.setText("");
            hou_red_zhong.setText("");
            qian_red_zhong.setText("");
            editText_station.setText("");
            editText_K_qian.setText("");
            editText_K_hou.setText("");
            //endregion
        }

        if (Shuizhun_setupActivity.dengji == 2){
            editText_K_qian.setText("301.550");
            editText_K_hou.setText("301.550");
            editText_K_qian.setFocusable(false);//设置结果不可编辑，但是能响应点击事件
            editText_K_hou.setFocusable(false);
        }
    }
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
        leijicha_text = (TextView)findViewById(R.id.shuizhun_leijicha);
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

        if (Shuizhun_setupActivity.dengji == 2){
            editText_K_qian.setText("301.550");
            editText_K_hou.setText("301.550");
            editText_K_qian.setFocusable(false);//设置结果不可编辑，但是能响应点击事件
            editText_K_hou.setFocusable(false);
        }

        button = (Button) findViewById(R.id.shuizhun_jisuan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //region 传入数据
                try{
                    cezhan = editText_station.getText().toString();
                    k1 = Double.parseDouble(editText_K_hou.getText().toString());
                    k2 = Double.parseDouble(editText_K_qian.getText().toString());
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
                    Toast.makeText(ShuizhunActivity.this, "请正确填写全部数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //endregion
                //region 测站检核计算
                //region 二等
                if (Shuizhun_setupActivity.dengji == 2) {
                    houshiju = Caculate.Round(hhs - hhx,2);//计算出来的视距需要乘以100再化成米
                    qianshiju = Caculate.Round(qhs - qhx,2);
                    shicha = Caculate.Round(houshiju - qianshiju,2);
                    leijicha = shicha + Shuizhun_stationActivity.leijishiju;

                    hou_black_red = Caculate.Round((hhz + k1 - hhongz) * 10,2);//后尺差以mm为单位
                    qian_black_red = Caculate.Round((qhz + k2 - qhongz) * 10,2);
                    houjianqian = Caculate.Round(hou_black_red - qian_black_red,2);

                    gaocha_black = Caculate.Round(hhz - qhz,3);//高程以cm为单位
                    gaocha_red = Caculate.Round(hhongz - qhongz,3);
                    heijianhong = Caculate.Round((gaocha_black - gaocha_red) * 10,2);

                    gaocha_avg = Caculate.Round((gaocha_black + gaocha_red) / 2,4);
                }
                //endregion
                //region 四等
                if (Shuizhun_setupActivity.dengji == 4){
                    houshiju = Caculate.Round((hhs - hhx) / 10,1);//计算出来的视距需要乘以100再化成米
                    qianshiju = Caculate.Round((qhs - qhx) / 10,1);
                    shicha = Caculate.Round(houshiju - qianshiju,1);
                    leijicha = shicha + Shuizhun_stationActivity.leijishiju;

                    hou_black_red = Caculate.Round((hhz + k1 - hhongz),0);
                    qian_black_red = Caculate.Round((qhz + k2 - qhongz),0);
                    houjianqian = Caculate.Round(hou_black_red - qian_black_red,0);

                    gaocha_black = Caculate.Round((hhz - qhz) / 1000,3);//高程以m为单位
                    gaocha_red = Caculate.Round((hhongz - qhongz) / 1000,3);

                    // 由于后视尺和前视线尺的常数不同，差值应为0.1m
                    if(Math.abs(gaocha_red) > Math.abs(gaocha_black)){
                        if(gaocha_red > 0)
                            heijianhong = gaocha_black - (gaocha_red - 0.1);
                        else
                            heijianhong = gaocha_black - (gaocha_red + 0.1);
                    }
                    else{
                        if(gaocha_red > 0)
                            heijianhong = gaocha_black - (gaocha_red + 0.1);
                        else
                            heijianhong = gaocha_black - (gaocha_red - 0.1);
                    }
                    heijianhong = Caculate.Round(heijianhong * 1000,0);
                    gaocha_avg = Caculate.Round((gaocha_black - houjianqian / 1000 / 2),4);
                }
                //endregion
                //endregion

                //region 数据存储
                Shuizhun_resultActivity.juli.add(Caculate.Round(houshiju + qianshiju,1));
                Shuizhun_resultActivity.gaocha.add(gaocha_avg);
                Shuizhun_resultActivity.cezhan.add(cezhan);
                //endregion
                Intent intent = new Intent(ShuizhunActivity.this,Shuizhun_stationActivity.class);
                intent.putExtra("测站",cezhan);

                intent.putExtra("前视距",qianshiju);
                intent.putExtra("后视距",houshiju);
                intent.putExtra("当前视差",shicha);
                intent.putExtra("累积视差",leijicha);

                intent.putExtra("前黑红读差",qian_black_red);
                intent.putExtra("后黑红读差",hou_black_red);
                intent.putExtra("后减前",houjianqian);

                intent.putExtra("黑面高差",gaocha_black);
                intent.putExtra("红面高差",gaocha_red);
                intent.putExtra("黑减红",heijianhong);

                intent.putExtra("平均高差",gaocha_avg);

                //用于数据合格后把数据传入数据库
                intent.putExtra("K1",k1);
                intent.putExtra("K2",k2);
                intent.putExtra("hhs",hhs);
                intent.putExtra("hhz",hhz);
                intent.putExtra("hhx",hhx);
                intent.putExtra("qhs",qhs);
                intent.putExtra("qhz",qhz);
                intent.putExtra("qhx",qhx);
                intent.putExtra("qhongz",qhongz);
                intent.putExtra("hhongz",hhongz);

                startActivity(intent);//每次都会触发station的oncreate事件
            }
        });
    }
}
