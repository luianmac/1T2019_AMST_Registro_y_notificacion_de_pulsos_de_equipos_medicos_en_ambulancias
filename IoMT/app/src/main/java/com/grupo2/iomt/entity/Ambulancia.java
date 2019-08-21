package com.grupo2.iomt;

public class Ambulancia {
    int id;
    String placa;
    boolean ocupado;
    int conductor;

    public Ambulancia(int id, String placa, boolean ocupado, int conductor) {
        this.id = id;
        this.placa = placa;
        this.ocupado = ocupado;
        this.conductor = conductor;
    }

    public Ambulancia() {}

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

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public int getConductor() {
        return conductor;
    }

    public void setConductor(int conductor) {
        this.conductor = conductor;
    }
}
