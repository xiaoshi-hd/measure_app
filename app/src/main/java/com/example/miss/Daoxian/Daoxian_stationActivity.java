package com.example.miss.Daoxian;

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
import com.example.miss.R;

import java.util.ArrayList;
import java.util.List;

public class Daoxian_stationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private EditText du,fen,miao;
    private EditText cezhan,dis;
    private Button button_next,button_back,button_pingcha;
    private TextView disT,dism;
    private stationAdapter adapter;
    private List<Station> stationList=new ArrayList<>();
    private Station station;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daoxian_station);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_daoxian_jisuan);
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

        //region 找到控件
        cezhan=(EditText)findViewById(R.id.daoxian_cezhan);
        du=(EditText)findViewById(R.id.daoxian_du);
        fen=(EditText)findViewById(R.id.daoxian_fen);
        miao=(EditText)findViewById(R.id.daoxian_miao);
        dis=(EditText)findViewById(R.id.daoxian_dis);
        button_back=(Button)findViewById(R.id.daoxian_button_back);
        button_next=(Button)findViewById(R.id.daoxian_button_next);
        button_pingcha=(Button)findViewById(R.id.daoxian_button_pingcha);
        disT=(TextView)findViewById(R.id.daoxian_dis_text);
        dism = (TextView)findViewById(R.id.daoxian_m_text);
        //endregion

        //因为第一站不用观测距离，将dis,disT两个控件隐藏
        //这里将dis的内容设为0，是为了避免改编辑框为空，会造成输入不完整的提示
        dis.setText("0");
        dis.setVisibility(View.INVISIBLE);
        disT.setVisibility(View.INVISIBLE);
        dism.setVisibility(View.INVISIBLE);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(Daoxian_stationActivity.this));//设置组织方式
        adapter=new stationAdapter(stationList);
        recyclerView.setAdapter(adapter);

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    stationList.remove(stationList.size() - 1);
                    adapter.notifyDataSetChanged();//刷新adapter
                    //Log.d("Activity生命周期", String.valueOf(Daoxian_data.guanCeJList.get(Daoxian_data.guanCeJList.size() - 1)));
                    Daoxian_data.guanCeJList.remove(Daoxian_data.guanCeJList.size() - 1);
                    //Log.d("Activity生命周期", String.valueOf(Daoxian_data.guanCeJList.get(Daoxian_data.guanCeJList.size() - 1)));
                    if(Daoxian_data.guanCeJList.size()!=0) {
                        Daoxian_data.disList.remove(Daoxian_data.disList.size() - 1);
                    }
                    if(Daoxian_data.guanCeJList.size()==0){
                        dis.setText("0");
                        dis.setVisibility(View.INVISIBLE);
                        disT.setVisibility(View.INVISIBLE);
                        dism.setVisibility(View.INVISIBLE);
                    }
                }
                catch (Exception e) {
                    Toast.makeText(Daoxian_stationActivity.this, "上一站无数据", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addToList()==true){
                    dis.setVisibility(View.VISIBLE);
                    disT.setVisibility(View.VISIBLE);
                    dism.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();//刷新adapter
                    Toast.makeText(Daoxian_stationActivity.this,"该站数据已加入数据库",Toast.LENGTH_SHORT).show();
                }
            }
        });
        button_pingcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Daoxian_stationActivity.this,Daoxian_resultActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean addToList() {

        try{//一定要把station放在前面，这样输入无效数据能及时抛出异常，不会在列表中添加错误数据
            //将数据放入recyclerview，用于显示
            station = new Station(cezhan.getText().toString(),
                    Double.parseDouble(dis.getText().toString()),Caculate.DMStohudu(Integer.parseInt(du.getText().toString())
                    ,Integer.parseInt(fen.getText().toString()),Integer.parseInt(miao.getText().toString())));

            stationList.add(station);

            //因为第一站不观测距离，所以第一站不用往disList中加观测值
            if(Daoxian_data.guanCeJList.size()!=0){
                Daoxian_data.disList.add(Double.parseDouble(dis.getText().toString()));
            }
            Daoxian_data.ceZhan.add(cezhan.getText().toString());
            //将各观测角放入数据类中，便于计算平差
            Daoxian_data.guanCeJList.add(Caculate.DMStohudu(Integer.parseInt(du.getText().toString())
                    ,Integer.parseInt(fen.getText().toString()),Integer.parseInt(miao.getText().toString())));

        }
        catch (Exception e){
            Toast.makeText(Daoxian_stationActivity.this, "请正确填写全部数据", Toast.LENGTH_SHORT).show();
            return false;
        }

        cezhan.setText("");
        du.setText("");
        fen.setText("");
        miao.setText("");
        dis.setText("");

        return true;
    }
}
