package com.example.miss.tools;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miss.Caculate;
import com.example.miss.R;

public class fangweijiaoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button button;
    private EditText editText_qi_X,editText_qi_Y,editText_zhong_X, editText_zhong_Y,editText_result;
    private double qi_X;
    private double qi_Y;
    private double zhong_X;
    private double zhong_Y;
    private double result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fangweijiao);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        toolbar = (Toolbar)findViewById(R.id.toolbar_fangweijiao);
        button = (Button)findViewById(R.id.fangweijiao_button);
        editText_qi_X = (EditText)findViewById(R.id.fangweijiao_qi_X);
        editText_qi_Y = (EditText)findViewById(R.id.fangweijiao_qi_Y);
        editText_zhong_X = (EditText)findViewById(R.id.fangweijiao_zhong_X);
        editText_zhong_Y = (EditText)findViewById(R.id.fangweijiao_zhong_Y);
        editText_result = (EditText)findViewById(R.id.fangweijiao_result);
        editText_result.setFocusable(false);//设置结果不可编辑，但是能响应点击事件

        toolbar.setTitle("测量程序");//设置Toolbar标题
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

        button.setOnClickListener(new View.OnClickListener() {//设置button的监听事件
            @Override
            public void onClick(View v) {
                //region 输入数据检查，不能为空字符
                try{//运行try_catch时程序出现异常不会使程序崩溃
                    qi_X = Double.parseDouble(editText_qi_X.getText().toString());
                    qi_Y = Double.parseDouble(editText_qi_Y.getText().toString());
                    zhong_X = Double.parseDouble(editText_zhong_X.getText().toString());
                    zhong_Y = Double.parseDouble(editText_zhong_Y.getText().toString());
                }
                catch(Exception e){
                    Toast.makeText(fangweijiaoActivity.this, "请填写全部数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //endregion
                result = Caculate.hudutodms(Caculate.fangweijiaojisuan(qi_X,qi_Y,zhong_X,zhong_Y));
                editText_result.setText(String.format("%f",result));
            }
        });
    }
}
