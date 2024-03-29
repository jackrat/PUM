package com.jackrat.a01_android_ui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv1;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    ImageView iv1;
    View clay;
    Button btnP;
    EditText etA;
    EditText etB;
    EditText etC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv1);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btnP = findViewById(R.id.btnP);
        iv1 = findViewById(R.id.iv1);
        clay = findViewById(R.id.clay);
        etA = findViewById(R.id.etA);
        etB = findViewById(R.id.etB);
        etC = findViewById(R.id.etC);


        tv1.setText("Ala ma kota");
        tv1.setTextColor(Color.rgb(255, 0, 0));
        tv1.setTextSize(50);
        tv1.setTypeface(Typeface.MONOSPACE);
        tv1.setTypeface(Typeface.create("serif", Typeface.ITALIC));

        btn1.setBackgroundColor(Color.parseColor("magenta"));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setX(0);
                tv1.setY(0);
            }
        });


        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        btnP.setOnClickListener(this);
    }


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void btn2Click(View view) {
        btn2.setText(sdf.format(new Date()));
    }


    @Override
    public void onClick(View v) {
        if (v == btn3) {
            btn3.setText(sdf.format(new Date()));
        } else if (v == btn4) {
            btn4.setText("BTN4  " + sdf.format(new Date()));
            iv1.setImageResource(R.drawable.nnn);
        } else if (v == btnP) {
            LiczPierwiastki();
        }
    }


    public void showToast(View view) {
        Toast.makeText(this, "To jest komunikat wyświetlany przez Toast", Toast.LENGTH_LONG).show();
    }

    public void showAlertDialog(View view) {
        (new AlertDialog.Builder(this))
                .setMessage("To jest komunikat z AlertDialog")
                .setTitle("To jest tytuł")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Wybrano OK!", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Wybrano Anuluj!", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    public void showSnackBar(View view) {
        Snackbar.make(clay, "To jest komunikat ze Snackbar", Snackbar.LENGTH_LONG) // Snackbar.LENGTH_INDEFINITE - na stałe
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Wybrano OK!", Toast.LENGTH_LONG).show();
                    }
                })
                /*
                .setAction("Anuluj", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Wybrano OK!", Toast.LENGTH_LONG).show();
                    }
                })*/
                .show();
    }


    private void LiczPierwiastki() {
        Pierwiastki p = new Pierwiastki();
        p.A = Double.parseDouble(etA.getText().toString());
        p.B = Double.parseDouble(etB.getText().toString());
        p.C = Double.parseDouble(etC.getText().toString());
        p.Oblicz();
        String msg;
        if (p.Wynik.length == 0)
            msg = "Brak rozwiązań";
        else if (p.Wynik.length == 1)
            msg = String.format("x0 = %f", p.Wynik[0]);
        else
            msg = String.format("x1 = %f\nx2 = %f", p.Wynik[0], p.Wynik[1]);
        (new AlertDialog.Builder(this))
                .setTitle("Wynik:")
                .setMessage(msg)
                .setPositiveButton("OK",null)
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }

}
