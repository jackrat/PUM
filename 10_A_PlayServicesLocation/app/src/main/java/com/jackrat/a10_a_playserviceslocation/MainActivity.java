package com.jackrat.a10_a_playserviceslocation;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;



public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient locationProviderClient;
    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }


    
    public void go_getLastLocation(View view) {
        locationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null)
                            Log.d("LOC11", location.toString());
                        else
                            Log.d("LOC11", "location == null");
                    }
                });
    }


    public void go_requestLocationUpdates(View view) {
        if (locationCallback == null) {
            final LocationRequest locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(2000);

            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    Log.d("LOC11", "LOOPER: " + location.toString());
                }
            };

            locationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()
            );
        }
    }


    public void go_removeLocationUpdates(View view) {
        if (locationCallback != null) {
            locationProviderClient.removeLocationUpdates(locationCallback);
            locationCallback = null;
        }
    }
}
