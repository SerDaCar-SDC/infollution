package com.example.infollutionprueba1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScrern extends AppCompatActivity {

    TextView tvMovimiento;
    ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scrern);

        getSupportActionBar().hide();

        View tvMovimiento = findViewById(R.id.tvMovimiento);

        Animation translate = AnimationUtils.loadAnimation(this, R.anim.grow_disappear);

        tvMovimiento.startAnimation(translate);

        ImageView ivFoto = findViewById(R.id.ivLogo_SS);

        Animation translate = AnimationUtils.loadAnimation(this, R.anim.leftin);

        ivFoto.startAnimation(translate);

        openApp(true);
    }

    private void openApp(boolean locationPermission) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScrern
                        .this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
}
