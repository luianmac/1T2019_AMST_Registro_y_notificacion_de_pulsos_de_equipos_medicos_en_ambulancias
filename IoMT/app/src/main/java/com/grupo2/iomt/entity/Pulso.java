package com.grupo2.iomt.entity;

public class Pulso {
    int id;
    String nombre;
    int num_pulso;
    String descripcion;

    public Pulso(int id, String nombre, int num_pulso, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.num_pulso = num_pulso;
        this.descripcion = descripcion;
    }

    public Pulso() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNum_pulso() {
        return num_pulso;
    }

    public void setNum_pulso(int num_pulso) {
        this.num_pulso = num_pulso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
