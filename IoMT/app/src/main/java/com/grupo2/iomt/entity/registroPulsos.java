package com.grupo2.iomt.entity;

//Clase creada con los mismos atributos definidos en la tabla para almacenar
//los datos que se capturan de la base de datos

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class registroPulsos {

    private int id;
    private String fecha_registro;
    private int id_pulso;
    private int id_ambulancia;

    public registroPulsos() {
    }

    public registroPulsos(int id, String fecha_registro, int id_pulso, int id_ambulancia) {
        this.id = id;
        this.fecha_registro = fecha_registro;
        this.id_pulso = id_pulso;
        this.id_ambulancia = id_ambulancia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
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

    @Override
    public String toString() {

        //ZonedDateTime zdt = ZonedDateTime.parse(fecha_registro);
        //String newFormat = zdt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm"));

        return  "ID:                    " + id + "\n" +
                "Fecha de Registro:     " + fecha_registro + "\n" +
                "Pulso:                 " + id_pulso + "\n" +
                "Ambulancia:            " + id_ambulancia;
    }
}
