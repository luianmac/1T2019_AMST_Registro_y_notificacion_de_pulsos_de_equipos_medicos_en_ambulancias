package com.grupo2.iomt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.grupo2.iomt.entity.Ambulancia;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class estadoAmbulancia extends AppCompatActivity {
    //Declaración de variables a usar
    private Button btn;
    private ListView list;
    private ArrayAdapter<Ambulancia> adapter;
    private ArrayList<Ambulancia> arrayList;
    private RequestQueue mQueue;
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_ambulancia);
        mQueue = Volley.newRequestQueue(this);
        Intent login = getIntent();
        this.token = (String) login.getExtras().get("token");

        btn = (Button) findViewById(R.id.button);
        list = (ListView) findViewById(R.id.listView);

        arrayList = new ArrayList<Ambulancia>();

        adapter = new ArrayAdapter<Ambulancia>(getApplicationContext(), R.layout.custom_layout, arrayList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "este es " + list.getItemAtPosition(position), Toast.LENGTH_LONG).show();
                Intent pulsos = new Intent(getBaseContext(),pulsosDeAmbulancia.class);
                //System.out.println("ESSSSTOOOOO             " + position);
                pulsos.putExtra("idAmbulancia", position);
                pulsos.putExtra("token", token);
                startActivity(pulsos);

            }
        });

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
                            arrayList.clear();
                            System.out.println("PRUEBA:" +response.toString());
                            for(int i = 0; i<response.length(); i++){
                                JSONObject j = (JSONObject) response.get(i);
                                arrayList.add(new Ambulancia(j.getInt("id"), j.getString("placa"), j.getBoolean("ocupado"), j.getInt("conductor")));
                            }
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
        adapter.notifyDataSetChanged();
    }
}