package com.jackrat.a11_a_threads;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThreadActivity extends AppCompatActivity {

    ProgressBar pb;
    TextView tv;
    ScrollView sv;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        setTitle("Thread");
        tv = findViewById(R.id.tvThr);
        pb = findViewById(R.id.pbThr);
        sv = findViewById(R.id.svThr);
    }

    String s;
    Thread thr;

    public void start(View view) {
        thr = new Thread(new Runnable() {
            @Override
            public void run() {
                int licznik = 0;
                int n = 20;
                while (licznik < n && !thr.isInterrupted()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        s = String.format("%s Thread Interrupted", sdf.format(new Date()));
                        Log.d("MY_THR", s);
                        e.printStackTrace();
                        break;
                    }
                    licznik++;
                    s = String.format("%s Thread działa, licznik: %d", sdf.format(new Date()), licznik);
                    Log.d("MY_THR", s);
                    pb.setProgress(100 * licznik / n);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.append("\n" + s);
                            sv.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });

                }
            }
        });
        thr.start();
    }

    public void stop(View view) {
        if (thr != null && thr.isAlive())
            thr.interrupt();
    }

    public void testUI(View view) {
        tv.append(String.format("\n%s test UI", sdf.format(new Date())));
        sv.fullScroll(ScrollView.FOCUS_DOWN);
    }
}