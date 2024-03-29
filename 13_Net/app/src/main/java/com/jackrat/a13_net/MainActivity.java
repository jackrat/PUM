package com.jackrat.a13_net;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    TextView tv;


    //   Dla http (nie https) potrzebna pozycja w manifeście
    //    w tagu <application
    //               android:usesCleartextTraffic="true"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
    }

    String adr1 = "https://www.google.pl/";
    String adr2 = "http://212.87.228.200:3000/";
    String curAdr = null;
    StringBuilder sb = new StringBuilder();


    public void btnUrlConnClick(View view) {
        curAdr = curAdr == null || curAdr == adr2 ? adr1 : adr2;
        tv.setText("");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(curAdr);

                    URLConnection conn = url.openConnection();
                    String contEnd = conn.getContentEncoding();
                    Log.d("HTTP_GET", "ENCODING: " + contEnd);

                    String mime = conn.getContentType();
                    Log.d("HTTP_GET", "TYP MIME: " + mime);

                    int len = conn.getContentLength();
                    Log.d("HTTP_GET", "ContentLength: " + String.valueOf(len));


                    InputStream inpStr = conn.getInputStream();
                    byte[] buf = new byte[1024];
                    int rs = inpStr.read(buf);
                    while (rs > 0) {
                        String s = new String(buf, 0, rs);
                        //Log.d("HTTP_GET", s);
                        sb.append(s);
                        rs = inpStr.read(buf);
                    }
                    inpStr.close();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(sb.toString());
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    public void btnWWClick(View view) {
        startActivity(new Intent(this, WebViewActivity.class));
    }

    /*
    String s;
    public void btnUrlConnClick(View view) {
        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://www.google.pl/");
                    URLConnection conn = url.openConnection();
                    InputStream inputStream = conn.getInputStream();
                    byte[] buf = new byte[1024];
                    int rb = inputStream.read(buf);
                    s = new String(buf);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(s);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thr.start();
    }
    */
}