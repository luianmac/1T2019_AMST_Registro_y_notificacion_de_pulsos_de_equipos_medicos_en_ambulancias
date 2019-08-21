package com.grupo2.iomt.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View; //Es necesario importer las librerias
import android.content.Intent;
import android.widget.Toast;

import com.grupo2.iomt.R;
import com.grupo2.iomt.dao.TokenDao;
import com.grupo2.iomt.db.DB;
import com.grupo2.iomt.entity.Token;

public class MenuActivity extends AppCompatActivity {
    String token ;
    DB db;
    TokenDao tokenDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent login = getIntent();
        this.token = (String)login.getExtras().get("token");

        db = instanceDB("mainDB");
        tokenDAO = db.getTokenDAO();
    }
    /*La funcion IrEstadoAmbulancia cambia la vista del teléfono entre activity
    */
    public void irEstadoAmbulancia(View v){
        Intent estado = new Intent(getBaseContext(), EstadoAmbulanciaActivity.class);
        estado.putExtra("token", token);
        startActivity(estado);
    }
    public void irGraficoPulsos(View v){
        Intent graficos = new Intent(getBaseContext(),GraficoPulsosActivity.class);
        graficos.putExtra("token", token);
        startActivity(graficos);
    }

    public void salir(View v){
        this.finishAffinity();
        Toast toast = Toast.makeText(getApplicationContext(), "APP finalizada", Toast.LENGTH_LONG);
        toast.show();

    }

    public void cerrarSesion(View v){
        Token token_DB = tokenDAO.getTokenByToken(token);
        tokenDAO.delete(token_DB);
        Intent Main = new Intent(getBaseContext(),MainActivity.class);
        startActivity(Main);
    }

    private DB instanceDB(String name){
        DB db = Room.databaseBuilder(this, DB.class, name)
                .allowMainThreadQueries()
                .build();
        return db;
    }
}
