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
        TextView tvDescripcion;
        ImageView imagenHeadLine;
        TextView tvAutor;

        public NoticiasViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitulo = itemView.findViewById(R.id.tvNombre);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            imagenHeadLine = itemView.findViewById(R.id.ivImage);
            tvAutor = itemView.findViewById(R.id.tvAutor);

        }
        /*para volcar la info del tipo articulo en los componentes del layaut que hayamos llamado */
        public void bindNews(Article articulo) {
            tvTitulo.setText(articulo.getTitle());
            tvDescripcion.setText(articulo.getDescription());
            tvAutor.setText(articulo.getAuthor());

            String imagenUrl = articulo.getUrlToImage();
            Picasso.get().load(imagenUrl).into(imagenHeadLine);
        }
    }

    public void setListener(View.OnClickListener naListener) {
        this.naListener = naListener;
    }



}
