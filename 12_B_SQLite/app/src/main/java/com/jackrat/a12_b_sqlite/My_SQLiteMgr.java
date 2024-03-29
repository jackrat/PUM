package com.jackrat.a12_b_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class My_SQLiteMgr extends SQLiteOpenHelper {

    Context context;


    public My_SQLiteMgr(Context context) {
        super(context, "KontaktyC.db", null, 1);
        this.context = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("SQLL", "onCreate:!! ");
        db.execSQL(
                "create table telefony(lp integer primary key autoincrement,"+
                        "imie text," +
                        "nazwisko text," +
                        "telefon text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i = oldVersion; i < newVersion; i++)
            switch (i)
            {
                case 1:
                    Toast.makeText(context, "migracja 1 -> 2", Toast.LENGTH_SHORT).show();
                    //...
                    break;
                case 2:
                    Toast.makeText(context, "migracja 2 -> 3", Toast.LENGTH_SHORT).show();
                    //...
                    break;
                case 3:
                    Toast.makeText(context, "migracja 3 -> 4", Toast.LENGTH_SHORT).show();
                    //...
                    break;
                case 4:
                    Toast.makeText(context, "migracja 3 -> 4", Toast.LENGTH_SHORT).show();
                    //...
                    break;
                case 5:
                    Toast.makeText(context, "migracja 5 -> 6", Toast.LENGTH_SHORT).show();
                    //..
                    break;
                case 6:
                    Toast.makeText(context, "migracja 6 -> 7", Toast.LENGTH_SHORT).show();
                    //..
                    break;
            }

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //super.onDowngrade(db, oldVersion, newVersion); // to robi celowy wyjątek!!
    }




    public void dodajKontakt(String imie, String nazw, String telefon)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("imie", imie);
        wartosci.put("nazwisko", nazw);
        wartosci.put("telefon", telefon);
        db.insertOrThrow("telefony", null, wartosci);
    }


    public void ZmienKontakt(int LP, String telefon)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("telefon", telefon);
        db.update("telefony", cv,"lp=?",new String[]{ String.valueOf(LP)});
    }


    public Cursor select(String selection, String[] selectionsArgs)
    {
        String[] kolumny = {"lp", "imie", "nazwisko", "telefon"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.query("telefony", kolumny,selection, selectionsArgs,null,null,null);
        //Cursor kursor = db.rawQuery("select * from telefony where lp=?", new String[]{"3"});
        //Cursor kursor = db.rawQuery("select * from telefony", null);
        return kursor;
    }

    public void UsunKontakt(int LP)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("telefony", "lp=?", new String[]{String.valueOf(LP)});
    }
}
