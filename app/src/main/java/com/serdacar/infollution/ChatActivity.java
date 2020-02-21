package com.serdacar.infollution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

public class ChatActivity extends AppCompatActivity {

    private WebView wv;
    //ImageView ivMenuChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //ivMenuChat = findViewById(R.id.ivMenuChat);
        //ivMenuChat.setImageResource(R.drawable.ic_chat_foreground_rojo);

        wv = (WebView) findViewById(R.id.wv);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.loadUrl("https://console.dialogflow.com/api-client/demo/embedded/1e634938-104a-4c07-b5c5-699686d525b9");

        getSupportActionBar().hide();
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(getResources().getColor(R.color.colorAzulOscuro)));
    }

    public void accesoFirst(View v) {
        startActivity(new Intent(this, FirstActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    public void accesoMapa(View view) {
        startActivity(new Intent(this, MapActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }
}
