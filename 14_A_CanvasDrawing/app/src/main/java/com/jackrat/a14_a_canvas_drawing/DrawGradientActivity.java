package com.jackrat.a14_a_canvas_drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DrawGradientActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ViewWithGradient(this));
    }


    private static class ViewWithGradient extends View {

        public ViewWithGradient(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {

            canvas.drawColor(Color.WHITE);

            int w = canvas.getWidth();
            int h = canvas.getHeight();

            Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            LinearGradient linGrad = new LinearGradient(0, 0, 150, 150, Color.RED, Color.BLACK, Shader.TileMode.MIRROR);
            circlePaint.setShader(linGrad);
            canvas.drawCircle(w / 3, w / 3, w / 3, circlePaint);

            RadialGradient radGrad = new RadialGradient(3 * w / 4, h / 2, w / 8, Color.GREEN, Color.BLACK, Shader.TileMode.MIRROR);
            circlePaint.setShader(radGrad);
            canvas.drawCircle(3 * w / 4, h / 2, w / 4, circlePaint);

            SweepGradient sweepGrad = new SweepGradient(w / 3, h - (w / 3), new int[]{
                    Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.RED}, null);
            circlePaint.setShader(sweepGrad);
            canvas.drawCircle(w / 3, h - (w / 3), w / 3, circlePaint);

        }
    }

}

