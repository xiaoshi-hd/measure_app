package com.example.miss.Xianlu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.miss.R;

public class Xianlu_exampleActivity extends AppCompatActivity {
    public static boolean example;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xianlu_example);
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
