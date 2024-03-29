package com.jackrat.a14_a_canvas_drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class DrawCustomFontActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ViewWithChessBoardFont(this));
    }

   

    private static class ViewWithChessBoardFont extends View {
        private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private Typeface mType;
        private float textSize = 128;
        private float xStart = 100;
        private float yStart = 200;

        public ViewWithChessBoardFont(Context context) {
            super(context);
            mPaint.setTextSize(textSize);
        }




        @RequiresApi(api = Build.VERSION_CODES.O) //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        @Override
        protected void onDraw(Canvas canvas) {

/*
DIAGRAM BORDERS:
                                      SINGLE        DOUBLE       EXTRA *
       Top left corner                  1          !  or 033     a - A
       Top border                       2          "     034
       Top right corner                 3          #     035     s - S
       Left border                      4          $     036
       Right border                     5          %     037
       Bottom left corner               7          /     047     d - D
       Bottom border                    8          (     040
       Bottom right corner              9          )     041     f - F

BOARD POSITION ASSIGNMENTS:
                                    WHITE SQUARE         DARK SQUARE
       Squares                      [space] or * 042          +  043
       White pawn                        p                    P
       Black pawn                        o                    O
       White knight                      n                    N
       Black knight                      m                    M
       White bishop                      b                    B
       Black bishop                      v                    V
       White rook                        r                    R
       Black rook                        t                    T
       White queen                       q                    Q
       Black queen                       w                    W
       White king                        k                    K
       Black king                        l                    L

The EXTRA keys contain round corners.  The keys  (x),  (X),  (.), and  (:)
contain auxiliary symbols to indicate individual movements of the  pieces.
*/



            canvas.drawColor(Color.WHITE);
            mType = getResources().getFont(R.font.mayafnt);
            mPaint.setTypeface(mType);
            mPaint.setColor(Color.BLACK);
            canvas.drawText("!\"\"\"\"\"\"\"\"#", xStart, yStart, mPaint);
            canvas.drawText("$tMvWlVmT%", xStart, (yStart + (textSize * 1)), mPaint);
            canvas.drawText("$OoOoOoOo%", xStart, (yStart + (textSize * 2)), mPaint);
            canvas.drawText("$ + + + +%", xStart, (yStart + (textSize * 3)), mPaint);
            canvas.drawText("$+ + + + %", xStart, (yStart + (textSize * 4)), mPaint);
            canvas.drawText("$ + + + +%", xStart, (yStart + (textSize * 5)), mPaint);
            canvas.drawText("$+ + + + %", xStart, (yStart + (textSize * 6)), mPaint);
            canvas.drawText("$pPpPpPpP%", xStart, (yStart + (textSize * 7)), mPaint);
            canvas.drawText("$RnBqKbNr%", xStart, (yStart + (textSize * 8)), mPaint);
            canvas.drawText("/(((((((()", xStart, (yStart + (textSize * 9)), mPaint);

        }
    }
}