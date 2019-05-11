package com.appmoviles.muriel.retodeezermuriel.model;

import android.net.Uri;

public class Cancion {

    private String nombreCancion;
    private String nombreArtista;
    private String anioLanzamiento;
    private String id;
    private Uri imagen;
    private String linkDeezer;
    private String preview;

    public Cancion() {
    }

    public Cancion(String nombreCancion, String nombreArtista, String anioLanzamiento, Uri imagen) {
        this.nombreCancion = nombreCancion;
        this.nombreArtista = nombreArtista;
        this.anioLanzamiento = anioLanzamiento;
        this.imagen = imagen;
    }


    public String getLinkDeezer() {
        return linkDeezer;
    }

    public void setLinkDeezer(String linkDeezer) {
        this.linkDeezer = linkDeezer;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(String anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
    }

    public Uri getImagen() {
        return imagen;
    }

    public void setImagen(Uri imagen) {
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
