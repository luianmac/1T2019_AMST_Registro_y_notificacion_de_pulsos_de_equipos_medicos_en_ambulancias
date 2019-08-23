package com.grupo2.iomt.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.grupo2.iomt.R;
import com.grupo2.iomt.entity.Ambulancia;
import com.grupo2.iomt.entity.Pulso;
import com.grupo2.iomt.entity.RegistroPulso;
import com.grupo2.iomt.helpers.GetTablesHelper;

import java.util.ArrayList;

/**
 * The type Table registros pulsos activity.
 * @author Allan Orellana
 * @version 1.0
 */
public class Table_Registros_Pulsos_Activity extends AppCompatActivity {

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
     * The Handler.
     */
    Handler handler;
    /**
     * The Runnable.
     */
    Runnable runnable;
    /**
     * The Get tables helper.
     */
    GetTablesHelper getTablesHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table__registros__pulsos_);

        token = getIntent().getExtras().getString("token");
        getTablesHelper = new GetTablesHelper(token, getApplicationContext());

        registroPulsos = new ArrayList<>();
        ambulancias = new ArrayList<>();
        pulsos = new ArrayList<>();

        table = (TableLayout) findViewById(R.id.table1);

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
     * Actualizar.
     */
    public void actualizar(){
        getTablesHelper.getTables(urlRegistoPulsos, urlPulsos, urlAmbulancia);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.title_Table)).setVisibility(View.VISIBLE);
                getTablesHelper.addAmbulaciaAndPulso();
                registroPulsos = getTablesHelper.getRegistroPulsos();
                table.removeAllViews();

                crearTablaRegistros(registroPulsos);
                //init_values_Barchart(barChart);
            }
        }, 2000);
    }


    /**
     * Add row.
     *
     * @param table the table
     * @param cells the cells
     * @param id    the id
     */
    public void addRow(final TableLayout table, String[] cells, final int id){
        TableRow row = new TableRow(getApplicationContext());
        //row.setLayoutParams(table.getLayoutParams());
        row.setGravity(Gravity.CENTER_HORIZONTAL);
        row.setPadding(0,0,0,20);

        //row.setLayoutParams(new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
        if(id >= 0) {
            row.setClickable(true);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        RegistroPulso registroPulso = registroPulsos.get(id);
                        System.out.println(registroPulso.getHora());
                        showInfo(id);
                    }catch (Exception e){}

                }
            });
        }
        for (int i = 0; i< cells.length; i++){
            String value = cells[i];
            TextView textView = new TextView(getApplicationContext());
            textView.setGravity(Gravity.CENTER);
            textView.setText(value);
            textView.setTextColor(Color.BLACK);
            //textView.setCompoundDrawablePadding(10);
            textView.setPaddingRelative(10,0,10,0);
            //textView.setLayoutParams(table.getLayoutParams());
            row.addView(textView);
        }
        table.addView(row);
    }


    /**
     * Crear tabla registros.
     *
     * @param registroPulsos the registro pulsos
     */
    public void crearTablaRegistros(ArrayList<RegistroPulso> registroPulsos){
        addRow(table, new String[]{"Señal", "Fecha", "Hora"}, -1);
        for (int i = registroPulsos.size() -1 ; i >= 0; i --){
            RegistroPulso registroPulso = registroPulsos.get(i);
            String fecha = registroPulso.getFecha();
            String hora = registroPulso.getHora();
            String nombre = registroPulso.getPulso().getNombre();
            addRow(table, new String[]{nombre, fecha, hora}, i);
            //table.setColumnCollapsed(i, true);

        }
        //table.setStretchAllColumns(true);

    }

    /**
     * Show info.
     *
     * @param id the id
     */
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

        title.setText(registroPulso.getPulso().getNombre());

        date.setText("Señal obtenidad el " + registroPulso.getFecha() + " a la " + registroPulso.getHora());
        ambulancia.setText("Ambulancia: " + String.valueOf(registroPulso.getAmbulanciaID()));
        placa.setText("Placa: " + registroPulso.getAmbulancia().getPlaca());
        conductor.setText("Conductor: " + String.valueOf(registroPulso.getAmbulancia().getConductor()));
        try {
            System.out.println(registroPulso.getPulso().getNombre());
            prioridad.setText("Prioridad " + getTablesHelper.getPrioridades().get(registroPulso.getPulso().getNombre()));
        } catch (Exception e) {
            prioridad.setText("Prioridad " + "Baja");
        }

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Table_Registros_Pulsos_Activity.this);
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
