package com.jackrat.a14_a_canvas_drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DrawShapeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ViewWithRedDot(this));
    }
    private static class ViewWithRedDot extends View {


        public ViewWithRedDot(Context context) {
            super(context);

        }

        @Override protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.BLACK);

            Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            circlePaint.setColor(Color.RED);
            canvas.drawCircle(canvas.getWidth()/2, canvas.getHeight()/2, canvas.getWidth()/3, circlePaint);

        }
    }

}

