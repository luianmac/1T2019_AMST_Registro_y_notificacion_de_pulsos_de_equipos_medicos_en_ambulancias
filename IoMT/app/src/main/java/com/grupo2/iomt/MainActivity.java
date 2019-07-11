package com.grupo2.iomt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.grupo2.iomt.dao.TokenDao;
import com.grupo2.iomt.db.DB;
import com.grupo2.iomt.entity.Token;

import java.io.IOException;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    private EditText txtUser, txtPasswd;
    private Button btnLogin, btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        * Variables creadas referenciadas a los controles
        * Richard*/
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPasswd = (EditText) findViewById(R.id.txtPasswd);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);


        /* Ejemplo para usar base de datos

        DB db = instanceDB("mainDB");
        TokenDao a = db.getTokenDAO();
        Token aaa =  new Token("ahj");
        a.insert(aaa);
        a.getItems();

        */
    }

    public void IniciarSesion(View view){
        if(CheckInternet.errorConexion()){
            Toast.makeText(this, "No hay conexion a Internet", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent =new Intent(this,Menu.class);
            startActivity(intent);
        }

    }
    private DB instanceDB(String name){
        DB db = Room.databaseBuilder(this, DB.class, name)
                .allowMainThreadQueries()
                .build();
        return db;
    }




}
