package com.jackrat.a09_fileprovider;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;




public class MainActivity extends AppCompatActivity {


    TextView tv1;
    ImageView iv1;


    void Info(Exception exc) {
        (new AlertDialog.Builder(this))
                .setPositiveButton("OK", null)
                .setTitle(exc.getMessage())
                .setMessage(exc.getMessage())
                .show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv1);
        iv1 = findViewById(R.id.iv1);

        //To dodałem już po nagraniu filmu, dla wygody, ale niezgodnie z zalecanym przez Google scenariuszem
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "DIR-S");
        menu.add(0, 1, 1, "Photo to PRV");
        menu.add(0, 2, 2, "Photo to PUB");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                on_0_DirsClick();
                break;
            case 1:
                on_1_PrvPhotoClick();
                break;
            case 2:
                on_2_PubPhotoClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void on_0_DirsClick() {
        try {
            StringBuilder sb = new StringBuilder();
            File f = getFilesDir();
            sb.append("getFilesDir()");
            sb.append("\n" + f.getPath());
            sb.append("\n" + f.getAbsolutePath());
            sb.append("\n" + f.getCanonicalPath());

            f = Environment.getExternalStorageDirectory();
            sb.append("\n\ngetExternalStorageDirectory()");
            sb.append("\n" + f.getPath());
            sb.append("\n" + f.getAbsolutePath());
            sb.append("\n" + f.getCanonicalPath());

            f = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            sb.append("\n\ngetExternalStorageDirectory()");
            sb.append("\n" + f.getPath());
            sb.append("\n" + f.getAbsolutePath());
            sb.append("\n" + f.getCanonicalPath());


            tv1.setText(sb.toString());
        } catch (Exception exc) {
            Info(exc);
        }
    }


    //problem uri i dostępu do plików
    // proteza 1: zmiana targetSdkVersion < 24 (poniżej Nougat'a) w pliku build.gradle (Module: app)
    // proteza 2: wymuszenie policy w maszynie wirtualnej:
    //     StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
    //     StrictMode.setVmPolicy(builder.build());
    // właściwe rozwiązanie - użycie FileProvider'a (złożone - potrzebne zmiany: manifest + plik xml/xml_paths.xml)

    private void on_1_PrvPhotoClick() {
        try {
            File f = new File(getFilesDir(), "foto_prv.jpg");
            Uri u = FileProvider.getUriForFile(this, "com.jackrat.a09_fileprovider", f);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
            startActivityForResult(intent, 1666);

        } catch (Exception exc) {
            Info(exc);
        }
    }

    private void on_2_PubPhotoClick() {
        try {
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "foto_pub.jpg");
            Uri u = FileProvider.getUriForFile(this, "com.jackrat.a09_fileprovider", f);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
            startActivityForResult(intent, 1777);
        } catch (Exception exc) {
            Info(exc);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        File f;
        Bitmap bmp;
        try {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case 1666:
                        f = new File(getFilesDir(), "foto_prv.jpg");
                        bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
                        iv1.setImageBitmap(bmp);
                        break;
                    case 1777:
                        f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "foto_pub.jpg");
                        bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
                        iv1.setImageBitmap(bmp);
                        break;
                }
            } else
                Info(new Exception("bad resultCode"));
        } catch (Exception exc) {
            Info(exc);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
