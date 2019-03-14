package com.example.miss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class Xianlu2Activity extends AppCompatActivity {
    TextView mtextView1;
    TextView mtextView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xianlu2);
        mtextView1 = (TextView) findViewById(R.id.xianlu2_t1);
        mtextView1.setText(String.valueOf(XianluActivity.str));
        mtextView2 = (TextView) findViewById(R.id.xianlu2_t2);
        mtextView2.setText(String.valueOf(XianluActivity.str1));
        //Log.d("",XianluActivity.str1);
    }
}
