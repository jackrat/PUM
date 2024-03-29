package com.jackrat.a04_b_adaptery;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    List<ResolveInfo> lista;
    PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pm = getPackageManager();
        lv = findViewById(R.id.lv);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        lista = pm.queryIntentActivities(intent, 0);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = pm.getLaunchIntentForPackage(lista.get(position).activityInfo.packageName);
                startActivity(intent1);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "ArrayAdapter");
        menu.add(1, 2, 1, "SimpleAdapter");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                go_1_ArrayAdapter();
                break;
            case 2:
                go_2_SimpleAdapter();
                break;
        }
        return true;
    }


    private void go_1_ArrayAdapter() {
        String[] tab = new String[lista.size()];
        int i = 0;
        for (ResolveInfo ri : lista) {
            tab[i] = (String) ri.loadLabel(pm);
            i++;
        }
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tab);
        lv.setAdapter(aa);
    }


    private void go_2_SimpleAdapter() {
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (ResolveInfo ri : lista) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("LB", (String) ri.loadLabel(pm));
            map.put("PN", ri.activityInfo.packageName);
            maps.add(map);
        }
        SimpleAdapter sa = new SimpleAdapter(
                this,
                maps,
                android.R.layout.simple_list_item_2,
                new String[]{"LB", "PN"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        lv.setAdapter(sa);
    }

}
