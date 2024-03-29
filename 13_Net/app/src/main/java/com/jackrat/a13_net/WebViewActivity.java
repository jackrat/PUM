package com.jackrat.a13_net;


import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;


    void info(String msg) {
        (new AlertDialog.Builder(this))
                .setMessage(msg)
                .setPositiveButton("OK", null)
                .show();
    }

    //   Dla http (nie dla https) potrzebna pozycja w manifeście
    //    w tagu <application
    //               android:usesCleartextTraffic="true"


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        setTitle("WebView");
        webView = findViewById(R.id.ww);
        try {
            webView.setWebChromeClient(new WebChromeClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setAppCacheEnabled(true);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (url.contains("212"))
                        webView.loadUrl(jsOurGitPage);
                    else if (url.contains("google"))
                        webView.loadUrl(jsForGoogle);
                }
            });

        } catch (Exception exc) {
            info(exc.getMessage());
        }

    }


    String jsOurGitPage =
            "            javascript:(function() {                               " +
                    "       alert('Zaraz wpiszę jakiś login i hasło!');         " +
                    "       var inp1 = document.getElementById('user_name');    " +
                    "       inp1.value = 'jakiś login';                         " +
                    "       var inp2 = document.getElementById('password');     " +
                    "       inp2.value = 'jakieś hasło';                        " +
                    "       AndroidJS.methodForGit();                           " +
                    "    })()                                                   ";


    String jsForGoogle =
            "           javascript:(function() {                             " +
                    "      alert('Zaraz skasuję logo Google!');              " +
                    "      var hplogo = document.getElementById('hplogo');   " +
                    "      hplogo.parentNode.removeChild(hplogo);            " +
                    "      AndroidJS.methodForGoogle();                     " +
                    "   })()                                                 ";



    @JavascriptInterface
    public void methodForGoogle() {
        info("Zrobione!\nPozdrowienia od GOOGLE!");
    }

    @JavascriptInterface
    public void methodForGit() {
        info("Zrobione\nPozdrowienia od GIT'a!");
    }



    public void gitClick(View view) {
        webView.loadUrl("http://212.87.228.200:3000/user/login");
        webView.addJavascriptInterface(this, "AndroidJS");
    }


    public void gooClick(View view) {
        webView.loadUrl("https://www.google.pl/");
        webView.addJavascriptInterface(this, "AndroidJS");
    }


}