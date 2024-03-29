package com.jackrat.moodlelogin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    WebView wv;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wv = findViewById(R.id.wv);
        wv.setWebChromeClient(new WebChromeClient());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAppCacheEnabled(true);
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (wv.getUrl().contains("moodle.pcz.pl/login")) {
                    wv.loadUrl("javascript:(function() {                            " +
                            "let del = document.querySelector('.potentialidp');     " +
                            "if(del !== null) {                                     " +
                            "  let ael = del.children[0];                           " +
                            "  if(ael !== null)                                     " +
                            "    ael.click();                                       " +
                            " }                                                     " +
                            "})();                                                  ");
                } else if (wv.getUrl().contains("logowanie.man.pcz.pl")) {
                    wv.loadUrl("javascript:(function() {                                " +
                            "       var inp1 = document.getElementById('username');     " +
                            "       inp1.value = 'LLLLLLLLLLLLLLLL';                    " +
                            "       var inp2 = document.getElementById('password');     " +
                            "       inp2.value = 'HHHHHHHHHHHHHHHH';                    " +
                            "       var btn = document.querySelector('.form-button');   " +
                            "       btn.click();" +
                            "})();                                                      ");
                }
            }
        });
    }



    public void go(View view) {
        wv.loadUrl("https://moodle.pcz.pl/login/index.php");
    }
}