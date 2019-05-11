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
import com.appmoviles.muriel.retodeezermuriel.model.ListaReproduccion;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterTemplate_Listas_Reproduccion extends RecyclerView.Adapter<AdapterTemplate_Listas_Reproduccion.CustomViewHolder> {


    ArrayList<ListaReproduccion> data;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;

        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTemplate_Listas_Reproduccion() {
        data = new ArrayList<>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_lista_reproduccion, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterTemplate_Listas_Reproduccion.CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.renglon_tv_nombre_lista_reproduccion)).setText(data.get(position).getNombreLista());
        ((TextView) holder.root.findViewById(R.id.renglon_tv_nombre_usuario_creador)).setText(data.get(position).getNombreUsuarioCreador());
        ((TextView) holder.root.findViewById(R.id.renglon_tv_total_canciones)).setText(data.get(position).getTotalCanciones());

        Uri uri = data.get(position).getImagen();

        Glide.with(holder.root.findViewById(R.id.renglon_iv_imagen_lista_reproduccion)).load(uri).into((ImageView) holder.root.findViewById(R.id.renglon_iv_imagen_lista_reproduccion));

        ((RelativeLayout) holder.root.findViewById(R.id.rl_renglon_relative_marco_lista_reproduccion)).setOnClickListener(new View.OnClickListener() {
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

    public void agregarListaReproduccion(ListaReproduccion listaReproduccion) {
        data.add(listaReproduccion);
        notifyDataSetChanged();
    }

    public void vaciarLista(){
        data = new ArrayList<ListaReproduccion>();
    }

    //Patrón observer para identifcar cual transacción se seleccionó

    public interface OnItemClickListener {
        void onItemClick(ListaReproduccion listaReproduccion);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //Hasta aquí patrón observer
}
