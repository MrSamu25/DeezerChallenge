package com.appmoviles.muriel.retodeezermuriel;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.muriel.retodeezermuriel.adapters.AdapterTemplate_Canciones;
import com.appmoviles.muriel.retodeezermuriel.model.Cancion;
import com.appmoviles.muriel.retodeezermuriel.model.ListaReproduccion;
import com.appmoviles.muriel.retodeezermuriel.services.ServiceManager;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.appmoviles.muriel.retodeezermuriel.constantes.Constantes.ID_CANCION_SELECCIONADA;
import static com.appmoviles.muriel.retodeezermuriel.constantes.Constantes.ID_LISTA_REPRODUCCION;

public class CancionActivity extends AppCompatActivity implements AdapterTemplate_Canciones.OnItemClickListener, View.OnClickListener {

    private RecyclerView rv_listas_canciones;
    private AdapterTemplate_Canciones adapterTemplate_canciones;

    private TextView tv_cancion_nombre_play_list;
    private TextView tv_cancion_descripcion_play_list;
    private ImageView iv_cancion_imagen_lista_reproduccion;

    private TextView tv_cancion_numero_canciones;
    private TextView tv_cancion_numero_fans;


    private ImageView iv__return;

    ListaReproduccion listaReproduccionSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancion);

        tv_cancion_nombre_play_list = findViewById(R.id.tv_cancion_nombre_play_list);
        tv_cancion_descripcion_play_list = findViewById(R.id.tv_cancion_descripcion_play_list);
        iv_cancion_imagen_lista_reproduccion = findViewById(R.id.iv_cancion_imagen_lista_reproduccion);

        tv_cancion_numero_canciones = findViewById(R.id.tv_cancion_numero_canciones);
        tv_cancion_numero_fans = findViewById(R.id.tv_cancion_numero_fans);

        iv__return = findViewById(R.id.iv_return);
        iv__return.setOnClickListener(this);

        listaReproduccionSeleccionada = new ListaReproduccion();

        if (this.getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();

            String id_lista_reproduccion = (String) bundle.get(ID_LISTA_REPRODUCCION);

            //buscarListaReproduccion(id_lista_reproduccion);
            listaReproduccionSeleccionada.setId(id_lista_reproduccion);

            //Busca la info de la lista de reproducción en DEEZER
            buscarListaReproduccion(id_lista_reproduccion);

        }

        rv_listas_canciones = findViewById(R.id.rv_listas_canciones);
        adapterTemplate_canciones = new AdapterTemplate_Canciones();
        rv_listas_canciones.setHasFixedSize(true);
        rv_listas_canciones.setLayoutManager(new LinearLayoutManager(CancionActivity.this));

        rv_listas_canciones.setAdapter(adapterTemplate_canciones);
        adapterTemplate_canciones.setListener(CancionActivity.this);


        /**
         //Datos de prueba
         for (int i = 0; i < 8; i++) {
         Cancion tmp = new Cancion();
         tmp.setNombreCancion("nombre" + i);
         tmp.setAnioLanzamiento("Año " + i);
         tmp.setNombreArtista("artista " + i);
         adapterTemplate_canciones.agregarCancion(tmp);
         }

         */

    }

    @Override
    public void onItemClick(Cancion cancion) {
        Intent i = new Intent(CancionActivity.this, ReproducirActivity.class);
        i.putExtra(ID_CANCION_SELECCIONADA, cancion.getId());
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_return:
                finish();
                break;
        }
    }

    public void buscarListaReproduccion(String id_lista_repro) {


        new Thread(() -> {
            new ServiceManager.PlayListByIdGET(new ServiceManager.PlayListByIdGET.OnResponseListener() {
                @Override
                public void onResponse(String response) {
                    runOnUiThread(() -> {

                        try {

                            JSONObject json_puro = new JSONObject(response);
                            //Sub rama del json puro
                            String nombre = json_puro.getString("title");
                            String descripcion = json_puro.getString("description");
                            String picture = json_puro.getString("picture");

                            String fans = json_puro.getString("fans");

                            JSONArray listaCanciones = json_puro.getJSONObject("tracks").getJSONArray("data");

                            int numeroCanciones = listaCanciones.length();

                            for (int i = 0; i < numeroCanciones; i++) {

                                JSONObject infoCancionTemporal = listaCanciones.getJSONObject(i);

                                String idCancion = infoCancionTemporal.getString("id");
                                String nombreCancion = infoCancionTemporal.getString("title");
                                String nombreArtista = infoCancionTemporal.getJSONObject("artist").getString("name");
                                String anioLanzamiento = "Anio de lanzamiento - FALTA";
                                //Imagen del album, ya que así busque el objeto canción con toda su info, esta no tiene img por si sola (tiene ref con album que es lo mismo de abajo)
                                String imagenCancion = infoCancionTemporal.getJSONObject("album").getString("cover");


                                Cancion cancionTmp = new Cancion();
                                cancionTmp.setId(idCancion);
                                cancionTmp.setNombreCancion(nombreCancion);
                                cancionTmp.setNombreArtista(nombreArtista);
                                cancionTmp.setAnioLanzamiento(anioLanzamiento);
                                cancionTmp.setImagen(Uri.parse(imagenCancion));

                                adapterTemplate_canciones.agregarCancion(cancionTmp);
                            }

                            Glide.with(findViewById(R.id.iv_cancion_imagen_lista_reproduccion)).load(Uri.parse(picture)).into((ImageView) findViewById(R.id.iv_cancion_imagen_lista_reproduccion));

                            listaReproduccionSeleccionada.setNombreLista(nombre);
                            listaReproduccionSeleccionada.setDescripcion(descripcion);

                            tv_cancion_nombre_play_list.setText(listaReproduccionSeleccionada.getNombreLista());
                            tv_cancion_numero_canciones.setText("(# canciones: " + numeroCanciones + ")");
                            tv_cancion_numero_fans.setText("(# fans: " + fans + ")");

                            if (descripcion == null || descripcion.trim().equals("")) {
                                descripcion = "Sin descripción";
                                tv_cancion_descripcion_play_list.setText(descripcion);
                            } else {
                                tv_cancion_descripcion_play_list.setText(listaReproduccionSeleccionada.getDescripcion());
                            }

                        } catch (JSONException e) {
                            //e.printStackTrace();
                            Log.e("Error JSON en canciones", e.getMessage());
                        }

                    });
                }
            }, id_lista_repro);
        }).start();


    }

    public void mostrarMensaje(String texto) {
        Toast.makeText(CancionActivity.this, texto, Toast.LENGTH_LONG).show();
    }
}
