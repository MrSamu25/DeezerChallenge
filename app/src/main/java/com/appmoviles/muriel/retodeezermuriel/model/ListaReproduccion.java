package com.appmoviles.muriel.retodeezermuriel.model;

import android.net.Uri;

public class ListaReproduccion {


    private String nombreLista;
    private String nombreUsuarioCreador;
    private String totalCanciones;
    private Uri imagen;
    private String Descripcion;
    private String id;

    public ListaReproduccion() {
    }

    public ListaReproduccion(String nombreLista, String nombreUsuarioCreador, String totalCanciones, Uri imagen) {
        this.nombreLista = nombreLista;
        this.nombreUsuarioCreador = nombreUsuarioCreador;
        this.totalCanciones = totalCanciones;
        this.imagen = imagen;
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public void setNombreLista(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public String getNombreUsuarioCreador() {
        return nombreUsuarioCreador;
    }

    public void setNombreUsuarioCreador(String nombreUsuarioCreador) {
        this.nombreUsuarioCreador = nombreUsuarioCreador;
    }

    public String getTotalCanciones() {
        return totalCanciones;
    }

    public void setTotalCanciones(String totalCanciones) {
        this.totalCanciones = totalCanciones;
    }

    public Uri getImagen() {
        return imagen;
    }

    public void setImagen(Uri imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
