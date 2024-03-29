package com.jackrat.a10_c_hardware;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.ListIterator;

public class WiFiActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver = null;
    WifiManager wifiManager;

    TextView tvStatus;
    Button btnStopScan;
    Button btnScan;
    Button listKnown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wi_fi);
        TextView tvStatus = findViewById(R.id.status);
        Button btnStopScan = findViewById(R.id.stop_scan);
        Button btnScan = findViewById(R.id.scan);
        Button listKnown = findViewById(R.id.list_known);

        setTitle("WiFi");
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        btnScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                wifiManager.startScan();
                broadcastReceiver = createBroadcastReceiver();
                registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
                Toast.makeText(WiFiActivity.this, "Skanowanie rozpoczęte", Toast.LENGTH_SHORT).show();
                btnScan.setVisibility(View.GONE);
                btnStopScan.setVisibility(View.VISIBLE);
            }
        });


        btnStopScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (broadcastReceiver != null) {
                    unregisterReceiver(broadcastReceiver);
                    broadcastReceiver = null;
                }
                btnStopScan.setVisibility(View.GONE);
                btnScan.setVisibility(View.VISIBLE);

            }
        });


        listKnown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                @SuppressLint("MissingPermission") ListIterator<WifiConfiguration> configs = wifiManager.getConfiguredNetworks().listIterator();
                String allConfigs = "Konfiguracje: \n";
                while (configs.hasNext()) {
                    WifiConfiguration config = configs.next();
                    String configInfo = "Nazwa: " + config.SSID + "; priorytet = " + config.priority;
                    Log.v("WiFi", configInfo);
                    allConfigs += configInfo + "\n";
                }
                tvStatus.setText(allConfigs);
            }
        });

    }

    @Override
    protected void onPause() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
        super.onPause();
    }


    BroadcastReceiver createBroadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                List<ScanResult> resultList = WiFiActivity.this.wifiManager.getScanResults();
                int foundCount = resultList.size();

                Toast.makeText(WiFiActivity.this, "Skanowanie gotowe, znaleziono " + foundCount, Toast.LENGTH_SHORT).show();
                ListIterator<ScanResult> results = resultList.listIterator();
                String fullInfo = "Wyniki skanowania: \n";
                while (results.hasNext()) {
                    ScanResult info = results.next();
                    String wifiInfo = "Nazwa: " + info.SSID + "; możliwości = " + info.capabilities + "; siła sygnału = " + info.level + "dBm";
                    Log.v("WiFi", wifiInfo);
                    fullInfo += wifiInfo + "\n";
                }
                tvStatus.setText(fullInfo);

            }
        };
    }

}