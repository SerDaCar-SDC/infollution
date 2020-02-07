package com.serdacar.infollution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
    }

    public void accesoLoginActivity(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
}
