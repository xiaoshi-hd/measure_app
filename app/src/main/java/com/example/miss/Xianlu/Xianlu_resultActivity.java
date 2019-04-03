package com.example.miss.Xianlu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
    //region 说明菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.xianlu_result,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.xianlu_result_item){
            Intent intent = new Intent(Xianlu_resultActivity.this,Xianlu_explainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.xianlu_result_item2){
            Intent intent = new Intent(Xianlu_resultActivity.this,Xianlu_exampleActivity.class);
            startActivity(intent);
        }
        return true;
    }
    //endregion
}

