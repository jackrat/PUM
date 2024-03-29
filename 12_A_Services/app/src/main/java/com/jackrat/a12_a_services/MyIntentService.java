package com.jackrat.a12_a_services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MyIntentService extends IntentService {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Handler handler = new Handler();

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("INT_SRV", sdf.format(new Date()) + " onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("INT_SRV", sdf.format(new Date()) + " onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    String wykreowany;

    @Override
    public void onCreate() {
        super.onCreate();
        wykreowany = "Ur.: " + sdf.format(new Date());
        Log.d("INT_SRV", sdf.format(new Date()) + " onCreate");
    }


    public MyIntentService() {
        super("MyIntentService");

    }

    String s;

    @Override
    protected void onHandleIntent(Intent intent) {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            s = "MyIntentService działa, cykl:" + String.valueOf(i);
            Log.d("INT_SRV", s);

            //tak źle
            //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();

            //tak dobrze
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    sendNotify(s);
                }
            });

        }
    }


    //POWIADOMIENIA

    int notificationId = 0;

    void sendNotify(String s) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "666")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("MyIntentService!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentText(s);

        Notification notification = builder.build();

        notificationManager.notify(notificationId++, notification);
    }


    //API 26+
    public static void createNotificationChannel(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("666", "nazwa kanału 666", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Opis kanału 666");
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
