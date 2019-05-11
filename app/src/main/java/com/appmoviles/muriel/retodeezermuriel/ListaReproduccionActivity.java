package com.appmoviles.muriel.retodeezermuriel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.appmoviles.muriel.retodeezermuriel.adapters.AdapterTemplate_Listas_Reproduccion;
import com.appmoviles.muriel.retodeezermuriel.model.ListaReproduccion;
import com.appmoviles.muriel.retodeezermuriel.services.ServiceManager;
import com.bumptech.glide.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static com.appmoviles.muriel.retodeezermuriel.constantes.Constantes.ID_LISTA_REPRODUCCION;

public class ListaReproduccionActivity extends AppCompatActivity implements AdapterTemplate_Listas_Reproduccion.OnItemClickListener, View.OnClickListener {

    private RecyclerView rv_listas_reproduccion;
    private AdapterTemplate_Listas_Reproduccion adapterTemplate_listas_reproduccion;

    private EditText et_buscar_lista_reproduccion;
    private ImageButton ib_lista_reproduccion_buscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reproduccion);

        et_buscar_lista_reproduccion = findViewById(R.id.et_buscar_lista_reproduccion);
        et_buscar_lista_reproduccion.setOnClickListener(this);

        ib_lista_reproduccion_buscar = findViewById(R.id.ib_lista_reproduccion_buscar);
        ib_lista_reproduccion_buscar.setOnClickListener(this);

        rv_listas_reproduccion = findViewById(R.id.rv_listas_reproduccion);
        adapterTemplate_listas_reproduccion = new AdapterTemplate_Listas_Reproduccion();
        rv_listas_reproduccion.setHasFixedSize(true);
        rv_listas_reproduccion.setLayoutManager(new LinearLayoutManager(ListaReproduccionActivity.this));

        rv_listas_reproduccion.setAdapter(adapterTemplate_listas_reproduccion);
        adapterTemplate_listas_reproduccion.setListener(ListaReproduccionActivity.this);


        /**
         //Datos de prueba
         for (int i = 0; i < 10; i++) {
         ListaReproduccion tmp = new ListaReproduccion();
         tmp.setNombreLista("nombre lista " + i);
         tmp.setNombreUsuarioCreador("nombre usuario " + i);
         tmp.setTotalCanciones("" + i );
         adapterTemplate_listas_reproduccion.agregarListaReproduccion(tmp);
         }
         */

        buscarPlayList("Adele");

    }

    @Override
    public void onItemClick(ListaReproduccion listaReproduccion) {
        Intent i = new Intent(ListaReproduccionActivity.this, CancionActivity.class);
        i.putExtra(ID_LISTA_REPRODUCCION, listaReproduccion.getId());
        startActivity(i);
    }

    public void mostrarMensaje(String texto) {
        Toast.makeText(ListaReproduccionActivity.this, texto, Toast.LENGTH_LONG).show();
    }


    public void buscarPlayList(String busqueda) {

        new Thread(() -> {
            new ServiceManager.SearchPlayListGET(new ServiceManager.SearchPlayListGET.OnResponseListener() {
                @Override
                public void onResponse(String response) {
                    runOnUiThread(() -> {

                        try {

                            //TODO EL JSON
                            JSONObject json_puro = new JSONObject(response);
                            //Sub rama del json puro
                            JSONArray playList = json_puro.optJSONArray("data");

                            for (int i = 0; i < playList.length(); i++) {
                                JSONObject platListTmp = playList.getJSONObject(i);
                                String tituloPlayList = platListTmp.getString("title");
                                String totalCanciones = platListTmp.getString("nb_tracks");
                                String idPlayList = platListTmp.getString("id");

                                JSONObject usuarioCreador = platListTmp.getJSONObject("user");
                                String nombreCreador = usuarioCreador.getString("name");
                                String imagenPlayList = platListTmp.getString("picture");

                                ListaReproduccion listaReproduccionTmp = new ListaReproduccion();
                                listaReproduccionTmp.setNombreLista(tituloPlayList);
                                listaReproduccionTmp.setTotalCanciones(totalCanciones);
                                listaReproduccionTmp.setNombreUsuarioCreador(nombreCreador);
                                listaReproduccionTmp.setImagen(Uri.parse(imagenPlayList));

                                listaReproduccionTmp.setId(idPlayList);

                                adapterTemplate_listas_reproduccion.agregarListaReproduccion(listaReproduccionTmp);
                            }


                        } catch (JSONException e) {
                            //e.printStackTrace();
                            Log.e("Error JSON Listas Repro", e.getMessage());
                        }

                    });
                }
            }, busqueda);
        }).start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.ib_lista_reproduccion_buscar:

                if(et_buscar_lista_reproduccion.getText().toString() != null && !et_buscar_lista_reproduccion.getText().toString().trim().equals("")){

                    adapterTemplate_listas_reproduccion.vaciarLista();
                    String busqueda = et_buscar_lista_reproduccion.getText().toString();
                    buscarPlayList(busqueda);
                }
                else{
                    mostrarMensaje("Debe ingresar un nombre válido");
                }

                break;

        }
    }
}