package com.example.infollutionprueba1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScrern extends AppCompatActivity {

    TextView tvMovimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scrern);

        getSupportActionBar().hide();

        View tvMovimiento = findViewById(R.id.tvMovimiento);

        Animation translate = AnimationUtils.loadAnimation(this, R.anim.grow_disappear);

        tvMovimiento.startAnimation(translate);
    }
}
