package com.jackrat.a14_a_canvas_drawing;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DrawBitmapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ViewWithBitmap(this));
    }

    private static class ViewWithBitmap extends View {

        public ViewWithBitmap(Context context) {
            super(context);
        }

        @Override protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.CYAN);

            Bitmap lunaPic = BitmapFactory.decodeResource(getResources(), R.drawable.lnlun);

            // przeskalowana bitmapa
            Bitmap lunaPicMedium= Bitmap.createScaledBitmap(lunaPic, 800, 1200, false);
            canvas.drawBitmap(lunaPicMedium, 350, 400, null);

            //  miniaturka.
            Bitmap lunaPicSmall= Bitmap.createScaledBitmap(lunaPic, 200, 300, false);

            Matrix maxTopLeft = new Matrix();
            maxTopLeft.preRotate(30);

            Matrix maxBottomLeft = new Matrix();
            maxBottomLeft.preRotate(-30);

            Matrix maxTopRight = new Matrix();
            maxTopRight.preRotate(-30);		// obrót o 30 stopni
            maxTopRight.preScale(-1, 1);		// odbicie lustrzane

            Matrix maxBottomRight = new Matrix();
            maxBottomRight.preRotate(30);		// obrót o -30
            maxBottomRight.preScale(-1, 1);		// odbicie lustrzane

            Bitmap lunaPicTopLeft = Bitmap.createBitmap(lunaPicSmall, 0, 0, lunaPicSmall.getWidth(), lunaPicSmall.getHeight(), maxTopLeft, false);
            Bitmap lunaPicBottomLeft = Bitmap.createBitmap(lunaPicSmall, 0, 0, lunaPicSmall.getWidth(), lunaPicSmall.getHeight(), maxBottomLeft, false);
            Bitmap lunaPicTopRight = Bitmap.createBitmap(lunaPicSmall, 0, 0, lunaPicSmall.getWidth(), lunaPicSmall.getHeight(), maxTopRight, false);
            Bitmap lunaPicBottomRight = Bitmap.createBitmap(lunaPicSmall, 0, 0, lunaPicSmall.getWidth(), lunaPicSmall.getHeight(), maxBottomRight, false);

            // Zwolnienie pamięci poprzez usumięcie obrazków, które nie będą są już potrzebne.
            lunaPicSmall.recycle();
            lunaPic.recycle();

            canvas.drawBitmap(lunaPicTopLeft, 170, 300, null);
            canvas.drawBitmap(lunaPicBottomLeft, 170, 1300, null);
            canvas.drawBitmap(lunaPicTopRight, 1000, 300, null);
            canvas.drawBitmap(lunaPicBottomRight, 1000, 1300, null);


        }
    }
}
