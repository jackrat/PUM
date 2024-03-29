package com.jackrat.a06_b_perrmissionhelper;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionHelper extends AppCompatActivity {

    protected void show(String title, String msg) {
        (new AlertDialog.Builder(this))
                .setTitle(title).setMessage(msg)
                .setPositiveButton("OK", null).
                show();
    }

    protected void errorShow(Exception ex) {
        show("Error " + String.valueOf(ex), ex.getMessage());
    }


    private class ActionNeededPermission {
        public String name;
        public String[] permisssions;
        public Runnable runnable;
        public int reqCode;

        public ActionNeededPermission(String aName, String[] aPermissions, Runnable aRunnable) {
            name = aName;
            permisssions = aPermissions;
            runnable = aRunnable;
        }
    }

    private Map<String, ActionNeededPermission> actions;
    private List<String> names;

    public PermissionHelper() {
        actions = new HashMap<String, ActionNeededPermission>();
        names = new ArrayList<String>();
    }

    int uniqCode = 0;

    public void registerAcion(String name, String[] permissions, Runnable runnable) {
        ActionNeededPermission act = new ActionNeededPermission(name, permissions, runnable);
        actions.put(act.name, act);
        names.add(act.name);
        act.reqCode = ++uniqCode;
    }

    private ActionNeededPermission findByReqCode(int aReqCode) {
        int n = actions.size();
        for (int i = 0; i < n; i++) {
            ActionNeededPermission act = actions.get(names.get(i));
            if (act.reqCode == aReqCode)
                return act;
        }
        return null;
    }

    protected View rootLay;

    public void TryExecuteAction(String actionName) {
        try {
            final ActionNeededPermission act = actions.get(actionName);
            boolean ok = true;
            for (int i = 0; i < act.permisssions.length; i++) {
                final String permName = act.permisssions[i];
                ok = ActivityCompat.checkSelfPermission(this, permName) == PackageManager.PERMISSION_GRANTED;
                if(!ok)
                {
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this, permName))
                    {
                        Snackbar.make(rootLay, "Bieżąca operacja wymaga uprawnienia " + permName, Snackbar.LENGTH_LONG)
                                .setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ActivityCompat.requestPermissions(
                                                PermissionHelper.this,
                                                new String[]{permName},
                                                act.reqCode);
                                    }
                                })
                                .show();
                    }
                    else
                        ActivityCompat.requestPermissions(this, new String[]{permName}, act.reqCode);
                }
            }
            if (ok)
                act.runnable.run();
        } catch (Exception exc) {
            errorShow(exc);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ActionNeededPermission act = findByReqCode(requestCode);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Snackbar.make(rootLay, "Uprawnienie " + permissions[0]+" przydzielone", Snackbar.LENGTH_LONG).show();
            TryExecuteAction(act.name);
        }
        else
            Snackbar.make(rootLay, "Nie udzielono uprawnienia " + permissions[0], Snackbar.LENGTH_LONG).show();
    }
}


