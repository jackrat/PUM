package com.jackrat.a12_a_services;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyBoundService extends Service {
    String wykreowany;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    volatile boolean isDone = false;
    Thread workThr = null;
    Handler handler = new Handler();
    IBinder binder = new LocalBinder();
    String s;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("B_SRV", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("B_SRV", "onDestroy");
        if (workThr != null && workThr.isAlive()) {
            isDone = true;
            Log.i("B_SRV", "ustawono: isDone = true");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("B_SRV", "onStartCommand");
        //doWork();
        doWorkInThread();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("B_SRV", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i("B_SRV", "onRebind");
        super.onRebind(intent);
    }

    public MyBoundService() {
        wykreowany = "Ur.: " + sdf.format(new Date());
        Log.i("B_SRV", "constructed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i("B_SRV", "onBind");
        return binder;
    }

    class LocalBinder extends Binder {
        public MyBoundService getService() {
            return MyBoundService.this;
        }
    }

    private String getStateText(CharSequence cs) {
        return String.format("%tc MyBoundService %s ---- %s", (new Date()), cs, wykreowany);
    }

    public void generateToast() {
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), getStateText("from generateToast()"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Blokuje UI
    public void doWork() {
        isDone = false;
        for (int i = 0; i < 20; i++) {
            if (isDone) {
                Log.i("B_SRV", "isDone == true i KONIEC!!!!!");
                workThr = null;
                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            s = "MyBoundService działa, cykl:" + String.valueOf(i);
            Log.i("B_SRV", s);


            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //Nie blokuje UI
    public void doWorkInThread()
    {
        workThr = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        doWork();
                    }
                }
        );
        workThr.start();
    }









}