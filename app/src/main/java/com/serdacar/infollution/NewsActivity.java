package com.serdacar.infollution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.serdacar.infollution.model.Article;
import com.serdacar.infollution.model.Headlines;
import com.serdacar.infollution.retrofitUtils.APINews;
import com.serdacar.infollution.retrofitUtils.RetrofitClient;
import com.serdacar.infollution.rvUtils.NewsAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NewsActivity extends AppCompatActivity {

    RecyclerView rv;
    NewsAdapter na;
    LinearLayoutManager llm;

    ImageView ivLogoNoticias;

    String pais = "US";
    static final String API_KEY = "45dde31099fb4edfb5fb6b622b80bff2";

    static final String CLAVE_URL = "URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getSupportActionBar().hide();

        rv = findViewById(R.id.rvNoticias);
        ivLogoNoticias = findViewById(R.id.ivMenuNews);

        // pais = getCountry();

        cosumirWS();

        ivLogoNoticias.setImageResource(R.drawable.ic_info_foreground_rojo);
        ivLogoNoticias.setEnabled(false);

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

    private void cosumirWS() {

        Retrofit r = RetrofitClient.getClient(APINews.BASE_URL);
        APINews ars = r.create(APINews.class);
        Call<Headlines> call = ars.getHeadlines(pais, API_KEY);
        // Log.i("ANTES_ENQUEUE", "Antes del Enqueue" );

        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (!response.isSuccessful()) {
                    Log.i("onResponse", "Error" + response.code());
                } else {
                    Log.i("onResponse", "Exito" + response.code());
                    Headlines r = (Headlines) response.body();

                    ArrayList<Article> listaNoticias = r.getArticles();

                    configurarRecyclerView(listaNoticias);

                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Log.e("OnFailure", "Error" + t.getMessage());
            }
        });

    }

    private void configurarRecyclerView(final ArrayList<Article> listaNoticias) {
        rv = findViewById(R.id.rvNoticias);

        na = new NewsAdapter(listaNoticias);
        na.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewsActivity.this, NewsDetailledActivity.class);
                i.putExtra(CLAVE_URL, listaNoticias.get(rv.getChildAdapterPosition(v)).getUrl());
                startActivity(i);
            }
        });
        llm = new LinearLayoutManager(this);

        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        rv.setAdapter(na);
    }

}