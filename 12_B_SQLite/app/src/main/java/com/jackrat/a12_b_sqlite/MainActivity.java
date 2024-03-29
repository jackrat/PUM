package com.jackrat.a12_b_sqlite;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

   /* ADB - Android Debug Bridge
         w katalogu Sdk
         cmd
         ... AppData\Local\Android\Sdk\platform-tools
         adb root //emulatory + urz.
         adb -s emulator-5554 shell
             generic_X86 #
         cd /data/data/com.jackrat.a12_b_sqlite/databases

            ls, cd
         sqlite3 kontaktyC.db
         .tables
         select ... ;//uwaga na ; - konieczny


     */


    void info(Exception exc) {
        (new AlertDialog.Builder(this))
                .setMessage(exc.getMessage())
                .setPositiveButton("OK", null)
                .show();
    }

    My_SQLiteMgr dbMgr;
    TextView tv;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        editText = findViewById(R.id.editText);
        try {
            dbMgr = new My_SQLiteMgr(getApplicationContext());
        } catch (Exception exc) {
            dbMgr = null;
            info(new Exception("Aplikacja nie będzie działać..."));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "select");
        menu.add(0, 1, 0, "insert");
        menu.add(0, 2, 0, "update");
        menu.add(0, 3, 0, "delete");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (dbMgr != null)
            switch (item.getItemId()) {
                case 0:
                    sel();
                    break;
                case 1:
                    ins();
                    break;
                case 2:
                    upd();
                    break;
                case 3:
                    del();
                    break;
            }
        return super.onOptionsItemSelected(item);
    }

    private void sel() {
        if (dbMgr != null) {
            Cursor cur = dbMgr.select(null, null);
            boolean ok = cur.moveToFirst();
            if (ok) {
                StringBuilder sb = new StringBuilder();
                do {
                    int lp = cur.getInt(0);
                    String im = cur.getString(1);
                    String naz = cur.getString(2);
                    String tel = cur.getString(3);
                    sb.append(String.format("%d %s %s %s\n", lp, im, naz, tel));
                } while (cur.moveToNext());
                cur.close();
                tv.setText(sb);
            }
        }
    }


    static String[] imiona = new String[]{"Jan", "Piotr", "Katarzyna", "Zofia", "Pafnucy", "Teofil", "Zenobi", "Brunhilda"};
    static String[] nazwiska = new String[]{"Janik", "Piotrzyk", "Katar", "Zofil", "Pafi", "Teo", "Zenbi", "Brunat"};
    Random rnd = new Random();


    private void ins() {
        if (dbMgr != null) {
            String im = imiona[rnd.nextInt(imiona.length)];
            String naz = nazwiska[rnd.nextInt(nazwiska.length)];
            String tel = "";
            int j = 0;
            while (tel.length() < 9) {
                if (j == 3) {
                    j = 0;
                    tel += " ";
                } else{
                    tel += String.valueOf(rnd.nextInt(10));
                }
                j++;
            }
            dbMgr.dodajKontakt(im, naz, tel);
            sel();
        }
    }


    int lp;

    private void upd() {
        try {
            lp = Integer.parseInt(editText.getText().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Podaj tel.:");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String tel = input.getText().toString();
                    dbMgr.ZmienKontakt(lp, tel);
                    sel();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } catch (Exception exc) {
            (new AlertDialog.Builder(this))
                    .setPositiveButton("OK", null)
                    .setTitle("ERROR")
                    .setMessage(exc.getMessage())
                    .show();
        }
    }

    private void del() {
        try {
            if (dbMgr != null) {
                lp = Integer.parseInt(editText.getText().toString());
                dbMgr.UsunKontakt(lp);
                sel();
            }
        } catch (Exception exc) {
            (new AlertDialog.Builder(this))
                    .setPositiveButton("OK", null)
                    .setTitle("ERROR")
                    .setMessage(exc.getMessage())
                    .show();
        }
    }
}
