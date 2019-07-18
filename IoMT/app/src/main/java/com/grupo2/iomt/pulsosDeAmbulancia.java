package com.grupo2.iomt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.grupo2.iomt.entity.Ambulancia;
import com.grupo2.iomt.entity.registroPulsos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class pulsosDeAmbulancia extends AppCompatActivity {
    //Declaracion de variables a usar
    int idAmbulancia = 0;
    String token = "";
    int idPrueba = 4;
    private ListView listPulsos;
    private ArrayAdapter<registroPulsos> adapterPulsos;
    private ArrayList<registroPulsos> arrayListPulsos;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsos_de_ambulancia);
        Intent ambulancia = getIntent();
        this.idAmbulancia = (Integer) ambulancia.getExtras().get("idAmbulancia");
        this.token = (String) ambulancia.getExtras().get("token");
        mQueue = Volley.newRequestQueue(this);
        listPulsos = (ListView) findViewById(R.id.listViewPulsos);

        arrayListPulsos = new ArrayList<registroPulsos>();
        adapterPulsos = new ArrayAdapter<registroPulsos>(getApplicationContext(), R.layout.custom_layout, arrayListPulsos);

        listPulsos.setAdapter(adapterPulsos);

        Tiempo tiempo = new Tiempo();
        tiempo.execute();

    }
    //Método que permite poner en espera cada segundo
    public void hilo(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ejecutar(){
        Tiempo tiempo = new Tiempo();
        tiempo.execute();
    }

    //Clase para ser ejecutada en segundo plano
    public class Tiempo extends AsyncTask<Void,Integer,Boolean>{
        //Método para validar que se cumpla el método hilo()
        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = 0; i < 2; i++){
                hilo();
            }
            return true;
        }
        //Método que permite que la clase vuelva a ser llamada y que siga ejecutandose
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            obtenerPulso();
        }
    }
    /*  Método que permite la conexión a la base de datos y se captura todos los atributos
    *   validando con el id de ambulancia que se obtiene de la anterior actividad.
    */
    private void obtenerPulso() {

        String url_temp = "https://amstdb.herokuapp.com/db/registroDePulsos";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url_temp, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //TextView txt = (TextView) findViewById(R.id.txtData);
                        try {
                            arrayListPulsos.clear();
                            System.out.println("PRUEBA:" +response.toString());
                            //String linea = "id, camion, origen, destino\n\n";
                            for(int i = 0; i<response.length(); i++){
                                JSONObject j = (JSONObject) response.get(i);
                                //linea = linea +""+j.getString("id")+", "+j.getString("camion")+", "+j.getString("origen")+", "+j.getString("destino")+"\n\n";
                                if(j.getInt("ambulancia") == idPrueba){
                                    arrayListPulsos.add(new registroPulsos(j.getInt("id"), j.getString("fecha_registro"), j.getInt("pulso"), j.getInt("ambulancia")));
                                }

                                //arrayListPulsos.add(new Ambulancia(j.getInt("id"), j.getString("placa"), j.getBoolean("ocupado"), j.getInt("conductor")));
                            }

                            // txt.setText(linea);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("NO ES POSIBLE OBTENER DATA");
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "JWT " + token);
                System.out.println(token);
                return params;
            }
        };;
        mQueue.add(request);
        adapterPulsos.notifyDataSetChanged();
    }
}
