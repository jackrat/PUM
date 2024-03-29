package com.jackrat.a13_shapes;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv = findViewById(R.id.iv);

        String[] shapes = new String[]{
                "Linia",
                "Owal",
                "Prostokąt",
                "Ścieżka",
                "Łuk",
                "Prostokąt 'rounded'",
                "Prostokąt 'stroked'",
                "Ścieżka 2",
                "Ścieżka 3"};

        GridView gv = findViewById(R.id.gv);
        gv.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, shapes));

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Draw_00_Line();
                        break;
                    case 1:
                        Draw_01_Oval();
                        break;
                    case 2:
                        Draw_02_Rectangle();
                        break;
                    case 3:
                        Draw_03_Path();
                        break;
                    case 4:
                        Draw_04_Arc();
                        break;
                    case 5:
                        Draw_05_RectangleRouded();
                        break;
                    case 6:
                        Draw_06_RectangleStrocked();
                        break;
                    case 7:
                        Draw_07_Path2();
                        break;
                    case 8:
                        Draw_08_Path3();
                        break;
                }
            }
        });
    }


//    private void blackStarShow() {
//        iv.setImageBitmap(BitmapFactory.decodeStream(getResources().openRawResource(R.raw.blackstar)));
//    }


    private void Draw_00_Line() {
        ShapeDrawable d = new ShapeDrawable(new RectShape());
        d.setIntrinsicHeight(3);
        d.setIntrinsicWidth(500);
        d.getPaint().setColor(Color.RED);
        iv.setImageDrawable(d);
    }


    private void Draw_01_Oval() {
        ShapeDrawable d = new ShapeDrawable(new OvalShape());
        d.setIntrinsicHeight(400);
        d.setIntrinsicWidth(500);
        d.getPaint().setColor(Color.CYAN);
        iv.setImageDrawable(d);
    }


    private void Draw_02_Rectangle() {
        iv.setImageResource(R.drawable.green_rect); //UWAGA - inaczej
    }


    private void Draw_03_Path() {
        Path p = new Path();
        p.moveTo(100, 0);
        p.lineTo(50, 200);
        p.lineTo(200, 100);
        p.lineTo(0, 100);
        p.lineTo(150, 200);
        p.lineTo(100, 0);
        ShapeDrawable d = new ShapeDrawable(new PathShape(p, 250, 250));
        d.setIntrinsicHeight(350);
        d.setIntrinsicWidth(350);
        d.getPaint().setColor(Color.BLACK);
        iv.setImageDrawable(d);
    }


    private void Draw_04_Arc() {
        ShapeDrawable d = new ShapeDrawable(new ArcShape(20, 320));
        d.setIntrinsicHeight(250);
        d.setIntrinsicWidth(250);
        d.getPaint().setColor(Color.MAGENTA);
        iv.setImageDrawable(d);
    }


    private void Draw_05_RectangleRouded() {
        ShapeDrawable d = new ShapeDrawable(new RoundRectShape(
                new float[]{20, 20, 20, 20, 20, 20, 20, 20}, null, null));
        d.setIntrinsicHeight(250);
        d.setIntrinsicWidth(300);
        d.getPaint().setColor(Color.BLUE);
        iv.setImageDrawable(d);
        iv.setPadding(80, 80, 80, 80);
    }


    private void Draw_06_RectangleStrocked() {
        float[] outerRadii = new float[]{30, 30, 30, 30, 30, 30, 30, 30};
        RectF insetRectangle = new RectF(8, 8, 8, 8);
        float[] innerRadii = new float[]{6, 6, 6, 6, 60, 60, 60, 60};

        ShapeDrawable d = new ShapeDrawable(new RoundRectShape(outerRadii, insetRectangle, innerRadii));
        d.setIntrinsicHeight(350);
        d.setIntrinsicWidth(200);
        d.getPaint().setColor(Color.GREEN);
        iv.setPadding(60, 60, 60, 60);
        iv.setImageDrawable(d);
    }


    private void Draw_07_Path2() {
        Path p = new Path();
        p.setFillType(Path.FillType.EVEN_ODD); // inny typ wypełnienia
        p.moveTo(300, 50);
        p.lineTo(150, 550);
        p.lineTo(500, 300);
        p.lineTo(50, 300);
        p.lineTo(400, 550);
        p.lineTo(300, 50);
        ShapeDrawable d = new ShapeDrawable(new PathShape(p, 750, 600));
        d.setIntrinsicWidth(750);
        d.setIntrinsicHeight(600);
        int al = 50 + (int) (Math.random() * 200);
        d.getPaint().setColor(Color.argb(al, 0, 0, 0));
        iv.setImageDrawable(d);
        iv.setPadding(40, 40, 40, 40);
    }


    private void Draw_08_Path3() {
        Path p = new Path();
        p.moveTo(300, 50);
        p.lineTo(150, 550);
        p.lineTo(500, 300);
        p.lineTo(50, 300);
        p.lineTo(400, 550);
        p.lineTo(300, 50);
        ShapeDrawable d = new ShapeDrawable(new PathShape(p, 750, 600));
        d.setIntrinsicWidth(750);
        d.setIntrinsicHeight(600);
        Paint paint = d.getPaint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE); // kontur
        paint.setStrokeWidth(10);
        iv.setImageDrawable(d);
        iv.setPadding(40, 40, 40, 40);
    }


}