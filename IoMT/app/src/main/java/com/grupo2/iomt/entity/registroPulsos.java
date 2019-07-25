package com.grupo2.iomt.entity;

//Clase creada con los mismos atributos definidos en la tabla para almacenar
//los datos que se capturan de la base de datos

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class registroPulsos {

    private int id;
    private String fecha;
    private String hora;
    private int id_pulso;
    private int id_ambulancia;

    public registroPulsos() {
    }

    public registroPulsos(int id, String fecha, String hora, int id_pulso, int id_ambulancia) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.id_pulso = id_pulso;
        this.id_ambulancia = id_ambulancia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getId_pulso() {
        return id_pulso;
    }

    public void setId_pulso(int id_pulso) {
        this.id_pulso = id_pulso;
    }

    public int getId_ambulancia() {
        return id_ambulancia;
    }

    public void setId_ambulancia(int id_ambulancia) {
        this.id_ambulancia = id_ambulancia;
    }

    /*
    @Override
    public String toString() {

        //ZonedDateTime zdt = ZonedDateTime.parse(fecha_registro);
        //String newFormat = zdt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"));

        return  "ID:                    " + id + "\n" +
                "Fecha de Registro:     " + fecha_registro + "\n" +
                "Pulso:                 " + id_pulso + "\n" +
                "Ambulancia:            " + id_ambulancia;
    }
    */
}
