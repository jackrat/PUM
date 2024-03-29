package com.jackrat.a02_intents_a;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText et1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = findViewById(R.id.et1);
    }

    public void btnWWWClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://212.87.228.200:3000"));
        startActivity(intent);
    }

    public void btnSubClick(View view) {
        Intent intent = new Intent(this, SubActivity.class);
        startActivity(intent);
    }

    public void btnDaneSubClick(View view) {
        Intent intent = new Intent(this, SubActivity.class);
        intent.putExtra(SubActivity.KEY_tekst1, et1.getText().toString());
        intent.putExtra(SubActivity.KEY_int1, 123);
        intent.putExtra(SubActivity.KEY_dbl1, Math.PI);
        startActivity(intent);
    }

    public void btnDaneSubReturnClick(View view) {
        Intent intent = new Intent(this, SubActivity.class);
        intent.putExtra(SubActivity.KEY_tekst1, et1.getText().toString());
        intent.putExtra(SubActivity.KEY_int1, 123);
        intent.putExtra(SubActivity.KEY_dbl1, Math.PI);
        startActivityForResult(intent, 666);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 666:
                String msg;
                String s;
                int i;
                double d;
                if (data != null) {
                    Bundle bndl = data.getExtras();
                    s = bndl.getString(SubActivity.KEY_tekst1);
                    i = bndl.getInt(SubActivity.KEY_int1);
                    d = bndl.getDouble(SubActivity.KEY_dbl1);
                    msg = String.format("Dane to:\ns : %s\ni: %d\nd: %f", s, i, d);
                } else
                    msg = "BRAK DANYCH ZWRACANYCH!";

                if (resultCode == SubActivity.RESCODE_OK) {
                    msg = "resultCode to OK\n" + msg;
                } else{
                    msg = "resultCode to Cancel\n" + msg;
                }

                (new AlertDialog.Builder(this))
                        .setMessage(msg)
                        .show();
                break;
            //...............
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "000000000000000");
        menu.add(0, 1, 1, "111111111111111");
        menu.add(0, 2, 3, "222222222222222");
        menu.add(0, 3, 2, "3333333333333");

        menu.add(0, 4, 4, "tel");
        menu.add(0, 5, 5, "geo");
        menu.add(0, 6, 6, "calc");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
            case 1:
            case 2:
            case 3:
                Toast.makeText(this, "Wybrano pozycję " + String.valueOf(item.getItemId()), Toast.LENGTH_LONG).show();
                break;

            case 4:
                goTel();
                break;
            case 5:
                goGeo();
                break;

            case 6:
                goCalc();
                break;
        }

        return true;
    }


    private void goTel() {
        Intent intentTel = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:666123666"));
        startActivity(intentTel);
    }

    private void goGeo() {
        //Intent intentGeo = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:50,19?z=7"));
        Intent intentGeo = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=Częstochowa"));
        startActivity(intentGeo);
    }

    private void goCalc() {
        PackageManager pm = getPackageManager();
        Intent intentPM = pm.getLaunchIntentForPackage("com.android.calculator2");
        startActivity(intentPM);
    }
}
