package com.jackrat.a08_permisje_cd;


import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;





public class MainActivity extends AppCompatActivity {

    TextView tv;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    void addLine(String msg) {
        tv.setText(String.format("%s\n%s", tv.getText(), msg));
    }

    void show(String msg) {
        (new AlertDialog.Builder(MainActivity.this))
                .setTitle("Uwaga!")
                .setMessage(msg)
                .setPositiveButton("OK", null).
                show();
        addLine("Błąd");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
    }


    //wymaga ACCESS_WIFI_STATE w AndroidManifest
    public void go_01_readWiFiState(View view) {
        try {
            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Service.WIFI_SERVICE);
            boolean wifiEnabled = wm.isWifiEnabled();
            String msg = String.format("%s Wifi is: %s", sdf.format(new Date()), String.valueOf(wifiEnabled));
            addLine(msg);
        } catch (Exception exc) {
            show(exc.toString());
        }
    }

    //wymaga CHANGE_WIFI_STATE w AndroidManifest
    public void go_02_changeWiFiState(View view) {
        try {
            WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Service.WIFI_SERVICE);
            boolean wifiEnabled = wm.isWifiEnabled();
            wifiEnabled = !wifiEnabled;
            wm.setWifiEnabled(wifiEnabled);
        } catch (Exception exc) {
            show(exc.toString());
        }






    }

    //nie wymaga niczego !!!
    public void go_03_readMDState(View view) {
        try {
            TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            boolean mdIsEnabled = tm.getDataState() == TelephonyManager.DATA_CONNECTED;
            String msg = String.format("%s Mob.Data is: %s", sdf.format(new Date()), String.valueOf(mdIsEnabled));
            addLine(msg);
        } catch (Exception exc) {
            show(exc.toString());
        }

    }

    //wymaga MODIFY_PHONE_STATE  w AndroidManifest a to jest realnie osiągalne tylko dla aplikacji systemowych!!!
    public void go_04_changeMDState(View view) {
        try {
            TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            boolean mdIsEnabled = tm.getDataState() == TelephonyManager.DATA_CONNECTED;
            mdIsEnabled = !mdIsEnabled;
            tm.setDataEnabled(mdIsEnabled);
        } catch (Exception exc) {
            show(exc.toString());
        }
        goSettData();
    }


    void goSettData()
    {
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
            Intent intent = new Intent(Settings.ACTION_DATA_USAGE_SETTINGS);
            startActivity(intent);
        }
    }


    //wymaga CHANGE_NETWORK_STATE  w AndroidManifest - nie działa na nowszych API!, działa na API 16
    public void go_05_changeMDState(View view) {
        try {
            TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            boolean mdIsEnabled = tm.getDataState() == TelephonyManager.DATA_CONNECTED;
            mdIsEnabled = !mdIsEnabled;

            Object oCS = getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            Class<?> classCS = oCS.getClass();
            Field iConManField = classCS.getDeclaredField("mService");
            iConManField.setAccessible(true);
            Object obIConMan = iConManField.get(oCS);
            Class<?> classObIConMan = obIConMan.getClass();
            Method setMobileDataEnabledMethod = classObIConMan.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.invoke(obIConMan, mdIsEnabled);
        } catch (Exception exc) {
            show(exc.toString());
        }
        goSettData();
    }


    //wymaga ACCESS_NETWORK_STATE  w AndroidManifest
    public void go_06_readOnline(View view) {
        try{
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInjfo = cm.getActiveNetworkInfo();
            boolean isOnline = netInjfo!=null && netInjfo.isConnected();
            String msg = String.format("%s is online: %s", sdf.format(new Date()), String.valueOf(isOnline));
            addLine(msg);
        } catch (Exception exc) {
            show(exc.toString());
        }
    }


    //wymaga ACCESS_FINE_LOCATION  w AndroidManifest
    //oraz dodatkowo ActivityCompat.requestPermissions(...) lub zatwierdzenia ręcznego
    public void go_07_readGPS(View view) {
        LocationManager locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        String msg = String.format("%s is GPS on: %s", sdf.format(new Date()), String.valueOf(isGpsEnabled));
        addLine(msg);
        if(isGpsEnabled)
        {
            try {
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        10000,
                        10,
                        new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                addLine(location.toString());
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {

                            }

                            @Override
                            public void onProviderEnabled(String provider) {

                            }

                            @Override
                            public void onProviderDisabled(String provider) {

                            }
                        }
                );
            }
            catch (SecurityException sexc)
            {
                show(sexc.toString());
            }
        }
    }

    public void go_08_settingsGPS(View view) {
        LocationManager locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        String msg = String.format("%s GPS enabled: %s", sdf.format(new Date()), String.valueOf(isGpsEnabled));
        addLine(msg);
        if(! isGpsEnabled)
        {
            (new AlertDialog.Builder(this))
                    .setMessage("Trzeba właczyć GPS")
                    .setNegativeButton("Anuluj", null)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent =new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    }


    //wymaga play-services-location
    public void go_09_settingsGPS2(View view) {
        LocationRequest locationRequest = LocationRequest.create();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        LocationSettingsRequest mLocationSettingsRequest = builder.build();
        SettingsClient mSettingsClient = LocationServices.getSettingsClient(this);
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ResolvableApiException rae = (ResolvableApiException) e;
                        try {
                            rae.startResolutionForResult(MainActivity.this, 666);
                        } catch (Exception exc) {
                            show(exc.getMessage());
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addLine(String.format("requestCode: %d, resultCode: %d",requestCode, resultCode));
    }
}
