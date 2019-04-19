package com.example.miss.Shuizhun;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.Caculate;
import com.example.miss.Daoxian.Daoxian_data;
import com.example.miss.R;

import java.util.ArrayList;

public class Shuizhun_resultActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView t1,t2;
    private double bhc,sum_juli,sum_gaocha;
    private ArrayList<Double> gaizhengshu = new ArrayList<>();
    private ArrayList<Double> gaihougaocha = new ArrayList<>();
    private ArrayList<Double> gaocheng = new ArrayList<>();
    private ArrayList<String> dianhao = new ArrayList<>();

    public static ArrayList<String> cezhan = new ArrayList<>();
    //存储距离
    public static ArrayList<Double> juli = new ArrayList<>();
    //存储高差
    public static ArrayList<Double> gaocha = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuizhun_result);

        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_shuizhun_result);
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

        t1 = (TextView)findViewById(R.id.shuizhun_result_t1);
        t2 = (TextView)findViewById(R.id.shuizhun_result_t2);

        gaizhengshu.clear();
        gaihougaocha.clear();
        gaocheng.clear();

        //region 水准平差
        sum_juli = 0;
        for (int i = 0; i < juli.size(); i++) {
            sum_juli = juli.get(i) + sum_juli;
        }
        sum_gaocha = 0;
        for (int i = 0; i < gaocha.size(); i++) {
            sum_gaocha = gaocha.get(i) + sum_gaocha;
        }
        if (Shuizhun_setupActivity.dengji == 2) {
            bhc = sum_gaocha / 100 - (Shuizhun_setupActivity.gaocheng_end - Shuizhun_setupActivity.gaocheng_start);//观测值减去真实值
        }
        if (Shuizhun_setupActivity.dengji == 4) {
            bhc = sum_gaocha - (Shuizhun_setupActivity.gaocheng_end - Shuizhun_setupActivity.gaocheng_start);//观测值减去真实值
        }
        dianhao.add(0,"Start");
        for (int i = 0; i < juli.size() - 1; i++) {
            dianhao.add(String.valueOf(i + 1));
        }
        dianhao.add("End");
        gaocheng.add(Shuizhun_setupActivity.gaocheng_start);
        //闭合差改正数都是m
        for (int i = 0; i < gaocha.size(); i++)//改正分配
        {
            if (Shuizhun_setupActivity.dengji == 2) {
                gaizhengshu.add(Caculate.Round(-bhc * juli.get(i) / sum_juli,6));
                gaihougaocha.add(Caculate.Round(gaocha.get(i) / 100 + gaizhengshu.get(i), 6));
                gaocheng.add(Caculate.Round(gaihougaocha.get(i) + gaocheng.get(i),6));
            }
            if (Shuizhun_setupActivity.dengji == 4) {
                gaizhengshu.add(Caculate.Round(-bhc * juli.get(i) / sum_juli,4));
                gaihougaocha.add(Caculate.Round(gaocha.get(i) + gaizhengshu.get(i), 4));
                gaocheng.add(Caculate.Round(gaihougaocha.get(i) + gaocheng.get(i),4));
            }
        }
        //gaocheng.add(Shuizhun_setupActivity.gaocheng_end);
        //endregion

        //region 计算报告
        String str;
        str = "\t已知点数据\n";
        str += "-------------------------------------------------------------------\n";
        str += "起点高程: " + Shuizhun_setupActivity.gaocheng_start + " m      " + "  终点高程: " + Shuizhun_setupActivity.gaocheng_end + " m\n";
        str += "测站数：" + gaocha.size() + "\n";
        str += "总距离：" + Caculate.Round(sum_juli,1) + " m\n";
        if (Shuizhun_setupActivity.dengji == 2) {
            str += "高程闭合差：" + Caculate.Round(bhc * 1000, 2) + " mm\n";
            if (sum_juli <= 1000){
                str += "高程闭合差允许值：" + " 4 mm\n";
            }
            else{
                str += "高程闭合差允许值：" + 4 * Caculate.Round(Math.sqrt(sum_juli / 1000),1) + " mm\n";
            }
        }
        if (Shuizhun_setupActivity.dengji == 4) {
            str += "高程闭合差：" + Caculate.Round(bhc * 1000, 1) + " mm\n";
            if (sum_juli <= 1000){
                str += "高程闭合差允许值：" + " 20 mm\n";
            }
            else {
                str += "高程闭合差允许值：" + 20 * Caculate.Round(Math.sqrt(sum_juli / 1000), 1) + " mm\n";
            }
        }

        String str1;
        str1 = "\t高程配赋数据\n";
        str1 += "----------------------------------------------------------------------------------------------------------------------------------------------------\n";
        if (Shuizhun_setupActivity.dengji == 4) {
            str1 += Caculate.formatStr("点名", 10) + Caculate.formatStr("测站", 15) + Caculate.formatStr("平距(m)", 15) + Caculate.formatStr("高差(m)", 15)
                    + Caculate.formatStr("改正数(mm)", 15) + Caculate.formatStr("改后高差(m)", 15) + Caculate.formatStr("高程(m)", 15) + "\n";
            for (int i = 0; i < gaocheng.size(); i++) {
                str1 += Caculate.formatStr(String.valueOf(dianhao.get(i)), 25) + String.format("%-60s", "     ") + String.format("%-60s", "     ") + String.format("%-15s", Caculate.Round(gaocheng.get(i),4)) + "\n";
                if (i != gaocheng.size() - 1) {
                    str1 += String.format("%-18s", "     ") + String.format("%-20s", cezhan.get(i)) + String.format("%-20s", juli.get(i)) + String.format("%-20s", gaocha.get(i));
                    str1 += String.format("%-25s", Caculate.Round(gaizhengshu.get(i) * 1000,1)) + String.format("%-20s", Caculate.Round(gaihougaocha.get(i),4)) + "\n";
                }
            }
        }
        if (Shuizhun_setupActivity.dengji == 2) {
            str1 += Caculate.formatStr("点名", 10) + Caculate.formatStr("测站", 15) + Caculate.formatStr("平距(m)", 15) + Caculate.formatStr("高差(cm)", 15)
                    + Caculate.formatStr("改正数(mm)", 15) + Caculate.formatStr("改后高差(cm)", 15) + Caculate.formatStr("高程(m)", 15) + "\n";
            for (int i = 0; i < gaocheng.size(); i++) {
                str1 += Caculate.formatStr(String.valueOf(dianhao.get(i)), 25) + String.format("%-60s", "     ") + String.format("%-60s", "     ") + String.format("%-15s", Caculate.Round(gaocheng.get(i),6)) + "\n";
                if (i != gaocheng.size() - 1) {
                    str1 += String.format("%-18s", "     ") + String.format("%-20s", cezhan.get(i)) + String.format("%-20s", juli.get(i)) + String.format("%-20s", gaocha.get(i));
                    str1 += String.format("%-25s", Caculate.Round(gaizhengshu.get(i) * 1000,3)) + String.format("%-20s", Caculate.Round(gaihougaocha.get(i) * 100,4)) + "\n";
                }
            }
        }
        t1.setText(str);
        t2.setText(str1);
        //endregion
    }
}
