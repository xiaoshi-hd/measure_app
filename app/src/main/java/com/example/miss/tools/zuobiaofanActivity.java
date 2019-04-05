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

public class zuobiaofanActivity extends AppCompatActivity {

    //region 1.定义变量、控件
    private Toolbar toolbar;
    private Button button;
    private EditText editText_XA,editText_XB,editText_YA, editText_YB,editText_fangweijiao,editText_juli;
    private double XA,XB,YA,YB,fangweijiao,juli;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zuobiaofan);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        //region 2.找到控件
        toolbar = (Toolbar)findViewById(R.id.toolbar_zuobiaofan);
        button = (Button)findViewById(R.id.zuobiaofan_button);
        editText_XA = (EditText)findViewById(R.id.zuobiaofan_XA);
        editText_XB = (EditText)findViewById(R.id.zuobiaofan_XB);
        editText_YA = (EditText)findViewById(R.id.zuobiaofan_YA);
        editText_YB = (EditText)findViewById(R.id.zuobiaofan_YB);
        editText_fangweijiao = (EditText)findViewById(R.id.zuobiaofan_fangweijiao);
        editText_juli = (EditText)findViewById(R.id.zuobiaofan_juli);
        editText_fangweijiao.setFocusable(false);//设置结果不可编辑，但是能响应点击事件
        editText_juli.setFocusable(false);
        //endregion

        //region 3.设置Toolbar控件
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
        //endregion

        //region 4.为button设置点击事件
        button.setOnClickListener(new View.OnClickListener() {//设置button的监听事件
            @Override
            public void onClick(View v) {
                //region 输入数据检查，不能为空字符
                try{//运行try_catch时程序出现异常不会使程序崩溃
                    XA = Double.parseDouble(editText_XA.getText().toString());
                    XB = Double.parseDouble(editText_XB.getText().toString());
                    YA = Double.parseDouble(editText_YA.getText().toString());
                    YB = Double.parseDouble(editText_YB.getText().toString());
                }
                catch(Exception e){
                    Toast.makeText(zuobiaofanActivity.this, "请填写全部数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //endregion
                //region 计算
                fangweijiao = Caculate.hudutodms(Caculate.fangweijiaojisuan(XA,YA,XB,YB));
                editText_fangweijiao.setText(String.format("%f",fangweijiao));
                juli = Caculate.julijisuan(XA,YA,XB,YB);
                editText_juli.setText(String.valueOf(juli));
                //endregion
            }
        });
        //endregion
    }
}
