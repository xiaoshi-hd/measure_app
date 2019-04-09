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

public class zuobiaozhengActivity extends AppCompatActivity {
    //region 1.定义变量、控件
    private Toolbar toolbar;
    private Button button;
    private EditText editText_XA,editText_XB,editText_YA, editText_YB,editText_fangweijiao,editText_juli;
    private double XA,XB,YA,YB,fangweijiao,juli;
    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuobiaozheng);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        //region 2.找到控件
        toolbar = (Toolbar)findViewById(R.id.toolbar_zuobiaozheng);
        button = (Button)findViewById(R.id.zuobiaozheng_button);
        editText_XA = (EditText)findViewById(R.id.zuobiaozheng_XA);
        editText_XB = (EditText)findViewById(R.id.zuobiaozheng_XB);
        editText_YA = (EditText)findViewById(R.id.zuobiaozheng_YA);
        editText_YB = (EditText)findViewById(R.id.zuobiaozheng_YB);
        editText_fangweijiao = (EditText)findViewById(R.id.zuobiaozheng_fangweijiao);
        editText_juli = (EditText)findViewById(R.id.zuobiaozheng_juli);
        editText_XB.setFocusable(false);//设置结果不可编辑，但是能响应点击事件
        editText_YB.setFocusable(false);
        //endregion

        //region 3.设置Toolbar控件
        toolbar.setTitle("坐标正算");//设置Toolbar标题
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

        //region 4.为button设置点击事件
        button.setOnClickListener(new View.OnClickListener() {//设置button的监听事件
            @Override
            public void onClick(View v) {
                //region 输入数据检查，不能为空字符
                try{//运行try_catch时程序出现异常不会使程序崩溃
                    XA = Double.parseDouble(editText_XA.getText().toString());
                    YA = Double.parseDouble(editText_YA.getText().toString());
                    fangweijiao = Double.parseDouble(editText_fangweijiao.getText().toString());
                    juli = Double.parseDouble(editText_juli.getText().toString());
                }
                catch(Exception e){
                    Toast.makeText(zuobiaozhengActivity.this, "请填写全部数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //endregion
                //region 计算
                XB = XA + juli * Math.cos(Caculate.dmstohudu(fangweijiao));
                editText_XB.setText(String.format("%f",XB));
                YB = YA + juli * Math.sin(Caculate.dmstohudu(fangweijiao));
                editText_YB.setText(String.format("%f",YB));
                //endregion
            }
        });
        //endregion
    }
}
