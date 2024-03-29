package com.jackrat.sms_send_txt_file_wr;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {
    EditText telno, txt;


    private void Catch(Exception exc) {
        (new AlertDialog.Builder(this))
                .setTitle("Błąd")
                .setMessage(exc.getMessage())
                .setPositiveButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        telno = findViewById(R.id.editText1);
        txt = findViewById(R.id.editText2);
    }

    public void sendClick(View view) {

        try {
            String no = telno.getText().toString();
            String msg = txt.getText().toString();

            //Getting intent and PendingIntent instance
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

            //Get the SmsManager instance and call the sendTextMessage method to send message
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(no, null, msg, pi, null);

            Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception exc) {
            Catch(exc);
        }
    }


    public void click2(View view) {
        try {
            Uri contactUri = ContactsContract.Contacts.CONTENT_URI;
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int n = cursor.getColumnCount();
                do {
                    Log.d("CTN", String.valueOf(cursor.getPosition()) + ")======================================================================================================");
                    for (int i = 0; i < n; i++) {
                        Log.d("CTN", String.format("%s: %s", cursor.getColumnName(i), cursor.getString(i)));
                    }
                }
                while (cursor.moveToNext());
            }
        } catch (Exception exc) {
            Catch(exc);
        }
    }

    public void click3(View view) {

        String filename = "plik.txt";
        String fileContents = "Hello world!\nWitaj świecie!\nGuten tag welt!";

        //WRITE
        try (FileOutputStream fos = MainActivity.this.openFileOutput(filename, Context.MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
        } catch (Exception exc) {
            Catch(exc);
        }

        //READ
        try {
            File f = new File(getFilesDir(), filename);
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            br.close();
            Log.d("CTN", sb.toString());
        } catch (Exception exc) {
            Catch(exc);
        }
    }

    public void click4(View view) {




        String[] dirs = new String[]{
                Environment.DIRECTORY_MUSIC,
                Environment.DIRECTORY_PODCASTS,
                Environment.DIRECTORY_RINGTONES,
                Environment.DIRECTORY_ALARMS,
                Environment.DIRECTORY_NOTIFICATIONS,
                Environment.DIRECTORY_PICTURES,
                Environment.DIRECTORY_MOVIES,
                Environment.DIRECTORY_DOWNLOADS,
                Environment.DIRECTORY_DCIM
        };


        for (int i = 1; i <= dirs.length; i++) {

            String filename = "plik_%d.txt";
            String fileContents = "Hello big world!\nWitaj wielki świecie!\n!!!!!!!!!!\n??????????????";

            File pat2 = Environment.getExternalStoragePublicDirectory(dirs[i - 1]);

            //WRITE
            try {
                //File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), filename);
                File f = new File(pat2, String.format(filename, i));
                try (FileOutputStream fos = new FileOutputStream(f)) {
                    fos.write(fileContents.getBytes());
                } catch (Exception exc) {
                    Catch(exc);
                }
            } catch (Exception exc) {
                Catch(exc);
            }

            //READ
            try {
                //File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), filename);
                File f = new File(pat2, String.format(filename, i));
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append('\n');
                }
                br.close();
                Log.d("CTN", sb.toString());
            } catch (Exception exc) {
                Catch(exc);
            }


        }
    }
}
