package com.jackrat.a05_b_baseadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    List<ResolveInfo> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        list = getPackageManager().queryIntentActivities(
                (new Intent(Intent.ACTION_MAIN))
                        .addCategory(Intent.CATEGORY_LAUNCHER), 0);


        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = getPackageManager().getLaunchIntentForPackage(list.get(position).activityInfo.packageName);
                        startActivity(intent);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 3, 3, "BaseAdapter");
        menu.add(0, 4, 4, "BaseAdapter - pattern");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 3://BaseAdapter
                on_3_BaseAdapter();
                break;
            case 4: //BaseAdapter - pattern
                on_4_BaseAdapterPATT();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void on_3_BaseAdapter() {
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ResolveInfo ri = list.get(position);
                views t;
                if (convertView == null) {

                    LinearLayout ll = new LinearLayout(MainActivity.this);
                    ll.setOrientation(LinearLayout.VERTICAL);
                    convertView = ll;
                    t = new views();
                    convertView.setTag(t);

                    t.iv = new ImageView(MainActivity.this);
                    ll.addView(t.iv);

                    t.tv1 = new TextView(MainActivity.this);
                    ll.addView(t.tv1);

                    t.tv2 = new TextView(MainActivity.this);
                    ll.addView(t.tv2);

                } else{
                    t = (views) convertView.getTag();
                }
                t.iv.setImageDrawable(ri.loadIcon(getPackageManager()));
                t.tv1.setText(ri.loadLabel(getPackageManager()));
                t.tv2.setText(ri.activityInfo.packageName);

                return convertView;
            }
        });

    }

    private void on_4_BaseAdapterPATT() {
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ResolveInfo ri = list.get(position);
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_pattern, null);
                    views t = new views();
                    t.iv = convertView.findViewById(R.id.iv1);
                    t.tv1 = convertView.findViewById(R.id.tv1);
                    t.tv2 = convertView.findViewById(R.id.tv2);
                    convertView.setTag(t);
                }
                views t = (views) convertView.getTag();

                t.iv.setImageDrawable(ri.loadIcon(getPackageManager()));
                t.tv1.setText(ri.loadLabel(getPackageManager()));
                t.tv2.setText(ri.activityInfo.packageName);

                return convertView;
            }
        });

    }

    class views {
        public ImageView iv;
        public TextView tv1;
        public TextView tv2;
    }

}