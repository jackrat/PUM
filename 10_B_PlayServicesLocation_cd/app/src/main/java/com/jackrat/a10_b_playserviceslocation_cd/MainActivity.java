package com.jackrat.a10_b_playserviceslocation_cd;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient locationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
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


    private void startRequestLocationUpdates() {
        if (locationCallback == null) {
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


    public void go_requestLocationUpdates(View view) {
        if (locationCallback == null) {

            // LocationRequest
            locationRequest = new LocationRequest();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(2000);

            // SettingsClient && checkLocationSettings()
            // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
            SettingsClient settingsClient = LocationServices.getSettingsClient(this);

            // LocationSettingsRequest
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
            builder.addLocationRequest(locationRequest);
            LocationSettingsRequest locationSettingsRequest = builder.build();

            // wysłanie żądania sprawdzenia ustawień lokalizacji
            settingsClient.checkLocationSettings(locationSettingsRequest)
                    //żeby demo addOnFailureListener zadziałało wyłączyć ręcznie GPS
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            int statusCode = ((ApiException) e).getStatusCode();
                            switch (statusCode) {
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    Log.d("LOC11", "checkLocationSettings err RESOLUTION_REQUIRED error " + e.toString());
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    try {
                                        rae.startResolutionForResult(MainActivity.this, 666);
                                    } catch (IntentSender.SendIntentException ex) {
                                        Log.d("LOC11", "checkLocationSettings err RESOLUTION_REQUIRED error " + ex.toString());
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    Log.d("LOC11", "checkLocationSettings err SETTINGS_CHANGE_UNAVAILABLE " + e.toString());
                                    break;
                                default:
                                    Log.d("LOC11", "checkLocationSettings err ... " + e.toString());
                                    break;
                            }
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                        @Override
                        public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                            Log.d("LOC11", "checkLocationSettings Complete");
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                        @Override
                        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                            Log.d("LOC11", "checkLocationSettings Success");
                            startRequestLocationUpdates();
                        }
                    })
                    .addOnCanceledListener(new OnCanceledListener() {
                        @Override
                        public void onCanceled() {
                            Log.d("LOC11", "checkLocationSettings Canceled");
                        }
                    });
        }
    }


    public void go_removeLocationUpdates(View view) {
        if (locationCallback != null) {
            locationProviderClient.removeLocationUpdates(locationCallback);
            locationCallback = null;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("LOC11", "RequestPermissionsResult 666 PERMISSION_GRANTED");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 666) {
            if (resultCode == Activity.RESULT_OK)
                Log.d("LOC11", "startResolutionForResult OK");
            else
                Log.d("LOC11", "startResolutionForResult NOT_OK");
        } else
            super.onActivityResult(requestCode, resultCode, data);
    }
}
