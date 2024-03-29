package com.jackrat.a14_b_animations;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

public class FrameAnimationActivity2 extends AppCompatActivity {

    AnimationDrawable mframeAnimation = null;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.framebyframe_switcher);

        ImageSwitcher switcher = findViewById(R.id.ImageSwitcher_Juggle);
        switcher.setVisibility(View.VISIBLE);

        switcher.setFactory(new ViewSwitcher.ViewFactory() {

            public View makeView() {
                ImageView iv = new ImageView(FrameAnimationActivity2.this);
                iv.setBackgroundColor(0xFF000000);
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                iv.setLayoutParams(new ImageSwitcher.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
                return iv;
            }
        });

        // Obsługa przycisku Start
        final Button onButton = (Button) findViewById(R.id.ButtonStart);
        onButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startAnimation();
            }
        });

    }

    private void startAnimation() {
        final ImageSwitcher switcher = (ImageSwitcher) findViewById(R.id.ImageSwitcher_Juggle);

        new Thread() {
            final int dur = 500;
            final int[] imageResourceIds = {R.drawable.splash1, R.drawable.splash2, R.drawable.splash3};
            int cur = 0;

            @Override
            public void run() {
                while (cur < imageResourceIds.length) {
                    synchronized (this) {
                        final int curIdx = cur;
                        mHandler.post(new Runnable() {
                            public void run() {
                                switcher.setImageResource(imageResourceIds[curIdx]);
                            }
                        });
                        cur++;
                        try {
                            wait(dur);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

        }.start();

    }


}
