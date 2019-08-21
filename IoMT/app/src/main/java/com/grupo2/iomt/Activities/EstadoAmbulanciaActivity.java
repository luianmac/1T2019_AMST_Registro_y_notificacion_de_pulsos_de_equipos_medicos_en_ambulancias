package com.grupo2.iomt.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.grupo2.iomt.R;
import com.grupo2.iomt.list_adapters.Ambulacia_listAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EstadoAmbulanciaActivity extends AppCompatActivity {
    //Declaración de variables a usar
    private Button btn;
    private ListView list;
    //private ArrayAdapter<Ambulancia> adapter;
    private RequestQueue mQueue;
    private String token = "";

    private ArrayList <String> ambulaciaNums;
    private ArrayList <String> ambulaciaPlacas;
    private ArrayList <Boolean> ambulaciaOcupadas;
    private ArrayList <String> ambulaciaNumConductores;
    private ArrayList<Integer> ambulaciaImagenes;
    private Ambulacia_listAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_ambulancia);
        mQueue = Volley.newRequestQueue(this);
        Intent login = getIntent();
        this.token = (String) login.getExtras().get("token");

        btn = (Button) findViewById(R.id.button);
        list = (ListView) findViewById(R.id.listView);

        ambulaciaNums = new ArrayList<>();
        ambulaciaPlacas = new ArrayList<>();
        ambulaciaOcupadas = new ArrayList<>();
        ambulaciaNumConductores = new ArrayList<>();

        ambulaciaImagenes = new ArrayList<>();

        adapter = new Ambulacia_listAdapter(this, ambulaciaNums, ambulaciaPlacas, ambulaciaOcupadas, ambulaciaNumConductores,ambulaciaImagenes, token);
        list=(ListView)findViewById(R.id.listView);
        list.setClickable(true);
        list.setAdapter(adapter);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar();
            }
        });
    }

    /*Método que permite la conexión a la base de datos y capturar los datos
     *los cuales son almacenados en un array para presentarlos mediante un ListView
     */
    private void actualizar(){

        String url_temp = "https://amstdb.herokuapp.com/db/ambulancia";
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, url_temp, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ambulaciaNums.clear();
                            ambulaciaImagenes.clear();
                            ambulaciaOcupadas.clear();
                            ambulaciaPlacas.clear();
                            ambulaciaNumConductores.clear();

                            System.out.println("PRUEBA:" +response.toString());
                            for(int i = 0; i<response.length(); i++){
                                JSONObject j = (JSONObject) response.get(i);
                                String placa = j.getString("placa");
                                String numeroAmbulacia = String.valueOf(j.getInt("id"));
                                Boolean ocupado = j.getBoolean("ocupado");
                                String numConductor = String.valueOf(j.getInt("conductor"));

                                ambulaciaNums.add(numeroAmbulacia);
                                ambulaciaPlacas.add(placa);
                                ambulaciaOcupadas.add(ocupado);
                                ambulaciaNumConductores.add(numConductor);
                                ambulaciaImagenes.add(R.drawable.ambulancia);


                                adapter.notifyDataSetChanged();

                                //arrayList.add(new Ambulancia(j.getInt("id"), j.getString("placa"), j.getBoolean("ocupado"), j.getInt("conductor")));
                            }
                        } catch (Exception e) {
                            System.out.println("errooooorrrrrrr");
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
    }
}