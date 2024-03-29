package com.jackrat.a04_a_intents_getviewopen;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    ImageView iv;
    VideoView vv;


    void info(String msg) {
        (new AlertDialog.Builder(this))
                .setMessage(msg)
                .setTitle("Uwaga!")
                .setPositiveButton("OK", null)
                .show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.iv);
        vv = findViewById(R.id.vv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "jpg_view");
        menu.add(1, 2, 2, "jpg_get");
        menu.add(1, 3, 3, "jpg_open");

        menu.add(1, 4, 4, "mp3_view");
        menu.add(1, 5, 5, "mp3_get");
        menu.add(1, 6, 6, "mp3_open");

        menu.add(1, 7, 7, "mp4_view");
        menu.add(1, 8, 8, "mp4_get");
        menu.add(1, 9, 9, "mp4_open");

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                go_1_jpg_view();
                break;
            case 2:
                go_2_jpg_get();
                break;
            case 3:
                go_3_jpg_open();
                break;

            case 4:
                go_4_mp3_view();
                break;
            case 5:
                go_6_mp3_get();
                break;
            case 6:
                go_5_mp3_get();
                break;

            case 7:
                go_7_mp4_view();
                break;
            case 8:
                go_8_mp4_get();
                break;
            case 9:
                go_9_mp4_open();
                break;
        }
        return true;
    }


    private void go_1_jpg_view() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("image/*");
        startActivity(intent);
    }

    private void go_2_jpg_get() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        //startActivity(intent); // brak rezultatu
        startActivityForResult(intent, 2);
    }

    private void go_3_jpg_open() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        //startActivity(intent); // brak rezultatu
        startActivityForResult(intent, 3);
    }


    private void go_4_mp3_view() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("audio/*");
            startActivity(intent);
        } catch (Exception exc) {
            info(exc.getMessage());
        }
    }

    private void go_5_mp3_get() {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("audio/*");
            //startActivity(intent); namiastka funk.
            startActivityForResult(intent, 5);
        } catch (Exception exc) {
            info(exc.getMessage());
        }
    }

    private void go_6_mp3_get() {
        try {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("audio/*");
            //startActivity(intent); namiastka funk.
            startActivityForResult(intent, 6);
        } catch (Exception exc) {
            info(exc.getMessage());
        }
    }


    private void go_7_mp4_view() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("video/*");
            startActivity(intent);
        } catch (Exception exc) {
            info(exc.getMessage());
        }
    }

    private void go_8_mp4_get() {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("video/*");
            startActivityForResult(intent, 8);
        } catch (Exception exc) {
            info(exc.getMessage());
        }
    }

    private void go_9_mp4_open() {
        try {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("video/*");
            startActivityForResult(intent, 9);
        } catch (Exception exc) {
            info(exc.getMessage());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 2:
                case 3:
                    openJpgByUri(data.getData());
                    break;
                case 5:
                case 6:
                    openMp3ByUri(data.getData());
                    break;
                case 8:
                case 9:
                    openMp4ByUri(data.getData());
                    break;
            }
        }
    }


    private void openJpgByUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            iv.setImageBitmap(bmp);
        } catch (Exception exc) {
            info(exc.getMessage());
        }
    }


    private void openMp3ByUri(Uri data) {
        MediaPlayer mp = MediaPlayer.create(this, data);
        mp.start();
    }


    private void openMp4ByUri(Uri data) {
        vv.setVideoURI(data);
        vv.start();
    }

}

