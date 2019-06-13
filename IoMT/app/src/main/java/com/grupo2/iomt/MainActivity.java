package com.grupo2.iomt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    }

    public void IniciarSesion(View view){
        Intent intent =new Intent(this,Menu.class);
        startActivity(intent);
    }
}
