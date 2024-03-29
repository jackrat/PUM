package com.jackrat.a06_b_perrmissionhelper;

import android.Manifest;
import android.database.Cursor;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.TextureView;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends PermissionHelper {
    ListView lv;
    TextureView txv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        txv = findViewById(R.id.txv);
        rootLay = findViewById(R.id.root);

        registerAcion("CAM",
                new String[]{Manifest.permission.CAMERA},
                new Runnable() {
                    @Override
                    public void run() {
                        showCamera();
                    }
                });

        registerAcion("MP3",
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                new Runnable() {
                    @Override
                    public void run() {
                        playMP3();
                    }
                });

        registerAcion("CON",
                new String[]{Manifest.permission.READ_CONTACTS},
                new Runnable() {
                    @Override
                    public void run() {
                        readContacts();
                    }
                });

    }

    public void go_showCamera_1(View view) {
        showCamera();
    }

    public void go_playMp3_1(View view) {
        playMP3();
    }

    public void go_readContacts1(View view) {
        readContacts();
    }

    public void go_showCamera_2(View view) {
        TryExecuteAction("CAM");
    }

    public void go_playMp3_2(View view) {
        TryExecuteAction("MP3");
    }

    public void go_readContacts2(View view) {
        TryExecuteAction("CON");
    }

    private void showCamera() {
        try {
            Camera camera = Camera.open();
            camera.setDisplayOrientation(90);
            camera.setPreviewTexture(txv.getSurfaceTexture());
            camera.startPreview();
        } catch (Exception exc) {
            errorShow(exc);
        }
    }


    MediaPlayer mp;

    private void playMP3() {
        try {
            if (mp != null) {
                mp.stop();
                mp = null;
            } else{
                mp = new MediaPlayer();
                mp.setDataSource("/sdcard/Music/barmanek.mp3");
                mp.prepare();
                mp.start();
            }
        } catch (Exception exc) {
            errorShow(exc);
        }
    }


    private void readContacts() {
        try {
            Cursor crs = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                    new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},
                    null,
                    null,
                    null
            );
            if (crs.moveToFirst()) {
                int rowCount = crs.getCount();
                int col = crs.getColumnCount();
                int colID = crs.getColumnIndex(ContactsContract.Contacts._ID);
                int colDN = crs.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
                do {
                    String[] el = new String[]{crs.getString(colID), crs.getString(colDN)};
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("ID", el[0]);
                    map.put("DN", el[1]);
                    maps.add(map);
                }
                while (crs.moveToNext());
                lv.setAdapter(
                        new SimpleAdapter(this,
                                maps,
                                android.R.layout.simple_list_item_2,
                                new String[]{"ID", "DN"},
                                new int[]{android.R.id.text1, android.R.id.text2}));
            }
        } catch (Exception exc) {
            errorShow(exc);
        }
    }

}
