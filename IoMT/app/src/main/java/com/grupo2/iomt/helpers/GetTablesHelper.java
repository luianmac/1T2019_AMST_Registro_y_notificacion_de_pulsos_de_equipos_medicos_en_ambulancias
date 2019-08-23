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

/**
 * The type Get tables helper.
 * @author Allan Orellana
 * @version 1.0
 */
public class GetTablesHelper {
    private ArrayList<RegistroPulso> registroPulsos;
    private ArrayList<Ambulancia> ambulancias;
    private ArrayList<Pulso> pulsos;
    private Map<String, String> params;
    private Map<String, String> prioridades;
    private Map<String, String> signalCode;
    private RequestQueue queue;
    private String token;
    private Context context;
    private Integer bateria;

    /**
     * Instantiates a new Get tables helper.
     *
     *
     * @param token   the token
     * @param context the context
     */
    public GetTablesHelper( String token, Context context) {
        prioridades = new HashMap<>();
        prioridades.put("Señal desconocida", "Baja");
        prioridades.put("Hiperpirexia", "Alta");
        prioridades.put("Presion arterial baja", "Media");
        prioridades.put("Arritmia", "Baja");
        prioridades.put("Paro cardiaco", "Alta");
        prioridades.put("Presion arterial alta", "Media");
        prioridades.put("Sin señal", "Baja");

        signalCode = new HashMap<>();
        signalCode.put("SED", "Desconocida");
        signalCode.put("HIP", "Hiperpirexia");
        signalCode.put("PAB", "Presion Arterial Baja");
        signalCode.put("ARR", "Arritmia");
        signalCode.put("PCA", "Paro Cardiaco");
        signalCode.put("PAA", "Presion Arterial Alta");

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

    /**
     * Get tables.
     *
     * @param urlRegistros  the url registros
     * @param urlPulsos     the url pulsos
     * @param urlAmbulancia the url ambulancia
     */
    public void getTables(String urlRegistros, String urlPulsos, String urlAmbulancia){
        registroPulsos.clear();
        pulsos.clear();
        ambulancias.clear();
        obtenerRegistros(urlRegistros);
        obtenerAmbulancias(urlAmbulancia);
        obtenerPulsos(urlPulsos);

    }


    /**
     * Obtener pulsos.
     *
     * @param url the url
     */
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
                                if(signalCode.containsKey(nombre))
                                    nombre = signalCode.get(nombre);
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


    /**
     * Obtener registros.
     *
     * @param url the url
     */
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

    /**
     * Obtener ambulancias.
     *
     * @param url the url
     */
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

    /**
     * Obtener bateria.
     *
     * @param url the url
     */
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

    /**
     * Get pulso pulso.
     *
     * @param array the array
     * @param id    the id
     * @return the pulso
     */
    public Pulso getPulso (ArrayList<Pulso> array, int id ){
        for (int i = 0; i<array.size(); i++){
            Pulso pulso= array.get(i);
            if(pulso.getId() == id)
                return pulso;
        }
        return new Pulso();
    }

    /**
     * Get ambulancia ambulancia.
     *
     * @param array the array
     * @param id    the id
     * @return the ambulancia
     */
    public Ambulancia getAmbulancia(ArrayList<Ambulancia> array, int id ){
        for (int i = 0; i<array.size(); i++){
            Ambulancia ambulancia= array.get(i);
            if(ambulancia.getId() == id)
                return ambulancia;
        }
        return new Ambulancia();
    }

    /**
     * Add ambulacia and pulso.
     *
     * @param registroPulsos the registro pulsos
     */
    public void addAmbulaciaAndPulso(ArrayList<RegistroPulso> registroPulsos){
        for (int i = 0; i <registroPulsos.size(); i++){
            RegistroPulso registroPulso = registroPulsos.get(i);
            Pulso pulso =  getPulso(pulsos, registroPulso.getId());
            Ambulancia ambulancia = getAmbulancia(ambulancias, registroPulso.getAmbulanciaID());
            registroPulso.setPulso(pulso);
            registroPulso.setAmbulancia(ambulancia);
        }
    }

    /**
     * Add ambulacia and pulso.
     */
    public void addAmbulaciaAndPulso(){
        for (int i = 0; i <registroPulsos.size(); i++){
            RegistroPulso registroPulso = registroPulsos.get(i);
            Pulso pulso =  getPulso(pulsos, registroPulso.getPulsoID());
            Ambulancia ambulancia = getAmbulancia(ambulancias, registroPulso.getAmbulanciaID());
            registroPulso.setPulso(pulso);
            registroPulso.setAmbulancia(ambulancia);
        }
    }


    /**
     * Gets registro pulsos.
     *
     * @return the registro pulsos
     */
    public ArrayList<RegistroPulso> getRegistroPulsos() {
        return registroPulsos;
    }

    /**
     * Sets registro pulsos.
     *
     * @param registroPulsos the registro pulsos
     */
    public void setRegistroPulsos(ArrayList<RegistroPulso> registroPulsos) {
        this.registroPulsos = registroPulsos;
    }

    /**
     * Gets ambulancias.
     *
     * @return the ambulancias
     */
    public ArrayList<Ambulancia> getAmbulancias() {
        return ambulancias;
    }

    /**
     * Sets ambulancias.
     *
     * @param ambulancias the ambulancias
     */
    public void setAmbulancias(ArrayList<Ambulancia> ambulancias) {
        this.ambulancias = ambulancias;
    }

    /**
     * Gets pulsos.
     *
     * @return the pulsos
     */
    public ArrayList<Pulso> getPulsos() {
        return pulsos;
    }

    /**
     * Sets pulsos.
     *
     * @param pulsos the pulsos
     */
    public void setPulsos(ArrayList<Pulso> pulsos) {
        this.pulsos = pulsos;
    }

    /**
     * Gets params.
     *
     * @return the params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * Sets params.
     *
     * @param params the params
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * Gets prioridades.
     *
     * @return the prioridades
     */
    public Map<String, String> getPrioridades() {
        return prioridades;
    }

    /**
     * Sets prioridades.
     *
     * @param prioridades the prioridades
     */
    public void setPrioridades(Map<String, String> prioridades) {
        this.prioridades = prioridades;
    }

    /**
     * Get signalCode map.
     *
     * @return the map
     */
    public Map<String, String> getSignalCode() {
        return signalCode;
    }

    /**
     * Set codseñales.
     *
     * @param signalCode the signalCode
     */
    public void setSignalCode(Map<String, String> signalCode) {
        this.signalCode = signalCode;
    }

    /**
     * Gets queue.
     *
     * @return the queue
     */
    public RequestQueue getQueue() {
        return queue;
    }

    /**
     * Sets queue.
     *
     * @param queue the queue
     */
    public void setQueue(RequestQueue queue) {
        this.queue = queue;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     *
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets bateria.
     *
     * @return the bateria
     */
    public Integer getBateria() {
        return bateria;
    }

    /**
     * Sets bateria.
     *
     * @param bateria the bateria
     */
    public void setBateria(Integer bateria) {
        this.bateria = bateria;
    }
}
