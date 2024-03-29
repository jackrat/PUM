package com.jackrat.a07_reflections;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyClass {
    public int PoleInt;
    public String PoleStr;
    public PackageManager PolePackMan;

    public void MetShowPoleStr(Context ctx) {
        Toast.makeText(ctx, PoleStr, Toast.LENGTH_LONG).show();
    }

    public int MetMnoPoleInt(int b) {
        return PoleInt * b;
    }

    public List<String> MetGetPacNames() {
        List<PackageInfo> lst = PolePackMan.getInstalledPackages(0);
        ArrayList<String> res = new ArrayList<String>();
        for (PackageInfo pi : lst) {
            res.add(pi.packageName);
        }
        return res;
    }

    private void metPriv(Context ctx)
    {
        Toast.makeText(ctx, "TO JEST METODA PRYWATNA!!", Toast.LENGTH_LONG).show();
    }

}
