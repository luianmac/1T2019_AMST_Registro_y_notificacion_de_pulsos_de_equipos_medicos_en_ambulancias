package com.grupo2.iomt.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.grupo2.iomt.R;
import com.grupo2.iomt.entity.Pulso;
import com.grupo2.iomt.entity.RegistroPulso;
import com.grupo2.iomt.entity.visualizarPulsoApp;
import com.grupo2.iomt.helpers.GetTablesHelper;
import com.grupo2.iomt.list_adapters.Ambulancia_pulsos_listAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Pulsos de ambulancia activity.
 * @author Allan Orellana
 * @author Richard Ruales
 * @version 2.0
 */
public class PulsosDeAmbulanciaActivity extends AppCompatActivity {
    /**
     * The Id ambulancia.
     */
//Declaracion de variables a usar
    int idAmbulancia;
    /**
     * The Token.
     */
    String token;
    /**
     * The Array registro pulsos.
     */
    ArrayList<RegistroPulso> arrayRegistroPulsos = new ArrayList<>();
    /**
     * The Tiempo.
     */
    Tiempo tiempo;
    /**
     * The Tipos pulsos.
     */
    ArrayList<String> tiposPulsos;
    /**
     * The Fechas.
     */
    ArrayList <String> fechas;
    /**
     * The Descripciones.
     */
    ArrayList <String> descripciones;
    /**
     * The Prioridades imagenes.
     */
    ArrayList<Integer> prioridadesImagenes;
    /**
     * The Prioridades.
     */
    Map<String, String> prioridades;
    private Ambulancia_pulsos_listAdapter adapter;
    /**
     * The List.
     */
    ListView list;
    /**
     * The Get tables helper.
     */
    GetTablesHelper getTablesHelper;
    /**
     * The Url registo pulsos.
     */
    String urlRegistoPulsos = "https://amstdb.herokuapp.com/db/registroDePulsos";
    /**
     * The Url ambulancia.
     */
    String urlAmbulancia = "https://amstdb.herokuapp.com/db/ambulancia";
    /**
     * The Url pulsos.
     */
    String urlPulsos = "https://amstdb.herokuapp.com/db/pulsos";

    /**
     * Instantiates a new Pulsos de ambulancia activity.
     */
    public PulsosDeAmbulanciaActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsos_de_ambulancia);

        Intent ambulancia = getIntent();
        this.idAmbulancia = (Integer) ambulancia.getExtras().get("idAmbulancia");
        this.token = (String) ambulancia.getExtras().get("token");

        getTablesHelper = new GetTablesHelper(token, this);
        prioridades = getTablesHelper.getPrioridades();

        tiposPulsos = new ArrayList<>();
        fechas = new ArrayList<>();
        descripciones = new ArrayList<>();
        prioridadesImagenes = new ArrayList<>();

        adapter = new Ambulancia_pulsos_listAdapter(this, tiposPulsos,fechas,descripciones,prioridadesImagenes);
        list=(ListView)findViewById(R.id.listViewPulsos);
        list.setClickable(true);
        list.setAdapter(adapter);

        Tiempo tiempo = new Tiempo();
        tiempo.execute();

    }

    /**
     * Hilo.
     */
//Método que permite poner en espera cada segundo
    public void hilo(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ejecutar.
     */
    public void ejecutar(){
        tiempo = new Tiempo();
        tiempo.execute();

    }


    /**
     * The type Tiempo.
     */
//Clase para ser ejecutada en segundo plano
    public class Tiempo extends AsyncTask<Void,Integer,Boolean>{
        //Método para validar que se cumpla el método hilo()
        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = 0; i < 3; i++){
                hilo();
            }
            return true;
        }
        //Método que permite que la clase vuelva a ser llamada y que siga ejecutandose
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            getTablesHelper.getTables(urlRegistoPulsos, urlPulsos, urlAmbulancia);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getTablesHelper.addAmbulaciaAndPulso();
                    arrayRegistroPulsos = getTablesHelper.getRegistroPulsos();
                    crearLista();
                }
            },  1500);
        }
    }

    private void crearLista(){
        tiposPulsos.clear();
        fechas.clear();
        descripciones.clear();
        prioridadesImagenes.clear();
        for (int i = 0; i<arrayRegistroPulsos.size(); i++ ){
            RegistroPulso registroPulso = arrayRegistroPulsos.get(i);
            Pulso pulso = registroPulso.getPulso();
            tiposPulsos.add(pulso.getNombre());
            fechas.add(registroPulso.getFecha());
            descripciones.add(pulso.getDescripcion());
            String nombrePulso = pulso.getNombre();
            String prioridad;

            try {
                prioridad = prioridades.get(nombrePulso);
            }catch (Exception e){
                prioridad = "Desconocida";
            }
            if (prioridad.equals("Baja") || prioridad.equals("Desconocida") ){
                prioridadesImagenes.add(R.drawable.prioridad_baja);
            }
            else if (prioridad.equals("Media")){
                prioridadesImagenes.add(R.drawable.prioridad_media);
            }
            else{
                prioridadesImagenes.add(R.drawable.prioridad_alta);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tiempo.cancel(true);
    }
}