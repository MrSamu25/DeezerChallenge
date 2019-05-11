package com.appmoviles.muriel.retodeezermuriel.adapters;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appmoviles.muriel.retodeezermuriel.R;
import com.appmoviles.muriel.retodeezermuriel.model.Cancion;
import com.appmoviles.muriel.retodeezermuriel.model.ListaReproduccion;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterTemplate_Canciones extends RecyclerView.Adapter<AdapterTemplate_Canciones.CustomViewHolder> {


    ArrayList<Cancion> data;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;

        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTemplate_Canciones() {
        data = new ArrayList<>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_cancion, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.renglon_tv_nombre_cancion)).setText(data.get(position).getNombreCancion());
        ((TextView) holder.root.findViewById(R.id.renglon_tv_nombre_artista)).setText(data.get(position).getNombreArtista());
        ((TextView) holder.root.findViewById(R.id.renglon_tv_anio_lanzamiento)).setText(data.get(position).getAnioLanzamiento());

        Uri uri = data.get(position).getImagen();
        Glide.with(holder.root.findViewById(R.id.renglon_iv_imagen_cancion)).load(uri).into((ImageView) holder.root.findViewById(R.id.renglon_iv_imagen_cancion));

        ((RelativeLayout) holder.root.findViewById(R.id.rl_renglon_relative_marco_cancion)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(data.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void agregarCancion(Cancion cancion) {
        data.add(cancion);
        notifyDataSetChanged();
    }

    //Patrón observer para identifcar cual transacción se seleccionó

    public interface OnItemClickListener {
        void onItemClick(Cancion cancion);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //Hasta aquí patrón observer
}
