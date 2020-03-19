package com.serdacar.infollution;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.alespero.expandablecardview.ExpandableCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.serdacar.infollution.database.EstacionDataSource;
import com.serdacar.infollution.model.Estacion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FirstActivity extends AppCompatActivity {
    static final String CLAVE_EMAIL = "EMAIL";
    FirebaseAuth fbAuth;

    TextView tv;
    ImageView ivLogo;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        getSupportActionBar().hide();
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.colorAzulOscuro)));

        tv = findViewById(R.id.tvBienvenida);
        ivLogo = findViewById(R.id.ivMenuLogo);
        ivLogo.setEnabled(false);

        email = getIntent().getStringExtra(RegisterActivity.CLAVE_EMAIL);
        tv.setText(String.format(getString(R.string.tv_bienvenida_first), email));

        fbAuth = FirebaseAuth.getInstance();

        /*
        // METER EXPANDABLE SWIPECARD
        ExpandableCardView card = findViewById(R.id.swipecard);
        card.setOnExpandedListener(new ExpandableCardView.OnExpandedListener() {
            @Override
            public void onExpandChanged(View v, boolean isExpanded) {
                Toast.makeText(FirstActivity.this, isExpanded ? "Expanded!" : "Collapsed!", Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    public void accederNoticias(View view) {
        startActivity(new Intent(this, NewsActivity.class));
    }

    public void accesoMapa(View view) {
        startActivity(new Intent(this, MapActivity.class));
        //overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void accederChat(View view) {
        startActivity(new Intent(this, ChatActivity.class));
        //overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void accederScrolling(View view) {
        startActivity(new Intent(this, ScrollingActivity.class));
        //overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void desconectar() {
        fbAuth.signOut();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.putExtra(CLAVE_EMAIL, email);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void createSimpleDialog(View v) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Infollution")
                .setMessage("¿Desea salir de la aplicación?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                desconectar();
                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                            }
                        });

        dialog = builder.create();
        dialog.show();
    }


}
