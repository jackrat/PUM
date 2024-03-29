package com.jackrat.a14_b_animations;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class TweenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tween);


        final Button fadeButton = (Button) findViewById(R.id.ButtonAlpha);
        fadeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performAnimation(R.anim.transparency);
            }
        });


        final Button growButton = (Button) findViewById(R.id.ButtonScale);
        growButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performAnimation(R.anim.grow);
            }
        });


        final Button moveButton = (Button) findViewById(R.id.ButtonTranslate);
        moveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performAnimation(R.anim.translate_position);
            }
        });


        final Button spinButton = (Button) findViewById(R.id.ButtonRotate);
        spinButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performAnimation(R.anim.spin);
            }
        });


        final Button allButton = (Button) findViewById(R.id.ButtonAll);
        allButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                performAnimation(R.anim.shakennotstirred);
            }
        });

    }

    private void performAnimation(int animationResourceID) {
        ImageView reusableImageView = findViewById(R.id.ImageViewForTweening);
        reusableImageView.setImageResource(R.drawable.green_rect);
        reusableImageView.setVisibility(View.VISIBLE);

        Animation an = AnimationUtils.loadAnimation(this, animationResourceID);
        an.setAnimationListener(new MyAnimationListener());
        reusableImageView.startAnimation(an);
    }


    private void toggleButtons(boolean clickableState) {
        final Button fadeButton = (Button) findViewById(R.id.ButtonAlpha);
        fadeButton.setClickable(clickableState);

        final Button growButton = (Button) findViewById(R.id.ButtonScale);
        growButton.setClickable(clickableState);

        final Button moveButton = (Button) findViewById(R.id.ButtonTranslate);
        moveButton.setClickable(clickableState);

        final Button spinButton = (Button) findViewById(R.id.ButtonRotate);
        spinButton.setClickable(clickableState);

        final Button allButton = (Button) findViewById(R.id.ButtonAll);
        allButton.setClickable(clickableState);

    }

    class MyAnimationListener implements Animation.AnimationListener {

        public void onAnimationEnd(Animation animation) {
            ImageView reusableImageView = (ImageView) findViewById(R.id.ImageViewForTweening);
            reusableImageView.setVisibility(View.INVISIBLE);

            toggleButtons(true);
        }

        public void onAnimationRepeat(Animation animation) {

        }

        public void onAnimationStart(Animation animation) {
            toggleButtons(false);
        }

    }
}
