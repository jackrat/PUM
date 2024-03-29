package com.jackrat.a14_a_canvas_drawing;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawmenu, menu);
        menu.findItem(R.id.bitmap_menu_item).setIntent(new Intent(this, DrawBitmapActivity.class));
        menu.findItem(R.id.gradient_menu_item).setIntent(new Intent(this, DrawGradientActivity.class));
        menu.findItem(R.id.shape_menu_item).setIntent(new Intent(this, DrawShapeActivity.class));
        menu.findItem(R.id.text_menu_item).setIntent(new Intent(this, DrawTextActivity.class));
        menu.findItem(R.id.font_menu_item).setIntent(new Intent(this, DrawCustomFontActivity.class));
        menu.findItem(R.id.chess_menu_item).setIntent(new Intent(this, ChessActivity.class));
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(item.getIntent());
        super.onOptionsItemSelected(item);
        return true;
    }
}