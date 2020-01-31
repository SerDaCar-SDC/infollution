package com.example.infollutionprueba1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void irA_ActivityRegistro(View view) {
        startActivity(new Intent(this, ActivityRegistro.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void irA_SplashScreen(View view) {
        Intent i = new Intent(this, SplashScrern.class);
        startActivity(i);
    }
}
