package com.example.miss.Daoxian;

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
import android.widget.Toast;

import com.example.miss.Caculate;
import com.example.miss.R;


public class DaoxianActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button button;
    private EditText du_first,fen_first,miao_first;
    private EditText du_end,fen_end,miao_end;
    private EditText BX,BY,CX,CY;
    private RadioButton left,right;

    //region 说明菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.daoxian,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.daoxian_item1){
            Intent intent1 = new Intent(DaoxianActivity.this,Daoxian_explainActivity.class);
            startActivity(intent1);
        }
        return true;
    }
    //endregion
    protected void onResume() {
        super.onResume();
        //region 清空列表数据，防止返回首页再进入依旧保存有数据
        Log.i("Activity生命周期","OnResume方法调用");
        Daoxian_data.ceZhan.clear();
        Daoxian_data.disList.clear();
        Daoxian_data.guanCeJList.clear();
        //endregion
    }
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daoxian);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        //region 标题栏
        toolbar = (Toolbar)findViewById(R.id.toolbar_daoxian);
        toolbar.setTitle("附合导线近似平差计算");//设置Toolbar标题
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
        left = (RadioButton)findViewById(R.id.daoxian_left) ;
        right = (RadioButton)findViewById(R.id.daoxian_right) ;

        du_first=(EditText)findViewById(R.id.daoxian_du_first);
        fen_first=(EditText)findViewById(R.id.daoxian_fen_first);
        miao_first=(EditText)findViewById(R.id.daoxian_miao_first);
        du_end=(EditText)findViewById(R.id.daoxian_du_end);
        fen_end=(EditText)findViewById(R.id.daoxian_fen_end);
        miao_end=(EditText)findViewById(R.id.daoxian_miao_end);

        BX=(EditText)findViewById(R.id.daoxian_BX);
        BY=(EditText)findViewById(R.id.daoxian_BY);
        CX=(EditText)findViewById(R.id.daoxian_CX);
        CY=(EditText)findViewById(R.id.daoxian_CY);

        button =(Button)findViewById(R.id.daoxian_button_begin);
        //endregion

        //region 清空列表数据，防止退出再进入依旧保存有数据
        Daoxian_data.ceZhan.clear();
        Daoxian_data.disList.clear();
        Daoxian_data.guanCeJList.clear();
        //endregion
        //button的点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //region 转向选择
                if (left.isChecked()){
                    Daoxian_data.k = 1;
                }
                if (right.isChecked()){
                    Daoxian_data.k = -1;
                }
                //endregion
                //region 数据导入
                try {//运行try_catch时程序出现异常不会使程序崩溃
                   Daoxian_data.fangwei_first = Caculate.DMStohudu(Integer.parseInt(du_first.getText().toString())
                            ,Integer.parseInt(fen_first.getText().toString()),Double.parseDouble(miao_first.getText().toString()));
                    Daoxian_data.fangwei_end =Caculate.DMStohudu(Integer.parseInt(du_end.getText().toString())
                            ,Integer.parseInt(fen_end.getText().toString()),Double.parseDouble(miao_end.getText().toString()));
                    Daoxian_data.B = new Daoxian_data.ZuoBiao(Double.parseDouble(BX.getText().toString())
                            ,Double.parseDouble(BY.getText().toString()));
                    Daoxian_data.C = new Daoxian_data.ZuoBiao(Double.parseDouble(CX.getText().toString())
                            ,Double.parseDouble(CY.getText().toString()));
                }
                catch (Exception e) {
                    Toast.makeText(DaoxianActivity.this, "请正确填写全部数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Log.d("Activity生命周期", String.valueOf(Caculate.hudutodms(Daoxian_data.fangwei_first)));
                //endregion
                Intent intent = new Intent(DaoxianActivity.this,Daoxian_stationActivity.class);
                startActivity(intent);
            }
        });
        //endregion
    }
}
