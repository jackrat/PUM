package com.jackrat.a03_intents_b;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "Pakiety A");
        menu.add(0, 2, 2, "Pakiety B");
        menu.add(0, 3, 3, "Kalendarz");
        menu.add(0, 4, 4, "Mail");
        menu.add(0, 5, 5, "Kontakty view");
        menu.add(0, 6, 6, "Kontakty pick");
        menu.add(0, 7, 7, "Kontakty tel pick");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                go_1_PakietyA();
                break;
            case 2:
                go_2_PakietyB();
                break;
            case 3:
                go_3_Kalendarz();
                break;
            case 4:
                go_4_Mail();
                break;
            case 5:
                go_5_KontaktyView();
                break;
            case 6:
                go_6_KontaktyPick();
                break;
            case 7:
                go_7_KontaktyTelPick();
                break;
        }
        return true;
    }


    private void go_1_PakietyA() {
        PackageManager pm = getPackageManager();
        List<PackageInfo> lst = pm.getInstalledPackages(0);
        StringBuilder sb = new StringBuilder();
        for (PackageInfo pi : lst) {
            sb.append(pi.packageName + "\n");
        }
        tv.setText(sb.toString());
        Toast.makeText(this, "Pakietów: " + String.valueOf(lst.size()), Toast.LENGTH_SHORT).show();
    }

    private void go_2_PakietyB() {
        PackageManager pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> lst = pm.queryIntentActivities(intent, 0);
        StringBuilder sb = new StringBuilder();
        for (ResolveInfo ri : lst) {
            sb.append(ri.loadLabel(pm));
            sb.append(" - " + ri.activityInfo.packageName + "\n");
        }
        tv.setText(sb.toString());
        Toast.makeText(this, "Pakietów: " + String.valueOf(lst.size()), Toast.LENGTH_SHORT).show();
    }


    private void go_3_Kalendarz() {
        Intent intent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);

        Calendar t1 = Calendar.getInstance();
        t1.set(2020, 5, 1, 10, 0);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, t1.getTimeInMillis());

        Calendar t2 = Calendar.getInstance();
        t1.set(2020, 5, 1, 13, 30);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, t1.getTimeInMillis());

        intent.putExtra(CalendarContract.Events.TITLE, "Konferecja zarządu");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Hotel Kmicic");

        startActivity(intent);
    }

    private void go_4_Mail() {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain"); //typ MIME
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"jackrat@el.pcz.czest.pl", "jackrat@interia.pl"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Wiadomość testowa");
            intent.putExtra(Intent.EXTRA_TEXT, "Treść wiadomości");

            File f = new File("/storage/emulated/0/Music/barmanek.mp3");
            Uri u = Uri.fromFile(f); // to zadziała do API Level 23 maksymalnie
            intent.putExtra(Intent.EXTRA_STREAM, u);

            startActivity(intent);
        } catch (Exception exc) {
            (new AlertDialog.Builder(this))
                    .setMessage(exc.getMessage())
                    .show();
        }
    }


    private void go_5_KontaktyView() {
        Intent intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);
        startActivity(intent);
    }


    private void go_6_KontaktyPick() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 777);
    }


    private void go_7_KontaktyTelPick() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, 888);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 777:
                if (resultCode == RESULT_OK) {
                    ContentResolver contentResolver = getContentResolver();
                    Uri u = data.getData();
                    Cursor curs = contentResolver.query(u, null, null, null, null);
                    curs.moveToFirst();
                    int nk = curs.getColumnCount();
                    int nr = curs.getCount();
                    Toast.makeText(this, "nr : " + String.valueOf(nr), Toast.LENGTH_SHORT).show();
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < nk; j++) {
                        sb.append(curs.getColumnName(j));
                        sb.append(" : " + curs.getString(j) + "\n");
                    }
                    tv.setText(sb.toString());
                }
                break;
            case 888:
                Uri u2 = data.getData();
                ContentResolver cr = getContentResolver();
                String[] proj = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cur2 = cr.query(u2, proj, null, null, null);
                cur2.moveToFirst();
                String s = cur2.getString(0);
                tv.setText(s);
                break;
        }
    }


}

