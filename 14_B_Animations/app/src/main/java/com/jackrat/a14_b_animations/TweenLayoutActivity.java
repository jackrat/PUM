package com.jackrat.a14_b_animations;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class TweenLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweenoflayout);

        // Będziemy animować kontrolkę.
        LinearLayout layoutToAnimate =  findViewById(R.id.LayoutRow);

        // Wczytujemy odpowiednią animację.
        Animation an = AnimationUtils.loadAnimation(this, R.anim.snazzyintro);
        // I uruchamiamy ją.
        layoutToAnimate.startAnimation(an);


    }

}
