package com.serdacar.infollution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    TextView tvMovimiento;
    ImageView ivFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        View tvMovimiento = findViewById(R.id.tvMovimiento);

        Animation translate1 = AnimationUtils.loadAnimation(this, R.anim.grow_disappear);

        tvMovimiento.startAnimation(translate1);

        ImageView ivFoto = findViewById(R.id.ivLogoSS);

        Animation translate2 = AnimationUtils.loadAnimation(this, R.anim.leftin);

        ivFoto.startAnimation(translate2);

        openApp(true);
    }

    private void openApp(boolean locationPermission) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity
                        .this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}
