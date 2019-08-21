package com.grupo2.iomt;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Ambulancia_pulsos_listAdapter extends ArrayAdapter<String> {

    private final Activity context;
    //private final ArrayList<String> maintitle;
    //private final ArrayList<String> subtitle;
    //private final ArrayList<Integer> imgid;

    ArrayList<String> tiposPulsos;
    ArrayList <String> fechas;
    ArrayList <String> descripciones;
    ArrayList<Integer> prioridadesImagenes;
    Integer selectedPos;
    public Ambulancia_pulsos_listAdapter(Activity context, ArrayList tiposPulsos, ArrayList fechas, ArrayList descripciones, ArrayList prioridadesImagenes) {
        super(context, R.layout.ambulacia_list, tiposPulsos);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.tiposPulsos = tiposPulsos;
        this.fechas = fechas;
        this.descripciones = descripciones;
        this.prioridadesImagenes = prioridadesImagenes;

    }

    public View getView(int position, View view, ViewGroup parent) {
        try {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.pulsos_ambulancias_list, null, true);

            TextView titleText = (TextView) rowView.findViewById(R.id.title_list);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon_prioridad);
            TextView decripcionText = (TextView) rowView.findViewById(R.id.textView_descipcion);
            TextView fechaText = (TextView) rowView.findViewById(R.id.textView_fecha_list);


            selectedPos = position;


            titleText.setText(tiposPulsos.get(position));
            imageView.setImageResource(prioridadesImagenes.get(position));
            decripcionText.setText(descripciones.get(position));
            fechaText.setText(fechas.get(position));

            return rowView;
        }
        catch(Exception e){
            e.printStackTrace();
            //System.out.println(e.getStackTrace());;
        }
        return new View(context);
    };
}
