package com.example.miss.Xianlu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.miss.R;

public class Xianlu_resultActivity extends AppCompatActivity {
    TextView mtextView1;
    TextView mtextView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xianlu_result);
        mtextView1 = (TextView) findViewById(R.id.xianlu2_t1);
        mtextView1.setText(String.valueOf(XianluActivity.str));
        mtextView2 = (TextView) findViewById(R.id.xianlu2_t2);
        mtextView2.setText(String.valueOf(XianluActivity.str1));
    }
}
