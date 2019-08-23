package com.grupo2.iomt.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TableLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.grupo2.iomt.R;
import com.grupo2.iomt.entity.Ambulancia;
import com.grupo2.iomt.entity.Pulso;
import com.grupo2.iomt.entity.RegistroPulso;
import com.grupo2.iomt.helpers.GetTablesHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The type Grafico pulsos activity.
 * @author Allan Orellana
 * @version 1.0
 */
public class GraficoPulsosActivity extends AppCompatActivity {
    /**
     * The Bar chart.
     */
    BarChart barChart;
    /**
     * The Token.
     */
    String token;
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
     * The Table.
     */
    TableLayout table;
    /**
     * The Registro pulsos.
     */
    ArrayList<RegistroPulso> registroPulsos;
    /**
     * The Ambulancias.
     */
    ArrayList<Ambulancia> ambulancias;
    /**
     * The Pulsos.
     */
    ArrayList<Pulso> pulsos;
    /**
     * The Get tables helper.
     */
    GetTablesHelper getTablesHelper;

    /**
     * The Handler.
     */
    Handler handler;
    /**
     * The Runnable.
     */
    Runnable runnable;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico_pulsos);

        token = getIntent().getExtras().getString("token");

        getTablesHelper = new GetTablesHelper(token, getApplicationContext());


        barChart = (BarChart)findViewById(R.id.barchart);
        table = (TableLayout) findViewById(R.id.table1);

        registroPulsos = new ArrayList<>();
        ambulancias = new ArrayList<>();
        pulsos = new ArrayList<>();


        init_Barchart(barChart);

        handler = new Handler();
        runnable = new Runnable() {
           @Override
           public void run() {
               registroPulsos = new ArrayList<>();
               ambulancias = new ArrayList<>();
               pulsos = new ArrayList<>();
               actualizar();
               handler.postDelayed(this, 10000);
            }
       };
        runnable.run();

    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(runnable);

        super.onBackPressed();

    }

    /**
     * Ir detalles.
     *
     * @param v the v
     */
    public void irDetalles(View v){
        handler.removeCallbacks(runnable);
        Intent intent = new Intent(getApplicationContext(), Table_Registros_Pulsos_Activity.class);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    /**
     * Actualizar.
     */
    public void actualizar(){
        System.out.println("Acccccccccc");
        getTablesHelper.getTables(urlRegistoPulsos, urlPulsos, urlAmbulancia);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getTablesHelper.addAmbulaciaAndPulso();
                registroPulsos = getTablesHelper.getRegistroPulsos();

                System.out.println(registroPulsos);
                Map<String, Integer> data = contarPulsos(registroPulsos);
                setDataBarchart(barChart, data);
                barChart.notifyDataSetChanged();
                barChart.invalidate();
                findViewById(R.id.btnDetalles).setVisibility(View.VISIBLE);

            }
        }, 3000);
    }

    /**
     * Set data barchart.
     *
     * @param barChart the bar chart
     * @param map      the map
     */
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

    /**
     * Add labels barchart.
     *
     * @param barChart the bar chart
     * @param labels   the labels
     */
    public void add_labels_Barchart(BarChart barChart, String[] labels){
        IndexAxisValueFormatter indexFormatter = new IndexAxisValueFormatter();
        indexFormatter.setValues(labels);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(indexFormatter);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(true);
        xAxis.setLabelRotationAngle(45);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setGranularityEnabled(true);

        xAxis.setXOffset(-20);

    }

    /**
     * Init barchart.
     *
     * @param barChart the bar chart
     */
    public void init_Barchart(BarChart barChart){
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(false);
        //barChart.setMaxVisibleValueCount(50);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        barChart.getLegend().setEnabled(false);
        barChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

    }


    /**
     * Contar pulsos map.
     *
     * @param registroPulsos the registro pulsos
     * @return the map
     */
    public Map<String, Integer> contarPulsos(ArrayList<RegistroPulso> registroPulsos){
        Map<String, Integer> contador = new HashMap<>();
        for (int i = 0; i<registroPulsos.size(); i++){
            RegistroPulso registroPulso = registroPulsos.get(i);
            Pulso pulso = registroPulso.getPulso();
            String nombre = pulso.getNombre();
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




}
