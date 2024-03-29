package com.jackrat.a12_a_services;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IntentServiceActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        setTitle("IntentService");
        tv = findViewById(R.id.textView);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public void startIntentService(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);
    }


    public void noise(View view) {
        tv.append("\n" + "UI odpowiada: "+ sdf.format(new Date()) );
    }
}