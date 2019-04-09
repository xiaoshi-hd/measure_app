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

public class dms_translateActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button button0,button1;
    private EditText editText_dms,editText_dms_result,editText_hudu, editText_hudu_result;
    private double dms;
    private double dms_result;
    private double hudu;
    private double hudu_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dms_translate);
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//不让自动弹出软键盘

        toolbar = (Toolbar)findViewById(R.id.toolbar_dms_translate);
        button0 = (Button)findViewById(R.id.dms_translate_button0);
        button1 = (Button)findViewById(R.id.dms_translate_button1);
        editText_dms = (EditText)findViewById(R.id.dms_translate_dms);
        editText_dms_result = (EditText)findViewById(R.id.dms_translate_dms_result);
        editText_hudu = (EditText)findViewById(R.id.dms_translate_hudu);
        editText_hudu_result = (EditText)findViewById(R.id.dms_translate_hudu_result);
        editText_hudu_result.setFocusable(false);
        editText_dms_result.setFocusable(false);//设置结果不可编辑，但是能响应点击事件

        toolbar.setTitle("度分秒转换");//设置Toolbar标题
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

        //按钮1的响应事件
        button0.setOnClickListener(new View.OnClickListener() {//设置button的监听事件
            @Override
            public void onClick(View v) {
                //region 输入数据检查，不能为空字符
                try{//运行try_catch时程序出现异常不会使程序崩溃
                    dms = Double.parseDouble(editText_dms.getText().toString());
                }
                catch(Exception e){
                    Toast.makeText(dms_translateActivity.this, "请填写数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //endregion
                hudu_result = Caculate.dmstohudu(dms);
                editText_hudu_result.setText(String.format("%f",hudu_result));
            }
        });
        //按钮2的响应事件
        button1.setOnClickListener(new View.OnClickListener() {//设置button的监听事件
            @Override
            public void onClick(View v) {
                //region 输入数据检查，不能为空字符
                try{//运行try_catch时程序出现异常不会使程序崩溃
                    hudu = Double.parseDouble(editText_hudu.getText().toString());
                }
                catch(Exception e){
                    Toast.makeText(dms_translateActivity.this, "请填写数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //endregion
                dms_result = Caculate.hudutodms(hudu);
                editText_dms_result.setText(String.format("%f",dms_result));
            }
        });
    }
}
