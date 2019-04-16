package com.example.miss.Shuizhun;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miss.Caculate;
import com.example.miss.Daoxian.Daoxian_resultActivity;
import com.example.miss.Daoxian.Starting_data;
import com.example.miss.Daoxian.Station;
import com.example.miss.Daoxian.stationAdapter;
import com.example.miss.R;

import java.util.ArrayList;
import java.util.List;

public class Shuizhun_stationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button button_back,button_next,button_pingcha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuizhun_station);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

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
        button_back = (Button)findViewById(R.id.shuizhun_back) ;
        button_next = (Button)findViewById(R.id.shuizhun_next) ;
        button_pingcha = (Button)findViewById(R.id.shuizhun_pingcha) ;

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Shuizhun_stationActivity.this,ShuizhunActivity.class);
                startActivity(intent);
            }
        });
        button_pingcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Shuizhun_stationActivity.this,Shuizhun_resultActivity.class);
                startActivity(intent1);
            }
        });
    }
}
