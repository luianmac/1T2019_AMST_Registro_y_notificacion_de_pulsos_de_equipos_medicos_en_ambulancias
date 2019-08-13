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

public class Ambulacia_listAdapter extends ArrayAdapter<String> {
    private final Activity context;
    //private final ArrayList<String> maintitle;
    //private final ArrayList<String> subtitle;
    //private final ArrayList<Integer> imgid;

    ArrayList <String> ambulaciaNums;
    ArrayList <String> ambulaciaPlacas;
    ArrayList <Boolean> ambulaciaOcupadas;
    ArrayList <String> ambulaciaNumConductores;
    ArrayList<Integer> ambulaciaImagenes;
    Integer selectedPos;
String token;
    public Ambulacia_listAdapter(Activity context, ArrayList ambulaciaNums, ArrayList ambulaciaPlacas, ArrayList ambulaciaOcupadas, ArrayList ambulaciaNumConductores, ArrayList ambulaciaImagenes, String token) {
        super(context, R.layout.ambulacia_list, ambulaciaNums);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.ambulaciaOcupadas = ambulaciaOcupadas;
        this.ambulaciaNums = ambulaciaNums;
        this.ambulaciaPlacas = ambulaciaPlacas;
        this.ambulaciaNumConductores = ambulaciaNumConductores;
        this.ambulaciaImagenes = ambulaciaImagenes;
        this.token = token;
    }

    public View getView(int position, View view, ViewGroup parent) {
        try {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.ambulacia_list, null, true);

            TextView titleText = (TextView) rowView.findViewById(R.id.title);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView placaText = (TextView) rowView.findViewById(R.id.Placa);
            TextView conductorText = (TextView) rowView.findViewById(R.id.Conductor);
            RadioButton ocupadoBoton = (RadioButton) rowView.findViewById(R.id.radioButton_estado);

            selectedPos = position;

            Boolean ocupado = ambulaciaOcupadas.get(position);
            ocupadoBoton.setChecked(ocupado);

            titleText.setText("Ambulacia: " + ambulaciaNums.get(position));
            imageView.setImageResource(ambulaciaImagenes.get(position));
            placaText.setText("Placa: " + ambulaciaPlacas.get(position));
            conductorText.setText("Conductor: " + ambulaciaNumConductores.get(position));
            rowView.setClickable(true);
            rowView.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               System.out.println("Aquillllllllllll");
                                               Intent pulsos = new Intent(context,pulsosDeAmbulancia.class);
                                               //System.out.println("ESSSSTOOOOO             " + position);
                                               pulsos.putExtra("idAmbulancia", Integer.parseInt(ambulaciaNums.get(selectedPos)));
                                               pulsos.putExtra("token", token);
                                               context.startActivity(pulsos);
                                           }
                                       }
            );
            return rowView;
        }
        catch(Exception e){
            e.printStackTrace();
            //System.out.println(e.getStackTrace());;
        }
        return new View(context);
    };
}
