package com.example.miss.Shuizhun;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.Caculate;
import com.example.miss.R;

public class Shuizhun_stationActivity extends AppCompatActivity {

    //region 定义变量
    private Toolbar toolbar;
    private Button button_back,button_next,button_pingcha;
    private TextView station;
    private TextView qianshiju,houshiju,shicha,leijicha_result;//视距
    private TextView hou_black_red,qian_black_red,houjianqian,heijianhong;//高差之差
    private TextView gaocha_black,gaocha_red,gaocha_avg;//高差
    private TextView mhou_black_red,mqian_black_red,mheijianhong;//高差之差前的标题
    private TextView mgaocha_black,mgaocha_red;//高差前的标题
    public static String cezhan;
    public static double leijishiju;
    public static boolean isnext;
    //endregion

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Activity生命周期","station 的 OnResume方法调用");

        if (Shuizhun_setupActivity.dengji == 2){

            mhou_black_red.setText("后基辅差(mm)");
            mqian_black_red.setText("前基辅差(mm)");
            mgaocha_black.setText("基础高差(cm)");
            mgaocha_red.setText("辅助高差(cm)");
            mheijianhong.setText("基减辅(mm)");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuizhun_station);

        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_shuizhun_station);
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

        //region 找到控件
        station = (TextView)findViewById(R.id.shuizhun_station_result);

        qianshiju = (TextView)findViewById(R.id.shuizhun_qianshiju);
        houshiju = (TextView)findViewById(R.id.shuizhun_houshiju);
        shicha = (TextView)findViewById(R.id.shuizhun_shicha);
        leijicha_result = (TextView)findViewById(R.id.shuizhun_leijicha_result);

        hou_black_red = (TextView)findViewById(R.id.shuizhun_hou_black_red);
        qian_black_red = (TextView)findViewById(R.id.shuizhun_qian_black_red);
        houjianqian = (TextView)findViewById(R.id.shuizhun_gaocha1);

        gaocha_black = (TextView)findViewById(R.id.shuizhun_black_gaocha);
        gaocha_red = (TextView)findViewById(R.id.shuizhun_red_gaocha);
        heijianhong = (TextView)findViewById(R.id.shuizhun_gaocha2);

        gaocha_avg = (TextView)findViewById(R.id.shuizhun_gaocha_average);

        mhou_black_red = (TextView)findViewById(R.id.shuizhun_hou_black_redm);
        mqian_black_red = (TextView)findViewById(R.id.shuizhun_qian_black_redm);

        mgaocha_black = (TextView)findViewById(R.id.shuizhun_black_gaocham);
        mgaocha_red = (TextView)findViewById(R.id.shuizhun_red_gaocham);
        mheijianhong = (TextView)findViewById(R.id.shuizhun_gaocha2m);

        button_back = (Button)findViewById(R.id.shuizhun_back) ;
        button_next = (Button)findViewById(R.id.shuizhun_next) ;
        button_pingcha = (Button)findViewById(R.id.shuizhun_pingcha) ;
        //endregion

        //region 设置控件样式
        if (Shuizhun_setupActivity.dengji == 2){

            mhou_black_red.setText("后基辅差(mm)");
            mqian_black_red.setText("前基辅差(mm)");
            mgaocha_black.setText("基础高差(cm)");
            mgaocha_red.setText("辅助高差(cm)");
            mheijianhong.setText("基减辅(mm)");
        }
        //endregion

        //region 传入计算结果


        final Intent intent = getIntent();
        station.setText("第"+intent.getStringExtra("测站")+"测站");

        houshiju.setText(String.valueOf(intent.getDoubleExtra("后视距",0)));
        qianshiju.setText(String.valueOf(intent.getDoubleExtra("前视距",0)));
        shicha.setText(String.valueOf(intent.getDoubleExtra("当前视差",0)));
        leijicha_result.setText(String.valueOf(Caculate.Round(intent.getDoubleExtra("累积视差",0),1)));
        qian_black_red.setText(String.valueOf(intent.getDoubleExtra("前黑红读差",0)));
        hou_black_red.setText(String.valueOf(intent.getDoubleExtra("后黑红读差",0)));
        houjianqian.setText(String.valueOf(intent.getDoubleExtra("后减前",0)));
        gaocha_black.setText(String.valueOf(intent.getDoubleExtra("黑面高差",0)));
        gaocha_red.setText(String.valueOf(intent.getDoubleExtra("红面高差",0)));
        heijianhong.setText(String.valueOf(intent.getDoubleExtra("黑减红",0)));

        if (Shuizhun_setupActivity.dengji == 2){
            gaocha_avg.setText("平均高差:"+intent.getDoubleExtra("平均高差",0)+"cm");
        }
        if (Shuizhun_setupActivity.dengji == 4){
            gaocha_avg.setText("平均高差:"+intent.getDoubleExtra("平均高差",0)+"m");
        }
        //endregion

        //region 数据检核
        if (Shuizhun_setupActivity.dengji == 2){
            if (Math.abs(intent.getDoubleExtra("后视距",0)) >= 50){
                houshiju.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(intent.getDoubleExtra("前视距",0)) >= 50){
                qianshiju.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(intent.getDoubleExtra("当前视差",0)) >= 1){
                shicha.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(Caculate.Round(intent.getDoubleExtra("累积视差",0),1)) >= 3){
                leijicha_result.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(intent.getDoubleExtra("前黑红读差",0)) >= 0.4){
                qian_black_red.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(intent.getDoubleExtra("后黑红读差",0)) >= 0.4){
                hou_black_red.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(intent.getDoubleExtra("后减前",0)) >= 0.6){
                houjianqian.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(intent.getDoubleExtra("黑减红",0)) >= 0.6){
                heijianhong.setTextColor(Color.rgb(255, 0, 0));
            }
        }
        if (Shuizhun_setupActivity.dengji == 4){
            if (Math.abs(intent.getDoubleExtra("后视距",0)) >= 80){
                houshiju.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(intent.getDoubleExtra("前视距",0)) >= 80){
                qianshiju.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(intent.getDoubleExtra("当前视差",0)) >= 5){
                shicha.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(Caculate.Round(intent.getDoubleExtra("累积视差",0),1)) >= 10){
                leijicha_result.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(intent.getDoubleExtra("前黑红读差",0)) >= 3){
                qian_black_red.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(intent.getDoubleExtra("后黑红读差",0)) >= 3){
                hou_black_red.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(intent.getDoubleExtra("后减前",0)) >= 5){
                houjianqian.setTextColor(Color.rgb(255, 0, 0));
            }
            if (Math.abs(intent.getDoubleExtra("黑减红",0)) >= 5){
                heijianhong.setTextColor(Color.rgb(255, 0, 0));
            }
        }
        //endregion

        //region 重测
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isnext = false;

                Shuizhun_resultActivity.cezhan.remove( Shuizhun_resultActivity.cezhan.size() - 1);
                Shuizhun_resultActivity.gaocha.remove( Shuizhun_resultActivity.gaocha.size() - 1);
                Shuizhun_resultActivity.juli.remove( Shuizhun_resultActivity.juli.size() - 1);

                //之后在返回的界面清空所有控件，可以在上个活动的onresume事件编写清空代码
                finish();
            }
        });
        //endregion

        //region 下一站
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cezhan = intent.getStringExtra("测站");
                leijishiju = Caculate.Round(intent.getDoubleExtra("累积视差",0),1);

                //region 该实例对应ShuiZhunCeLiang表中的一条记录；
                Shuizhun_data shuiZhunCeLiang = new Shuizhun_data();

                shuiZhunCeLiang.setCezhan(intent.getStringExtra("测站"));
                shuiZhunCeLiang.setK1(intent.getIntExtra("k1",0));
                shuiZhunCeLiang.setK2(intent.getIntExtra("k2",0));
                shuiZhunCeLiang.setHhs(intent.getIntExtra("hhs",0));
                shuiZhunCeLiang.setHhz(intent.getIntExtra("hhz",0));
                shuiZhunCeLiang.setHhx(intent.getIntExtra("hhx",0));
                shuiZhunCeLiang.setQhs(intent.getIntExtra("qhs",0));
                shuiZhunCeLiang.setQhz(intent.getIntExtra("qhz",0));
                shuiZhunCeLiang.setQhx(intent.getIntExtra("qhx",0));
                shuiZhunCeLiang.setHhongz(intent.getIntExtra("hhongz",0));
                shuiZhunCeLiang.setQhongz(intent.getIntExtra("qhongz",0));
                //将该条记录到数据库
                shuiZhunCeLiang.save();
                //endregion

                Toast.makeText(Shuizhun_stationActivity.this, "数据已添加数据库", Toast.LENGTH_SHORT).show();
                isnext = true;
                finish();
            }
        });
        //endregion

        //region 平差
        button_pingcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Shuizhun_stationActivity.this,Shuizhun_resultActivity.class);
                startActivity(intent1);
            }
        });
        //endregion
    }
}
