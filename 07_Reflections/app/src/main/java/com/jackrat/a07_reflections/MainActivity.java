package com.jackrat.a07_reflections;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView tv;

    MyClass mk;
    Object o;
    Class<?> obClass;
    Field[] obFields;
    Method[] obMethods;


    void show(String msg) {
        (new AlertDialog.Builder(MainActivity.this))
                .setTitle("Uwaga!")
                .setMessage(msg)
                .setPositiveButton("OK", null).
                show();
    }


    void addTxt(String s) {
        tv.setText(tv.getText() + "\n----------------\n" + s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        tv.setText(null);
        mk = new MyClass();
        mk.PoleInt = 123;
        mk.PoleStr = "Ala ma kota";
        mk.PolePackMan = getPackageManager();
        o = mk;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "-> obiekt Class");
        menu.add(1, 2, 2, "-> pola");
        menu.add(1, 3, 3, "-> metody");
        menu.add(1, 4, 4, "-> set/get poleInt");
        menu.add(1, 5, 5, "-> set/get poleStr bez listy");
        menu.add(1, 6, 6, "-> metoda proc. MetShowStr(ctx)");
        menu.add(1, 7, 7, "-> metoda funk. MetGetPacNames()");
        menu.add(1, 8, 8, "-> metoda funk. MetMnoPoleInt(int)");
        menu.add(1, 9, 9, "-> metoda PRYWATNA!!!");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case 1:
                    String s = o.getClass().getName();
                    addTxt(s);
                    obClass = Class.forName(s);
                    break;
                case 2:
                    obFields = obClass.getDeclaredFields();
                    StringBuilder sb = new StringBuilder();
                    for (Field f : obFields) {
                        sb.append(f.getName() + "   " + f.getType().toString() + "\n");
                    }
                    addTxt(sb.toString());
                    break;
                case 3:
                    obMethods = obClass.getMethods();
                    StringBuilder sb2 = new StringBuilder();
                    for (Method m : obMethods) {
                        sb2.append(m.getName() + "\n zwraca: " + m.getReturnType().toString());
                        Class<?>[] parTypes = m.getParameterTypes();
                        if (parTypes.length > 0) {
                            sb2.append("\n  parametry:");
                            for (Class<?> pt : parTypes)
                                sb2.append("\n   - " + pt.getName());
                        } else
                            sb2.append("\n bezparametrowa");
                        sb2.append("\n");
                    }
                    addTxt(sb2.toString());
                    break;
                case 4:
                    Field f = obFields[0];
                    f.setInt(o, 666);
                    addTxt(String.format("mk.PoleInt: %d", mk.PoleInt));
                    //lub
                    f.set(o, 999);
                    int ri = f.getInt(o);
                    addTxt(String.format("mk.PoleInt: %d\nread int: %d", mk.PoleInt, ri));
                    break;
                case 5:
                    Field fs = obClass.getDeclaredField("PoleStr");
                    fs.set(o, "Ola psa");
                    String rs = (String) fs.get(o);
                    addTxt(String.format("mk.PoleStr: %s\nreaded str: %s", mk.PoleStr, rs));
                    break;
                case 6:
                    Method m = obClass.getDeclaredMethod("MetShowPoleStr", Context.class);
                    m.invoke(o, this);
                    break;
                case 7:
                    Method mf = obClass.getDeclaredMethod("MetGetPacNames");
                    List<String> lst = (List<String>) mf.invoke(o);
                    StringBuilder sb3 = new StringBuilder();
                    for (String pn : lst) {
                        sb3.append(String.format("    - %s\n", pn));
                    }
                    addTxt(sb3.toString());
                    break;
                case 8:
                    Method m3 = obClass.getDeclaredMethod("MetMnoPoleInt", int.class);
                    int ri2 = (int) m3.invoke(o, 10);
                    addTxt(String.format("ri2: %d", ri2));
                    break;
                case 9:
                    Method m4 = obClass.getDeclaredMethod("metPriv", Context.class);
                    m4.setAccessible(true);
                    m4.invoke(o, this);
                    break;
            }
        } catch (Exception exc) {
            show(exc.getMessage());
        }
        return true;
    }
}
