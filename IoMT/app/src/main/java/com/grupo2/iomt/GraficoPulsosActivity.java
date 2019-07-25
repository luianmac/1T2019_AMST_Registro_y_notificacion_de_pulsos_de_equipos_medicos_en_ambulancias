package com.grupo2.iomt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GraficoPulsosActivity extends Activity {
    BarChart barChart;
    String token;
    String urlRegistoPulsos = "https://amstdb.herokuapp.com/db/registroDePulsos";
    String urlAmbulancia = "https://amstdb.herokuapp.com/db/ambulancia";
    String urlPulsos = "https://amstdb.herokuapp.com/db/pulsos";
    RequestQueue queue;
    TableLayout table;
    ArrayList<RegistroPulso> registroPulsos;
    ArrayList<Ambulancia> ambulancias;
    ArrayList<Pulso> pulsos;
    Map<String, String> params;
    Map<String, String> prioridades;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_pulsos);

        token = getIntent().getExtras().getString("token");
        queue = Volley.newRequestQueue(this);

        params = new HashMap<String, String>();
        params.put("Authorization", "JWT " + token);

        barChart = (BarChart)findViewById(R.id.barchart);
        table = (TableLayout) findViewById(R.id.table1);

        registroPulsos = new ArrayList<>();
        ambulancias = new ArrayList<>();
        pulsos = new ArrayList<>();

        prioridades = new HashMap<>();
        prioridades.put("SED", "Baja");
        prioridades.put("HIP", "Alta");
        prioridades.put("PAB", "Medio");
        prioridades.put("ARR", "Baja");
        prioridades.put("PCA", "Media");
            prioridades.put("PAA", "Alta");

        obtenerRegistros();
        obtenerAmbulancias();
        obtenerPulsos();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                addAmbulaciaAndPulso(registroPulsos);
                ((TextView) findViewById(R.id.title_Table)).setVisibility(View.VISIBLE);
                crearTablaRegistros(registroPulsos);
                Map<String, Integer> data = contarPulsos(registroPulsos);
                setDataBarchart(barChart, data);
                init_Barchart(barChart);
                barChart.notifyDataSetChanged();
                barChart.invalidate();
                //init_values_Barchart(barChart);


            }
        }, 2000);
    }
    public void setDataBarchart(BarChart barChart, Map<String, Integer> map){
            String[] labels = new String[map.keySet().size()];
            ArrayList<BarEntry> barEntries = new ArrayList<>();

            int maxValue = 0;
            Iterator <Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
            int counter = 0;
            while (iterator.hasNext()){
                Map.Entry<String, Integer> i = iterator.next();
                String key = i.getKey();
                Integer value = i.getValue();
                barEntries.add(new BarEntry(counter, value));

                labels[counter] = key;
                counter ++;
                if (value > maxValue)
                    maxValue = value;
            }
            BarDataSet barDataSet = new BarDataSet(barEntries, "Pulsos");
            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);


            BarData barData = new BarData(barDataSet);
            //barData.setBarWidth(0.9f);

            barChart.setData(barData);
            add_labels_Barchart(barChart, labels);

    }

    public void add_labels_Barchart(BarChart barChart, String[] labels){
        IndexAxisValueFormatter indexFormatter = new IndexAxisValueFormatter();
        indexFormatter.setValues(labels);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(indexFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(true);

    }
    public void init_Barchart(BarChart barChart){
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(false);
        //barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
    }

    public void obtenerRegistros(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlRegistoPulsos, null,
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

    public void obtenerAmbulancias(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlAmbulancia , null,
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


    public void obtenerPulsos(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, urlPulsos, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++){
                            try {
                                JSONObject pulsoJ = response.getJSONObject(i);
                                int id = pulsoJ.getInt("id");
                                String nombre = pulsoJ.getString("nombre");
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

    public Map<String, Integer> contarPulsos(ArrayList<RegistroPulso> registroPulsos){
        Map<String, Integer> contador = new HashMap<>();
        for (int i = 0; i<registroPulsos.size(); i++){
            RegistroPulso registroPulso = registroPulsos.get(i);
            Pulso pulso = registroPulso.getPulso();
            String nombre = pulso.nombre;
            try{
                Integer value = contador.get(nombre) + 1;
                //contador.remove(nombre);
                contador.put(nombre,value);
            }
            catch (Exception e){
                contador.put(nombre,1);
            }
        }
        return contador;
    }

    public void addRow(final TableLayout table, String[] cells, final int id){
        TableRow row = new TableRow(getApplicationContext());
        row.setGravity(Gravity.CENTER);
        row.setPadding(0,0,0,20);
        if(id >= 0) {
            row.setClickable(true);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RegistroPulso registroPulso = registroPulsos.get(id);
                    System.out.println(registroPulso.hora);
                    showInfo(id);
                }
            });
        }
        for (int i = 0; i< cells.length; i++){
            String value = cells[i];
            TextView textView = new TextView(getApplicationContext());
            textView.setGravity(Gravity.CENTER);
            textView.setText(value);
            textView.setTextColor(Color.BLACK);
            textView.setPaddingRelative(130,0,130,0);
            row.addView(textView);
        }
        table.addView(row);
    }

    public Ambulancia getAmbulancia(ArrayList<Ambulancia> array, int id ){
            for (int i = 0; i<array.size(); i++){
                Ambulancia ambulancia= array.get(i);
                if(ambulancia.id == id)
                    return ambulancia;
            }
            return new Ambulancia();
    }
    public Pulso getPulso (ArrayList<Pulso> array, int id ){
        for (int i = 0; i<array.size(); i++){
            Pulso pulso= array.get(i);
            if(pulso.id == id)
                return pulso;
        }
        return new Pulso();
    }

    public void addAmbulaciaAndPulso(ArrayList<RegistroPulso> registroPulsos){
            for (int i = 0; i <registroPulsos.size(); i++){
                RegistroPulso registroPulso = registroPulsos.get(i);
                Pulso pulso =  getPulso(pulsos, registroPulso.pulsoID);
                Ambulancia ambulancia = getAmbulancia(ambulancias, registroPulso.ambulanciaID);
                registroPulso.setPulso(pulso);
                registroPulso.setAmbulancia(ambulancia);
            }
    }

    public void crearTablaRegistros(ArrayList<RegistroPulso> registroPulsos){
            addRow(table, new String[]{"Señal", "Fecha", "Hora"}, -1);
            for (int i = registroPulsos.size() -1 ; i >= 0; i --){
                RegistroPulso registroPulso = registroPulsos.get(i);
                String fecha = registroPulso.getFecah();
                String hora = registroPulso.getHora();
                String nombre = registroPulso.getPulso().getNombre();
                addRow(table, new String[]{nombre, fecha, hora}, i);
                table.setColumnStretchable(i, true);
            }
            table.setStretchAllColumns(true);

    }
    public void showInfo(int id){
        RegistroPulso registroPulso = registroPulsos.get(id);
        View popup = getLayoutInflater().inflate(R.layout.dialog_info_registo_pulso, null);
        TextView title = (TextView) popup.findViewById(R.id.textView_Title);
        TextView prioridad = (TextView) popup.findViewById(R.id.textView_Prioridad);
        TextView date = (TextView) popup.findViewById(R.id.textView_Date);
        TextView ambulancia = (TextView) popup.findViewById(R.id.textView_Ambulancia);
        TextView placa = (TextView) popup.findViewById(R.id.textView_Placa);
        TextView conductor = (TextView) popup.findViewById(R.id.textView_Conductor);
        Button okButton = (Button) popup.findViewById(R.id.button_Ok);

        title.setText(registroPulso.pulso.nombre);

        date.setText("Señal obtenidad el " + registroPulso.fecah + " a la " + registroPulso.hora);
        ambulancia.setText("Ambulancia: " + String.valueOf(registroPulso.ambulanciaID));
        placa.setText("Placa: " + registroPulso.ambulancia.placa);
        conductor.setText("Conductor: " + String.valueOf(registroPulso.ambulancia.conductor));
        try{
            prioridad.setText("Prioridad " + prioridades.get(registroPulso.pulso.nombre));
        }
        catch (Exception e){
            prioridad.setText("Prioridad " + "Baja");
        }

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(GraficoPulsosActivity.this);
        mBuilder.setView(popup);
        final AlertDialog dialog = mBuilder.create();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
