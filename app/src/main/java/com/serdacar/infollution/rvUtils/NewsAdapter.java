package com.serdacar.infollution.rvUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.serdacar.infollution.R;
import com.serdacar.infollution.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NoticiasViewHolder> implements View.OnClickListener {
    View.OnClickListener naListener;
    ArrayList<Article> listaNoticias;
    Article art;

    public NewsAdapter(ArrayList<Article> listaNoticias) {
        this.listaNoticias = listaNoticias;
    }

    @NonNull
    @Override
    public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        v.setOnClickListener(naListener);
        return new NoticiasViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiasViewHolder holder, int position) {
        /*Enlazamos el contenedor del contenido del elemento de la posición position
        * de pokemonVH con el VH*/
        holder.bindNews(listaNoticias.get(position));
    }

    @Override
    public int getItemCount() {
        /*Crea tantas cajas como listaNoticias haya*/
        return listaNoticias.size();
    }



    @Override
    public void onClick(View v) {
        if (naListener != null) {
            naListener.onClick(v);
        }
    }

    public static class NoticiasViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo;
        TextView tvURL;
        ImageView imagenHeadLine;

        public NoticiasViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitulo = itemView.findViewById(R.id.tvNombre);
            tvURL = itemView.findViewById(R.id.tvURL);
            imagenHeadLine = itemView.findViewById(R.id.ivImage);
        }
        /*para volcar la info del tipo articulo en los componentes del layaut que hayamos llamado */
        public void bindNews(Article articulo) {

            tvTitulo.setText(articulo.getTitle());
            tvURL.setText(articulo.getUrl());

            String url = articulo.getUrl();

            // obtenerUrl(url);

            String imagenUrl = articulo.getUrlToImage();
            Picasso.get().load(imagenUrl).into(imagenHeadLine);


        }

        /*
        public String obtenerUrl(String urlRecogida) {

            String urlNoticia = urlRecogida;

            return urlNoticia;
        }*/
    }

    public void setListener(View.OnClickListener naListener) {
        this.naListener = naListener;
    }



}
