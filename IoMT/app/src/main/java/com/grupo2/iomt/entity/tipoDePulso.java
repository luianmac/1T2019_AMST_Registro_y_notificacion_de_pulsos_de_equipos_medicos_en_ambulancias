package com.grupo2.iomt.entity;

public class tipoDePulso {

    public int id;
    public String nombre;
    public int numPulsos;
    public String descripcion;

    public tipoDePulso() {
    }

    public tipoDePulso(int id, String nombre, int numPulsos, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.numPulsos = numPulsos;
        this.descripcion = descripcion;
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

    public int getNumPulsos() {
        return numPulsos;
    }

    public void setNumPulsos(int numPulsos) {
        this.numPulsos = numPulsos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
