package com.jackrat.a11_a_threads;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.idThr: startActivity(new Intent(this, ThreadActivity.class)); break;
            case R.id.idAsTas:startActivity(new Intent(this, AsyncTaskActivity.class));break;
        }

    }
}
