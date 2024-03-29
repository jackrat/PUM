package com.jackrat.a10_c_hardware;

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


    public void btnWiFiClick(View view) {
        startActivity (new Intent(this, WiFiActivity.class));
    }

    public void btnSensorClick(View view) {
        startActivity (new Intent(this, SensorsActivity.class));
    }

    public void btnBatteryClick(View view) {
        startActivity (new Intent(this, BatteryActivity.class));
    }
}