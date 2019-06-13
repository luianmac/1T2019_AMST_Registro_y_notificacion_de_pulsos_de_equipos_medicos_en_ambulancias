package com.grupo2.iomt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View; //Es necesario importer las librerias
import android.content.Intent;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    /*La funcion IrEstadoAmbulancia cambia la vista del teléfono entre activity
    */
    public void irEstadoAmbulancia(View v){
        Intent estadoAmbulancia = new Intent(getBaseContext(), estadoAmbulancia.class);
        startActivity(estadoAmbulancia);
    }
}
