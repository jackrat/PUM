package com.jackrat.a14_a_canvas_drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class ChessActivity extends AppCompatActivity {


    class RefreshHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {
            ChessActivity.this.update();
        }

        public void sleep(long delayMillis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), delayMillis);
        }
    }

    private RefreshHandler mRedrawHandler = new RefreshHandler();



    public void update() {
        mRedrawHandler.sleep(500);
        shuffle();
        v.invalidate();
    }


    void shuffle() {
        int x1, x2, y1, y2;
        do {
            x1 = 1 + (int) Math.round(Math.random() * 7);
            y1 = 1 + (int) Math.round(Math.random() * 7);
        } while (65 > (byte) figury[x1][y1]);
        boolean col1 = ((x1 + y1) % 2) == 0;
        char f = figury[x1][y1];

        do {
            x2 = 1 + (int) Math.round(Math.random() * 7);
            y2 = 1 + (int) Math.round(Math.random() * 7);
        } while (65 <= (byte) figury[x2][y2]);
        boolean col2 = ((x2 + y2) % 2) == 0;


        figury[x1][y1] = col1 ? ' ' : '+';
        figury[x2][y2] = col1 == col2 ? f : Character.isLowerCase(f) ? Character.toUpperCase(f) : Character.toLowerCase(f);
    }

    static char[][] figury = new char[][]{
            {'!', '\"', '\"', '\"', '\"', '\"', '\"', '\"', '\"', '#'},
            {'$', 't', 'M', 'v', 'W', 'l', 'V', 'm', 'T', '%'},
            {'$', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', '%'},
            {'$', ' ', '+', ' ', '+', ' ', '+', ' ', '+', '%'},
            {'$', '+', ' ', '+', ' ', '+', ' ', '+', ' ', '%'},
            {'$', ' ', '+', ' ', '+', ' ', '+', ' ', '+', '%'},
            {'$', '+', ' ', '+', ' ', '+', ' ', '+', ' ', '%'},
            {'$', 'p', 'P', 'p', 'P', 'p', 'P', 'p', 'P', '%'},
            {'$', 'R', 'n', 'B', 'q', 'K', 'b', 'N', 'r', '%'},
            {'/', '(', '(', '(', '(', '(', '(', '(', '(', ')'}
    };


    ViewWithChessBoardFont v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = new ViewWithChessBoardFont(this);
        setContentView(v);

        mRedrawHandler.handleMessage(null);
    }


    private class ViewWithChessBoardFont extends View {
        private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Typeface mType;
        private float textSize = 128;
        private float xStart = 100;
        private float yStart = 200;

        public ViewWithChessBoardFont(Context context) {
            super(context);
            mPaint.setTextSize(textSize);
        }


        public void drawBoard(Canvas canvas) {
            for (int i = 0; i < 10; i++)
                canvas.drawText(figury[i], 0, 10, xStart, (yStart + (textSize * i)), mPaint);
            Log.d("UPD", "drawBoard");
        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            mType = getResources().getFont(R.font.mayafnt);
            mPaint.setTypeface(mType);
            mPaint.setColor(Color.BLACK);
            drawBoard(canvas);
        }
    }
}