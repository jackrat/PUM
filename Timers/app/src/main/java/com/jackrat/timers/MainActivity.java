package com.jackrat.timers;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    TextView tv;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        timer = new Timer(15000, 100);


    }

    public void btStartClick(View view) {
        timer.start();
    }

    public void btStopClick(View view) {
        timer.cancel();
    }


    class Timer extends CountDownTimer {

        final long sek = 1000;
        final long min = sek * 60;
        final long hour = min * 60;
        final long day = hour * 24;

        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long mls = millisUntilFinished;
            long h = mls / hour;
            mls = mls % hour;
            long m = mls / min;
            mls = mls % min;
            long s = mls / sek;
            mls = mls % sek;
            long ss = mls / 100;
            tv.setText(String.format("%02d:%02d:%02d.%1d", h, m, s, ss));
        }

        @Override
        public void onFinish() {
            tv.setText("Koniec");
            File f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
            File[] fs = f.listFiles();
            if (fs.length > 0) {
                Uri u = Uri.fromFile(fs[0]);
                MediaPlayer.create(MainActivity.this, u)
                        .start();
            }
        }
    }

}