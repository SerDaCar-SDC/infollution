package com.serdacar.infollution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
    }

    public void accesoRegisterActivity(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void accesoFirstActivity(View view) {
        Intent i = new Intent(this, MapActivity.class);
        startActivity(i);
    }
}
