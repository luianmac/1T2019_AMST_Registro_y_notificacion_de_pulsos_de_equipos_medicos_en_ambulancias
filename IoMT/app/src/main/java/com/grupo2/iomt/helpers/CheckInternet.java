package com.grupo2.iomt.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;

public class CheckInternet {

    /*
    Autor: Allan Orellana
    Parametros: ninguno
    Descripcion: Realiza un ping (ICMP) al servidor DNS de Google. Basta con tener una respuesta para saber si hay conexion con Internet
    Devuelve: True si hay error en la conexion, y false si no lo hay.
     */
    public static boolean errorConexion() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return !(exitValue == 0);  //True si no hay respuesta y False si sí la hay
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return true; // en caso de exister un error en la conexion
    }


}
