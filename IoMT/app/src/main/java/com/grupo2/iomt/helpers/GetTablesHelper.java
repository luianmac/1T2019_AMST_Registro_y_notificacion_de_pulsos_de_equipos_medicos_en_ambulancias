package com.grupo2.iomt.helpers;

import android.content.Context;
import android.os.Handler;

import androidx.room.Room;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.grupo2.iomt.db.DB;
import com.grupo2.iomt.entity.Ambulancia;
import com.grupo2.iomt.entity.Pulso;
import com.grupo2.iomt.entity.RegistroPulso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetTablesHelper {
    private ArrayList<RegistroPulso> registroPulsos;
    private ArrayList<Ambulancia> ambulancias;
    private ArrayList<Pulso> pulsos;
    private Map<String, String> params;
    private Map<String, String> prioridades;
    private Map<String, String> codseñales;
    private RequestQueue queue;
    private String token;
    private Context context;
    private Integer bateria;

    public GetTablesHelper( String token, Context context) {
        prioridades = new HashMap<>();
        prioridades.put("Señal desconocida", "Baja");
        prioridades.put("Hiperpirexia", "Alta");
        prioridades.put("Presion arterial baja", "Media");
        prioridades.put("Arritmia", "Baja");
        prioridades.put("Paro cardiaco", "Alta");
        prioridades.put("Presion arterial alta", "Media");
        prioridades.put("Sin señal", "Baja");

        codseñales = new HashMap<>();
        codseñales.put("SED", "Desconocida");
        codseñales.put("HIP", "Hiperpirexia");
        codseñales.put("PAB", "Presion Arterial Baja");
        codseñales.put("ARR", "Arritmia");
        codseñales.put("PCA", "Paro Cardiaco");
        codseñales.put("PAA", "Presion Arterial Alta");

        queue = Volley.newRequestQueue(context);

        params = new HashMap<String, String>();
        params.put("Authorization", "JWT " + token);

        token = token;
        context = context;
        bateria = 0;

        registroPulsos = new ArrayList<>();
        pulsos = new ArrayList<>();
        ambulancias = new ArrayList<>();

    }

    private DB instanceDB(String name){
        DB db = Room.databaseBuilder(this.context, DB.class, name)
                .allowMainThreadQueries()
                .build();
        return db;
    }

    public void getTables(String urlRegistros, String urlPulsos, String urlAmbulancia){
        registroPulsos.clear();
        pulsos.clear();
        ambulancias.clear();
        obtenerRegistros(urlRegistros);
        obtenerAmbulancias(urlAmbulancia);
        obtenerPulsos(urlPulsos);

    }


    public void obtenerPulsos(String url){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++){
                            try {
                                JSONObject pulsoJ = response.getJSONObject(i);
                                int id = pulsoJ.getInt("id");
                                String nombre = pulsoJ.getString("nombre");
                                if(codseñales.containsKey(nombre))
                                    nombre = codseñales.get(nombre);
                                int  numero_pulsos = pulsoJ.getInt("numero_pulsos");
                                String descripcion = pulsoJ.getString("descripcion");
                                Pulso pulso = new Pulso(id,nombre,numero_pulsos,descripcion);
                                pulsos.add(pulso);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Erooor respuesta");
                System.out.println(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return params;
            }
        };
        queue.add(request);
    }


    public void obtenerRegistros(String url){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for(int i = 0; i<response.length(); i++){
                                JSONObject registro = response.getJSONObject(i);

                                String[] cadenaSplit = registro.getString("fecha_registro").split("T");
                                String fecha = cadenaSplit[0];
                                String hora = cadenaSplit[1].split("\\.")[0];

                                int pulso = registro.getInt("pulso");
                                int ambulancia = registro.getInt("ambulancia");
                                int id = registro.getInt("id");

                                RegistroPulso registroPulso = new RegistroPulso(id,fecha,hora,pulso,ambulancia);
                                registroPulsos.add(registroPulso);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return params;
            }
        };
        queue.add(request);
    }

    public void obtenerAmbulancias(String url){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url , null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i<response.length(); i++){
                            try {
                                JSONObject ambulanciaJ = response.getJSONObject(i);
                                int id = ambulanciaJ.getInt("id");
                                String placa = ambulanciaJ.getString("placa");
                                boolean ocuapdo = ambulanciaJ.getBoolean("ocupado");
                                int conductor = ambulanciaJ.getInt("conductor");
                                Ambulancia ambulancia = new Ambulancia(id,placa, ocuapdo,conductor);
                                ambulancias.add(ambulancia);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Erooor respuesta");
                System.out.println(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return params;
            }
        };
        queue.add(request);
    }

    public void obtenerBateria(String url){
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            bateria = (response.getInt("bateria"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOR");
                System.out.println(error);
            }
        }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                return params;
            }
        };
        queue.add(request);
    }

    public Pulso getPulso (ArrayList<Pulso> array, int id ){
        for (int i = 0; i<array.size(); i++){
            Pulso pulso= array.get(i);
            if(pulso.getId() == id)
                return pulso;
        }
        return new Pulso();
    }
    public Ambulancia getAmbulancia(ArrayList<Ambulancia> array, int id ){
        for (int i = 0; i<array.size(); i++){
            Ambulancia ambulancia= array.get(i);
            if(ambulancia.getId() == id)
                return ambulancia;
        }
        return new Ambulancia();
    }

    public void addAmbulaciaAndPulso(ArrayList<RegistroPulso> registroPulsos){
        for (int i = 0; i <registroPulsos.size(); i++){
            RegistroPulso registroPulso = registroPulsos.get(i);
            Pulso pulso =  getPulso(pulsos, registroPulso.getId());
            Ambulancia ambulancia = getAmbulancia(ambulancias, registroPulso.getAmbulanciaID());
            registroPulso.setPulso(pulso);
            registroPulso.setAmbulancia(ambulancia);
        }
    }
    public void addAmbulaciaAndPulso(){
        for (int i = 0; i <registroPulsos.size(); i++){
            RegistroPulso registroPulso = registroPulsos.get(i);
            Pulso pulso =  getPulso(pulsos, registroPulso.getPulsoID());
            Ambulancia ambulancia = getAmbulancia(ambulancias, registroPulso.getAmbulanciaID());
            registroPulso.setPulso(pulso);
            registroPulso.setAmbulancia(ambulancia);
        }
    }



    public ArrayList<RegistroPulso> getRegistroPulsos() {
        return registroPulsos;
    }

    public void setRegistroPulsos(ArrayList<RegistroPulso> registroPulsos) {
        this.registroPulsos = registroPulsos;
    }

    public ArrayList<Ambulancia> getAmbulancias() {
        return ambulancias;
    }

    public void setAmbulancias(ArrayList<Ambulancia> ambulancias) {
        this.ambulancias = ambulancias;
    }

    public ArrayList<Pulso> getPulsos() {
        return pulsos;
    }

    public void setPulsos(ArrayList<Pulso> pulsos) {
        this.pulsos = pulsos;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getPrioridades() {
        return prioridades;
    }

    public void setPrioridades(Map<String, String> prioridades) {
        this.prioridades = prioridades;
    }

    public Map<String, String> getCodseñales() {
        return codseñales;
    }

    public void setCodseñales(Map<String, String> codseñales) {
        this.codseñales = codseñales;
    }

    public RequestQueue getQueue() {
        return queue;
    }

    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getBateria() {
        return bateria;
    }

    public void setBateria(Integer bateria) {
        this.bateria = bateria;
    }
}
