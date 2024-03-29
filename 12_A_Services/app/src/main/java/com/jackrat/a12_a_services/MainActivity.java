package com.jackrat.a12_a_services;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyIntentService.createNotificationChannel(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "IntentServiceActivity");
        menu.add(0, 1, 1, "BoundServiceActivity");
        menu.add(0, 2, 2, "startBoundService");
        menu.add(0, 3, 3, "stopBoundService");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent intent0 = new Intent(this, IntentServiceActivity.class);
                startActivity(intent0);
                break;
            case 1:
                Intent intent1 = new Intent(this, BoundServiceActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent3 = new Intent(this, MyBoundService.class);
                startService(intent3);
                break;
            case 3:
                Intent intent4 = new Intent(this, MyBoundService.class);
                stopService(intent4);
                break;
        }
        super.onOptionsItemSelected(item);
        return true;
    }
}
