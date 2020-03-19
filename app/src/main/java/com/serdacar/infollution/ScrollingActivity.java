package com.serdacar.infollution;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;


public class ScrollingActivity extends AppCompatActivity {

    ImageView ivGas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        ivGas = findViewById(R.id.ivGas);
        ivGas.setImageResource(R.drawable.ic_molecule_foreground_rojo);
        ivGas.setEnabled(false);

    }


    public void accesoFirst(View v) {
        startActivity(new Intent(this, FirstActivity.class));
        //overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void accesoMapa(View view) {
        startActivity(new Intent(this, MapActivity.class));
        //overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void accederChat(View view) {
        startActivity(new Intent(this, ChatActivity.class));
        //overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }

    public void accederNoticias(View view) {
        startActivity(new Intent(this, NewsActivity.class));
    }

}
