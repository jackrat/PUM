package com.jackrat.a06_a_permisje;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    View rootLay;
    ImageView iv;
    TextureView txv;

    void Info(Exception exc) {
        (new AlertDialog.Builder(this))
                .setPositiveButton("OK", null)
                .setTitle("Błąd")
                .setMessage(exc.getMessage())
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.iv);
        rootLay = findViewById(R.id.rootLay);
        txv = findViewById(R.id.txv);
    }public void btnCamStartClick(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            cameraStart();
        else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
                Snackbar.make(rootLay, "Zgoda na Manifest.permission.CAMERA jest konieczna żeby ...", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 666);
                            }
                        })
                        .show();
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 666);
        }
    }

    public void btnCamStopClick(View view) {
        cameraStop();
    }

    public void btnGetFileClick(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            getFile();
        else{
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
                Snackbar.make(rootLay, "Zgoda na Manifest.permission.READ_EXTERNAL_STORAGE jest konieczna żeby ...", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 777);
                            }
                        })
                        .show();
            else
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 777);
        }
    }

    Camera camera = null;

    public void cameraStart() {
        if (camera == null) {
            try {
                camera = Camera.open();
                camera.setDisplayOrientation(90);
                camera.setPreviewTexture(txv.getSurfaceTexture());
                camera.startPreview();
            } catch (Exception exc) {
                Info(exc);
            }
        }
    }

    public void cameraStop() {
        if (camera != null) {
            try {
                camera.stopPreview();
                camera.release();
                camera = null;
                iv.setImageBitmap(txv.getBitmap());
            } catch (Exception exc) {
                Info(exc);
            }
        }
    }

    private void getFile() {
        try {
            Bitmap bmp = BitmapFactory.decodeFile("/mnt/sdcard/Pictures/logo_700.jpg");
            iv.setImageBitmap(bmp);
        } catch (Exception exc) {
            Info(exc);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            if (requestCode == 666) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    cameraStart();
                else
                    Snackbar.make(rootLay, "Permisji Manifest.permission.CAMERA nie przydzielono!", Snackbar.LENGTH_LONG).show();
            } else if (requestCode == 777) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getFile();
                else
                    Snackbar.make(rootLay, "Permisji Manifest.permission.READ_EXTERNAL_STORAGE nie przydzielono!", Snackbar.LENGTH_LONG).show();
            }
        } catch (Exception exc) {
            Info(exc);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}