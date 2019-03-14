package com.example.miss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Xianlu4Activity extends AppCompatActivity {
    public static boolean example;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xianlu4);
        Button button = (Button) findViewById(R.id.xianlu4_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                example = true;
                finish();
            }
        });
    }
}
