package com.example.miss.Daoxian;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.Caculate;
import com.example.miss.R;

public class Daoxian_resultActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daoxian_result);
        //region 标题栏加返回箭头
        toolbar = (Toolbar)findViewById(R.id.toolbar_daoxian_result);
        toolbar.setTitle("附和导线近似平差计算");//设置Toolbar标题
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
        textView = (TextView)findViewById(R.id.daoxian_result_text);

        try{
            Starting_data.jisuan();

            if (Starting_data.warn_jiao){
                Toast.makeText(Daoxian_resultActivity.this, "角度闭合差超限！！！", Toast.LENGTH_LONG).show();
            }
            if (Starting_data.warn_bian){
                Toast.makeText(Daoxian_resultActivity.this, "导线全长闭合差超限！！！", Toast.LENGTH_LONG).show();
            }

            String str;

            str = "---------------限差要求--------------------\n";
            str += "角度闭合差限差：" + Caculate.Round(24 * Math.sqrt(Starting_data.guanCeJList.size()), 3) + "\n";
            str += "导线全长相对闭合差限差:" + String.format("%1.4f",1 / 5000d) + "\n\n";
            str += "---------------导线基本信息-----------------\n";
            str += "测站数：" + Starting_data.guanCeJList.size() + "\n";
            str += "导线全长" + Caculate.Round(Starting_data.sum_dis,3) + "\n";
            str += "角度闭合差：" + Caculate.hudutos(Starting_data.bhc) + "″\n";
            str += "各站角度改正值：" + Caculate.hudutos(Starting_data.bhc / Starting_data.guanCeJList.size()) + "″\n";
            str += "X坐标闭合差：" + Caculate.Round(Starting_data.xbhc * 1000, 1) + "mm" + "\n";
            str += "Y坐标闭合差：" + Caculate.Round(Starting_data.ybhc * 1000, 1)  + "mm" + "\n";
            str += "导线全长闭合差：" + String.format("%f",Caculate.Round(Starting_data.xybhc / Starting_data.sum_dis, 8)) + "\n\n";
            str += "---------------测站点坐标-------------------\n";
            str += Caculate.formatStr("测站",10) + Caculate.formatStr("X坐标",20) + Caculate.formatStr("Y坐标",20) + "\n";
            for (int i = 0; i < Starting_data.resultList.size(); i++)
            {
                str += Caculate.formatStr(String.valueOf(Starting_data.ceZhan.get(i)),10) + "    ";
                str += Caculate.formatStr(String.valueOf(Caculate.Round(Starting_data.resultList.get(i).getX(),3)),20) + "    ";
                str += Caculate.formatStr(String.valueOf(Caculate.Round(Starting_data.resultList.get(i).getY(),3)),20) + "\n";
            }
            str += "\n---------------角度数据---------------------\n";
            str += String.format("%-20s", "    ") + String.format("%-20s", "     ") + String.format("%-20s", "方位角") + "\n";
            str += String.format("%-10s", "测站名") + String.format("%-20s", "观测角") + "\n";
            for (int i = 0; i < Starting_data.fangweijiao.size(); i++)
            {
                str +=String.format("%-20s", "    ") + String.format("%-20s", "     ") + Caculate.hudutoDMS(Starting_data.fangweijiao.get(i)) + "\n";
                if (i != Starting_data.fangweijiao.size() - 1)
                {
                    str += String.format("%-15s", Starting_data.ceZhan.get(i));//输出的是改正后的观测角
                    str += String.format("%-20s", Caculate.hudutoDMS(Starting_data.guanCeJList.get(i) - Starting_data.k * Starting_data.bhc / Starting_data.guanCeJList.size())) + "\n";
                }
            }
            //endregion
            textView.setText(str);
        }
        catch (Exception e){
            Toast.makeText(Daoxian_resultActivity.this, "请正确填写数据再进行计算", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
