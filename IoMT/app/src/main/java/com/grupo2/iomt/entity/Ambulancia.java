package com.grupo2.iomt.entity;

//Clase creada con los mismos atributos definidos en la tabla para almacenar
//los datos que se capturan de la base de datos

public class Ambulancia {
    private int id;
    private String placa;
    private  Boolean ocupado;
    private int conductor;

    public Ambulancia() {
    }

    public Ambulancia(int id, String placa, Boolean ocupado, int conductor) {
        this.id = id;
        this.placa = placa;
        this.ocupado = ocupado;
        this.conductor = conductor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Boolean getOcupado() {
        return ocupado;
    }

    public void setOcupado(Boolean ocupado) {
        this.ocupado = ocupado;
    }

    public int getConductor() {
        return conductor;
    }

    public void setConductor(int conductor) {
        this.conductor = conductor;
    }

    @Override
    public String toString() {
        return  "ID:        " + id + "\n" +
                "Placa:     " + placa + "\n" +
                "Ocupado:   " + ocupado + "\n" +
                "Conductor: " + conductor;
    }
}
