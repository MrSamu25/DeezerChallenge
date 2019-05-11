package com.appmoviles.muriel.retodeezermuriel;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.muriel.retodeezermuriel.model.Cancion;
import com.appmoviles.muriel.retodeezermuriel.model.ListaReproduccion;
import com.appmoviles.muriel.retodeezermuriel.services.ServiceManager;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.appmoviles.muriel.retodeezermuriel.constantes.Constantes.ID_CANCION_SELECCIONADA;

public class ReproducirActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView iv_reproducir_return;
    private ImageView iv_reproducir_imagen_cancion;
    private TextView tv_reproducir_nombre_cancion;
    private TextView tv_reproducir_artista_cancion;
    private TextView tv_reproducir_duracion_cancion;
    private TextView tv_reproducir_nombre_album;
    private Button btn_reproducir_reproducir_cancion;

    private Cancion cancionSelecionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reproducir);

        iv_reproducir_return = findViewById(R.id.iv_reproducir_return);
        iv_reproducir_return.setOnClickListener(this);
        iv_reproducir_imagen_cancion = findViewById(R.id.iv_reproducir_imagen_cancion);
        tv_reproducir_nombre_cancion = findViewById(R.id.tv_reproducir_nombre_cancion);
        tv_reproducir_artista_cancion = findViewById(R.id.tv_reproducir_artista_cancion);
        tv_reproducir_nombre_album = findViewById(R.id.tv_reproducir_nombre_album);
        tv_reproducir_duracion_cancion = findViewById(R.id.tv_reproducir_duracion_cancion);
        btn_reproducir_reproducir_cancion = findViewById(R.id.btn_reproducir_reproducir_cancion);
        btn_reproducir_reproducir_cancion.setOnClickListener(this);


        cancionSelecionada = new Cancion();

        if (this.getIntent().getExtras() != null) {
            Bundle bundle = this.getIntent().getExtras();
            String idCancion = (String)bundle.get(ID_CANCION_SELECCIONADA);

            cancionSelecionada.setId(idCancion);

            //Busca la info de la canción en DEEZER
            buscarCancion(idCancion);

        }
    }

    private void buscarCancion(String idCancion) {


        new Thread(() -> {
            new ServiceManager.TrackByIdGET(new ServiceManager.TrackByIdGET.OnResponseListener() {
                @Override
                public void onResponse(String response) {
                    runOnUiThread(() -> {

                        try {

                            JSONObject json_puro = new JSONObject(response);
                            String title = json_puro.getString("title");

                            String nombreArtista = json_puro.getJSONObject("artist").getString("name");
                            String nombreAlbum = json_puro.getJSONObject("album").getString("title");
                            String imagen = json_puro.getJSONObject("album").getString("cover");
                            String duracion = json_puro.getString("duration");
                            String fecha_lanzamiento = json_puro.getString("release_date");
                            String link = json_puro.getString("link");
                            String preview = json_puro.getString("preview");

                            cancionSelecionada.setNombreCancion(title);
                            cancionSelecionada.setNombreArtista(nombreArtista);
                            cancionSelecionada.setImagen(Uri.parse(imagen));
                            cancionSelecionada.setAnioLanzamiento(fecha_lanzamiento);
                            cancionSelecionada.setLinkDeezer(link);
                            cancionSelecionada.setPreview(preview);


                            Glide.with(findViewById(R.id.iv_reproducir_imagen_cancion)).load(Uri.parse(imagen)).into((ImageView) findViewById(R.id.iv_reproducir_imagen_cancion));
                            tv_reproducir_nombre_cancion.setText(cancionSelecionada.getNombreCancion());
                            tv_reproducir_artista_cancion.setText(cancionSelecionada.getNombreArtista());
                            tv_reproducir_nombre_album.setText("Nombre Álbum: " + nombreAlbum);
                            tv_reproducir_duracion_cancion.setText("Duración: " + duracion);


                        } catch (JSONException e) {
                            //e.printStackTrace();
                            Log.e("Error JSON Reproducir", e.getMessage());
                        }

                    });
                }
            }, idCancion);
        }).start();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_reproducir_reproducir_cancion:
                escucharCancion();
                break;

            case R.id.iv_reproducir_return:
                finish();
                break;

        }
    }

    public void escucharCancion(){

        MediaPlayer mp = new MediaPlayer();
        Uri uri = Uri.parse(cancionSelecionada.getPreview());
        try {
            mp.setDataSource(ReproducirActivity.this, uri);
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error reproduciendo", e.getMessage());
        }

        mp.start();

    }



    public void mostrarMensaje(String texto) {
        Toast.makeText(ReproducirActivity.this, texto, Toast.LENGTH_LONG).show();
    }
}
