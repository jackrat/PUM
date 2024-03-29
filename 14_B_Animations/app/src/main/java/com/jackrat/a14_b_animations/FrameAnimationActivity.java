package com.jackrat.a14_b_animations;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FrameAnimationActivity extends AppCompatActivity {

    AnimationDrawable mframeAnimation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.framebyframe);

        // Obsługa przycisku Start.
        final Button onButton = (Button) findViewById(R.id.ButtonStart);
        onButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startAnimation();
            }
        });

        // Obsługa przycisku Stop.
        final Button offButton = (Button) findViewById(R.id.ButtonStop);
        offButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopAnimation();
            }
        });

    }

    private void startAnimation()
    {
        ImageView img = findViewById(R.id.ImageView_Juggle);

        BitmapDrawable frame1 = (BitmapDrawable)getResources().getDrawable(R.drawable.splash1);
        BitmapDrawable frame2 = (BitmapDrawable)getResources().getDrawable(R.drawable.splash2);
        BitmapDrawable frame3 = (BitmapDrawable)getResources().getDrawable(R.drawable.splash3);

        int reasonableDuration = 250;
        mframeAnimation = new AnimationDrawable();
        mframeAnimation.setOneShot(false);
        mframeAnimation.addFrame(frame1, reasonableDuration);
        mframeAnimation.addFrame(frame2, reasonableDuration);
        mframeAnimation.addFrame(frame3, reasonableDuration);

        img.setBackgroundDrawable(mframeAnimation);

        mframeAnimation.setVisible(true,true);
        mframeAnimation.start();
    }
    private void stopAnimation()
    {
        mframeAnimation.stop();
        mframeAnimation.setVisible(false,false);
    }




}