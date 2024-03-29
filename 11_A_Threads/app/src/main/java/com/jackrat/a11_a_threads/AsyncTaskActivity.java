package com.jackrat.a11_a_threads;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AsyncTaskActivity extends AppCompatActivity {

    ProgressBar pb;
    TextView tv;
    ScrollView sv;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    MyAsyncTask mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        setTitle("AsyncTask");
        tv = findViewById(R.id.tvAsTas);
        pb = findViewById(R.id.pbAsTas);
        sv = findViewById(R.id.svAsTas);
    }

    public void start(View view) {
        mat = new MyAsyncTask();
        mat.execute(20);
    }

    public void stop(View view) {
        if (mat != null)
            mat.cancel(true);
    }

    public void testUI(View view) {
        tv.append(String.format("\n%s test UI", sdf.format(new Date())));
        sv.fullScroll(ScrollView.FOCUS_DOWN);
    }

    class MyProgres {
        public String msg;
        public int percent;
    }




        class MyAsyncTask extends AsyncTask<Integer, MyProgres, List<String>> {

        List<String> result;
        MyProgres progress = new MyProgres();
        private long id;

        public MyAsyncTask() {
            super();
            id = (new Date()).getTime();
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            String s = String.format("%s PreExecute of %d", sdf.format(new Date()), id);
            Log.d("ASY_TAS", s);
            result = new ArrayList<String>();
            pb.setProgress(0);
            tv.append("\n" + s);
            sv.fullScroll(ScrollView.FOCUS_DOWN);
        }


        @Override
        protected List<String> doInBackground(Integer... integers) {
            result.clear();
            int n = integers[0];
            int licznik = 0;
            while (licznik < n) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                licznik++;
                progress.msg = String.format("%s AsyncTask %d działa, licznik: %d", sdf.format(new Date()), id, licznik);
                progress.percent = 100 * licznik / n;
                result.add(progress.msg);
                Log.d("ASY_TAS", progress.msg);
                publishProgress(progress);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.append("\n" + progress.msg);
                        sv.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                if (this.isCancelled())
                    break;
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(MyProgres... values) {
            super.onProgressUpdate(values);
            MyProgres p = values[0];
            pb.setProgress(p.percent);
            tv.append("\n"+ String.valueOf(p));
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
            try {
                pb.setProgress(0);
                StringBuilder sb = new StringBuilder();
                sb.append(sdf.format(new Date()) + " STOP\n");
                for (String si : strings) {
                    sb.append("\n" + si);
                }
                tv.append("\n" + sb.toString());
                sv.fullScroll(ScrollView.FOCUS_DOWN);
                (new AlertDialog.Builder(AsyncTaskActivity.this))
                        .setTitle("UWAGA!")
                        .setPositiveButton("OK", null)
                        .setMessage(String.format("KONIEC AsyncTask %d\n", id, sb.toString()))
                        .show();
            }
            catch (Exception exc)
            {
                Log.d("ASY_TAS", exc.getMessage());
            }
        }


    }



}