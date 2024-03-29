package com.jackrat.a02_intents_a;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {

    public static String KEY_tekst1 = "text_text_text1";
    public static String KEY_int1 = "int_int_int1";
    public static String KEY_dbl1 = "KEY_dbl1";

    public static int RESCODE_OK = 1;
    public static int RESCODE_CANCEL = 0;


    String t1, t2;
    int i1, i2;
    double d1, d2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        btnDaneClick(null);
    }

    public void btnKoniecClick(View view) {
        finish();
    }

    public void btnDaneClick(View view) {
        //t1 = getIntent().getExtras().getString(KEY_tekst1);
        //i1 = getIntent().getExtras().getInt(KEY_int1);
        //d1 = getIntent().getExtras().getDouble(KEY_dbl1);

        Intent cintent = getIntent();

        //t1 = cintent.getStringExtra(KEY_tekst1);
        //i1 = cintent.getIntExtra(KEY_int1);
        //d1 = cintent.getDoubleExtra(KEY_dbl1);

        Bundle bundle = cintent.getExtras();
        if (bundle != null) {

            t1 = bundle.getString(KEY_tekst1);
            i1 = bundle.getInt(KEY_int1);
            d1 = bundle.getDouble(KEY_dbl1);

            t2 = (String) bundle.get(KEY_tekst1);
            i2 = (int) bundle.get(KEY_int1);
            d2 = (double) bundle.get(KEY_dbl1);

            boolean b = (t1 == t2) && (i1 == i2) && (d1 == d2);
            if (b)
                Toast.makeText(this, "Dane odczytane tożsamo na dwa sposoby!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Dane odczytane NIE tożsamo na dwa sposoby!!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnOKClick(View view) {
        t1 = t1 + " zmodyfikowano";
        i1 = i1 * 100;
        d1 = d1 * 1000;
        Intent intent = new Intent();
        intent.putExtra(KEY_tekst1, t1);
        intent.putExtra(KEY_int1, i1);
        intent.putExtra(KEY_dbl1, d1);
        setResult(RESCODE_OK, intent);
        finish();
    }

    public void btnCancelClick(View view) {
        Intent intent = new Intent();
        intent.putExtra(KEY_tekst1, t1);
        intent.putExtra(KEY_int1, i1);
        intent.putExtra(KEY_dbl1, d1);
        setResult(RESCODE_CANCEL, intent);
        finish();
    }
}
