package com.jackrat.a11_b_threadssync;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {


    EditText etW;
    EditText etC;
    Bilans b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etW = findViewById(R.id.etW);
        etC = findViewById(R.id.etC);
        b = new Bilans();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "n Threads start v1");
        menu.add(1, 2, 2, "n Threads start v2");
        menu.add(1, 3, 3, "n Threads start v3");
        menu.add(1, 4, 4, "n Threads start v4");
        menu.add(1, 5, 5, "volatile");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                start_nThreads(1);
                break;
            case 2:
                start_nThreads(2);
                break;
            case 3:
                start_nThreads(3);
                break;
            case 4:
                Lock lock = new ReentrantLock();
                b.setLock(lock);
                start_nThreads(4);
                break;
            case 5:
                (new VolatileExample()).exampleStart();
                break;
        }
        return true;
    }

    private void start_nThreads(int nWariant) {
        int nWatkow = Integer.parseInt(etW.getText().toString());
        int nIter = Integer.parseInt(etC.getText().toString());
        b.zeruj();
        Thread[] thread = new Thread[nWatkow];
        for (int i = 0; i < nWatkow; i++)
            thread[i] = new BilansThread("W" + String.valueOf(i + 1), b, nIter, nWariant);

        for (int i = 0; i < nWatkow; i++) {
            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("THR_N", "KONIEC!");
    }
}
