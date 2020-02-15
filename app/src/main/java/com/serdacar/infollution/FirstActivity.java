package com.serdacar.infollution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class FirstActivity extends AppCompatActivity {
    static final String CLAVE_EMAIL = "EMAIL";
    TextView tv;
    FirebaseAuth fbAuth;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        getSupportActionBar().hide();
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.colorAzulOscuro)));

        tv = findViewById(R.id.tvBienvenida);

        email = getIntent().getStringExtra(RegisterActivity.CLAVE_EMAIL);
        tv.setText(String.format(getString(R.string.tv_bienvenida_first), email));

        fbAuth = FirebaseAuth.getInstance();
    }

    //MENU CON EL ITEM DESCONECTAR
    /*@Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itm_desconectar) {
            fbAuth.signOut();
            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra(CLAVE_EMAIL, email);
            startActivity(i);
            finish();
            overridePendingTransition(R.anim.right_in, R.anim.right_out);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_first_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    public void accesoMapa(View view) {
        startActivity(new Intent(this, MapActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void desconectar(View view) {
        fbAuth.signOut();
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra(CLAVE_EMAIL, email);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);    }
}
