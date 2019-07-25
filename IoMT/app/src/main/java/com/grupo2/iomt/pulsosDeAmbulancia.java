package com.grupo2.iomt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.grupo2.iomt.entity.Ambulancia;
import com.grupo2.iomt.entity.registroPulsos;
import com.grupo2.iomt.entity.tipoDePulso;
import com.grupo2.iomt.entity.visualizarPulsoApp;

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
     ArrayAdapter<visualizarPulsoApp> adapterPulsos;
     ArrayList<visualizarPulsoApp> arrayListPulsos = new ArrayList<>();
     ArrayList<registroPulsos> arrayRegistroPulsos = new ArrayList<>();
     ArrayList<tipoDePulso> arrayTipoPulso =  new ArrayList<>();
     RequestQueue mQueue;
    private int id = 0;
    int socketTimeout = 3000;

    public pulsosDeAmbulancia() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsos_de_ambulancia);
        Intent ambulancia = getIntent();
        this.idAmbulancia = (Integer) ambulancia.getExtras().get("idAmbulancia");
        this.token = (String) ambulancia.getExtras().get("token");
        mQueue = Volley.newRequestQueue(this);
        listPulsos = (ListView) findViewById(R.id.listViewPulsos);

        arrayListPulsos = new ArrayList<visualizarPulsoApp>();
        adapterPulsos = new ArrayAdapter<visualizarPulsoApp>(getApplicationContext(), R.layout.custom_layout, arrayListPulsos);

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
            for (int i = 0; i < 3; i++){
                hilo();
            }
            return true;
        }
        //Método que permite que la clase vuelva a ser llamada y que siga ejecutandose
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            obtenerPulso();
            obtenerDetallePulso();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    mostrarActivity();
                }
            },  2000);
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
                            arrayRegistroPulsos.clear();
                            System.out.println("PRUEBA:" +response.toString());
                            //String linea = "id, camion, origen, destino\n\n";
                            for(int i = 0; i<response.length(); i++){
                                JSONObject j = (JSONObject) response.get(i);
                                //linea = linea +""+j.getString("id")+", "+j.getString("camion")+", "+j.getString("origen")+", "+j.getString("destino")+"\n\n";
                                //if(j.getInt("ambulancia") == idAmbulancia){

                                String[] cadenaSplit = j.getString("fecha_registro").split("T");
                                String fecha = cadenaSplit[0];
                                String hora = cadenaSplit[1].split("\\.")[0];

                                    arrayRegistroPulsos.add(new registroPulsos(j.getInt("id"), fecha, hora, j.getInt("pulso"), j.getInt("ambulancia")));
                                    id = j.getInt("pulso");
                                System.out.println(arrayRegistroPulsos.size()+"\n\n\n\n");
                                //}

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
        };
        /*
        for(int i = 0; i < arrayRegistroPulsos.size(); i++){
            System.out.println(arrayListPulsos.get(i).getPulso());
        }
        */

        mQueue.add(request);
        //adapterPulsos.notifyDataSetChanged();
    }

    private void obtenerDetallePulso(){
        String url_temp = "https://amstdb.herokuapp.com/db/pulsos";

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url_temp, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //TextView txt = (TextView) findViewById(R.id.txtData);
                        try {
                            arrayTipoPulso.clear();
                            System.out.println("PRUEBA:" +response.toString());
                            //String linea = "id, camion, origen, destino\n\n";
                            for(int i = 0; i<response.length(); i++){
                                JSONObject j = (JSONObject) response.get(i);
                                //linea = linea +""+j.getString("id")+", "+j.getString("camion")+", "+j.getString("origen")+", "+j.getString("destino")+"\n\n";
                                //if(j.getInt("ambulancia") == idAmbulancia){
                                    arrayTipoPulso.add(new tipoDePulso(j.getInt("id"), j.getString("nombre"), j.getInt("numero_pulsos"), j.getString("descripcion")));
                                    //i = j.getInt("id");
                                System.out.println(arrayTipoPulso.size()+"\n\n\n");
                                //}

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
        };
        //RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        //request.setRetryPolicy(policy);
        mQueue.add(request);
        //adapterPulsos.notifyDataSetChanged();
    }

    private void mostrarActivity(){
        arrayListPulsos.clear();
        for(int i = 0; i<arrayRegistroPulsos.size(); i++){
            for(int j = 0; j < arrayTipoPulso.size(); j++) {
                //idPulso = arrayTipoPulso.get(i).getId();
                System.out.println(arrayRegistroPulsos.get(i).getFecha() + "\n\n\n");
                if (arrayTipoPulso.get(j).getId() == arrayRegistroPulsos.get(i).getId_pulso()) {

                    arrayListPulsos.add(new visualizarPulsoApp(arrayRegistroPulsos.get(i).getId(), arrayRegistroPulsos.get(i).getFecha(), arrayRegistroPulsos.get(i).getHora(), arrayTipoPulso.get(i).getNombre(), arrayRegistroPulsos.get(i).getId_ambulancia()));
                }
            }
        }
        adapterPulsos.notifyDataSetChanged();
    }
}
