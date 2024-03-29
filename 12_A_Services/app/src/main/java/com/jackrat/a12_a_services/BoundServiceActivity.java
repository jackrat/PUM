package com.jackrat.a12_a_services;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class BoundServiceActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 2, 2, "startBoundService");
        menu.add(0, 3, 3, "stopBoundService");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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


    MyBoundService myBoundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service);
        setTitle("BoundService");
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBoundService = ((MyBoundService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myBoundService = null;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(getApplicationContext(), MyBoundService.class);
        bindService(intent, serviceConnection, this.BIND_AUTO_CREATE);


    }


    @Override
    protected void onStop() {
        super.onStop();
        if (myBoundService != null) {
            unbindService(serviceConnection);
            myBoundService = null;
        }
    }

    public void ToastFromService(View view) {
        if (myBoundService != null)
            myBoundService.generateToast();
    }

    public void workClick(View view) {
        if (myBoundService != null)
            myBoundService.doWork();
    }

    public void workInBackClick(View view) {
        if (myBoundService != null)
            myBoundService.doWorkInThread();
    }
}