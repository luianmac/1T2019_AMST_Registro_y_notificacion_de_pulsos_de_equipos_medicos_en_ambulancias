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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class estadoAmbulancia extends AppCompatActivity {

    //private EditText editTxt;
    private Button btn;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
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

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_layout, arrayList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "este es " + list.getItemAtPosition(position), Toast.LENGTH_LONG).show();
            }
        });

       /* btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /*arrayList.add(editTxt.getText().toString());
                adapter.notifyDataSetChanged();
                editTxt.setText(" ");
            }
        });
    }

    /*private void revisarAmbulancias(String url, final ArrayList<String> arrayList){
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            arrayList.add("Placa: "+response.getString("plca")+"\n"+"Ocupado:    "+response.getString("ocupado")+"\n"+"Conductor        : "+response.getString("Conductor"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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
        mQueue.add(request);
    }*/
    }
        public void actualizar(View view){
            // for(int i=1; i<3; i++) {
            //
            String url = "https://amstdb.herokuapp.com/db/ambulancia/2";
            //revisarAmbulancias(url, arrayList);


            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response);
                            try {
                                arrayList.add("Placa: " + response.getString("placa") + "\n" + "Ocupado:    " + response.getString("ocupado") + "\n" + "Conductor        : " + response.getString("conductor"));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "JWT " + token);
                    System.out.println(token);
                    return params;
                }
            };
            mQueue.add(request);

            adapter.notifyDataSetChanged();
           // arrayList.clear();

        }
    }


