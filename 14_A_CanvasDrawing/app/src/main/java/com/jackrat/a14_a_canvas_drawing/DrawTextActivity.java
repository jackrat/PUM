package com.jackrat.a14_a_canvas_drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DrawTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ViewWithText(this));
    }

    private static class ViewWithText extends View {
        private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Typeface mType;


        static int fsize = 64;
        static int y0 = 100;

        public ViewWithText(Context context) {
            super(context);

            mPaint.setTextSize(fsize);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int y = y0;
            int d = 100;

            canvas.drawColor(Color.GREEN);

            mPaint.setTypeface(null);
            canvas.drawText("Domyślna czcionka (normalna)", 30, y, mPaint);

            mType = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
            mPaint.setTypeface(mType);
            canvas.drawText("Czcionka bezszeryfowa (normalna)", 30, y += d, mPaint);

            mType = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
            mPaint.setTypeface(mType);
            canvas.drawText("Czcionka bezszeryfowa (pogrubiona)", 30, y += d, mPaint);

            mType = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL);
            mPaint.setTypeface(mType);
            canvas.drawText("Czcionka Monospace (normalna)", 30, y += d, mPaint);

            mType = Typeface.create(Typeface.SERIF, Typeface.NORMAL);
            mPaint.setTypeface(mType);
            canvas.drawText("Czcionka o stałej szer. znaków (normalna)", 30, y += d, mPaint);

            mType = Typeface.create(Typeface.SERIF, Typeface.BOLD);
            mPaint.setTypeface(mType);
            canvas.drawText("Czcionka szeryfowa (pogrubiona)", 30, y += d, mPaint);

            mType = Typeface.create(Typeface.SERIF, Typeface.ITALIC);
            mPaint.setTypeface(mType);
            canvas.drawText("Czcionka szeryfowa (kursywa)", 30, y += d, mPaint);

            mType = Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC);
            mPaint.setTypeface(mType);
            canvas.drawText("Czcionka szeryfowa (pogrubiona kursywa)", 30, y += d, mPaint);

            mPaint.setTypeface(null);
            fsize += 64;
            mPaint.setTextSize(fsize);
            canvas.drawText("Czcionka rozmiaru " + fsize, 30, y += d, mPaint);

            fsize  += 64;
            mPaint.setTextSize(fsize);
            canvas.drawText("Czcionka rozmiaru " + fsize, 30, y += 2 * d, mPaint);

            mPaint.setTextSize(fsize);
            mPaint.setAntiAlias(false);
            canvas.drawText("Tekst bez antyaliasingu", 30, y += 2 * d, mPaint);

            mPaint.setAntiAlias(true);
            mPaint.setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            canvas.drawText("Tekst przekreślony", 30, y += 2 * d, mPaint);

            mPaint.setFlags(Paint.UNDERLINE_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
            canvas.drawText("Tekst podkreślony", 30, y += 2 * d, mPaint);

        }
    }
}
