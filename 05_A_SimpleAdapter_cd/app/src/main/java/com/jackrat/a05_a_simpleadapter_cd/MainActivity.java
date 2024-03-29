package com.jackrat.a05_a_simpleadapter_cd;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    List<ResolveInfo> list;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getPackageManager().getLaunchIntentForPackage(list.get(position).activityInfo.packageName);
                startActivity(intent);
            }
        });

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        list = getPackageManager().queryIntentActivities(intent, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 2, 2, "SimpleAdapter");
        menu.add(1, 3, 3, "SimpleAdapter+PATT");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 2:
                go_2_SimpleAdapter();
                break;
            case 3:
                go_3_SimpleAdapterPATT();
                break;
        }
        return true;
    }



    private void go_2_SimpleAdapter() {
        PackageManager pm = getPackageManager();
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (ResolveInfo ri : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("keyPN", ri.activityInfo.packageName);
            map.put("keyLB", ri.loadLabel(pm));
            map.put("keyIC", ri.loadIcon(pm));
            maps.add(map);
        }


        SimpleAdapter sa = new SimpleAdapter(this,
                maps,
                android.R.layout.simple_list_item_2,
                new String[]{"keyPN", "keyLB"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        lv.setAdapter(sa);

    }



    private void go_3_SimpleAdapterPATT() {
        PackageManager pm = getPackageManager();
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (ResolveInfo ri : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("keyPN", ri.activityInfo.packageName);
            map.put("keyLB", ri.loadLabel(pm));
            map.put("keyIC", ri.loadIcon(pm));
            maps.add(map);
        }


        SimpleAdapter sa = new SimpleAdapter(this,
                maps,
                R.layout.item_pattern,
                new String[]{"keyPN", "keyLB", "keyIC"},
                new int[]{R.id.tv2, R.id.tv1 , R.id.iv }
        );

        sa.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if(view instanceof ImageView && data instanceof Drawable)
                {
                    ((ImageView) view).setImageDrawable((Drawable) data);
                    return true;
                }
                return false;
            }
        });

        lv.setAdapter(sa);
    }

}