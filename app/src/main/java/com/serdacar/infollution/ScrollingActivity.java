package com.serdacar.infollution;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ScrollingActivity extends AppCompatActivity {
    ImageView ivGas;
    TextView leerMasDioxidoAzufre;
    TextView leerMasMonoxidoCarbono;
    TextView leerMasMonoxidoNitrogeno;
    TextView leerMasDioxidoNitrogeno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ivGas = findViewById(R.id.ivGas);
        ivGas.setImageResource(R.drawable.ic_molecule_foreground_rojo);
        ivGas.setEnabled(false);

        leerMasDioxidoAzufre = findViewById(R.id.LeerMasDioxidoAzufre);
        leerMasMonoxidoCarbono = findViewById(R.id.LeerMasMonoxidoCarbono);
        leerMasMonoxidoNitrogeno = findViewById(R.id.LeerMasMonoxidoNitrogeno);
        leerMasDioxidoNitrogeno = findViewById(R.id.LeerMasDioxidoNitrogeno);
    }

    public void accesoFirst(View v) {
        startActivity(new Intent(this, FirstActivity.class));
    }

    public void accesoMapa(View view) {
        startActivity(new Intent(this, MapActivity.class));
    }

    public void accederChat(View view) {
        startActivity(new Intent(this, ChatActivity.class));
    }

    public void accederNoticias(View view) {
        startActivity(new Intent(this, NewsActivity.class));
    }

    int cont1 = 1;
    public void leerMasDioxidoAzufre(View view) {
        cont1 ++;
        if (cont1 %2 == 0) {
            leerMasDioxidoAzufre.setText(R.string.dioxidoazufre_info);
        } else {
            leerMasDioxidoAzufre.setText("Leer más...");
        }
    }

    int cont2 = 1;
    public void leerMasMonoxidoCarbono(View view) {
        cont2 ++;
        if (cont2 %2 == 0) {
            leerMasMonoxidoCarbono.setText(R.string.monoxidocarbono_info);
        } else {
            leerMasMonoxidoCarbono.setText("Leer más...");
        }
    }

    int cont3 = 1;
    public void leerMasMonoxidoNitrogeno(View view) {
        cont3 ++;
        if (cont3 %2 == 0) {
            leerMasMonoxidoNitrogeno.setText(R.string.monoxidonitrogeno_info);
        } else {
            leerMasMonoxidoNitrogeno.setText("Leer más...");
        }
    }

    int cont4 = 1;
    public void leerMasDioxidoNitrogeno(View view) {
        cont4 ++;
        if (cont4 %2 == 0) {
            leerMasDioxidoNitrogeno.setText(R.string.dioxidonitrogeno_info);
        } else {
            leerMasDioxidoNitrogeno.setText("Leer más...");
        }
    }
}
