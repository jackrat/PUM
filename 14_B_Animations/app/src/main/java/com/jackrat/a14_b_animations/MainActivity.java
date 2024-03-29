package com.jackrat.a14_b_animations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    String[] menus = new String[]{
            "01 Animacja przejść - element",
            "02 Animacja przejść - układ",
            "03 Animacja poklatkowa - tło ImageView",
            "04 Animacja poklatkowa - ImageSwitcher" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       GridView gv = findViewById(R.id.gv);
       gv.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, menus));
       gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (position)
               {
                   case 0: startActivity(new Intent(MainActivity.this, TweenActivity.class));break;
                   case 1:startActivity(new Intent(MainActivity.this, TweenLayoutActivity.class));break;
                   case 2:startActivity(new Intent(MainActivity.this, FrameAnimationActivity.class));break;
                   case 3:startActivity(new Intent(MainActivity.this, FrameAnimationActivity2.class));break;
               }
           }
       });

    }

}