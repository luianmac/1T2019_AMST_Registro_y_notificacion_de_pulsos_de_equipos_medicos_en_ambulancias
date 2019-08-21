package com.grupo2.iomt.entity;

public class RegistroPulso {
    int id;
    String fecha;
    String hora;
    int pulsoID;
    int ambulanciaID;
    Ambulancia ambulancia;
    Pulso pulso;

    public RegistroPulso(int id, String fecha, String hora, int pulso, int ambulancia) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.pulsoID = pulso;
        this.ambulanciaID = ambulancia;
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

    public Pulso getPulso() {
        return pulso;
    }

    public void setPulso(Pulso pulso) {
        this.pulso = pulso;
    }

    public Ambulancia getAmbulancia() {
        return ambulancia;
    }

    public void setAmbulancia(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
    }

    public int getPulsoID() {
        return pulsoID;
    }

    public void setPulsoID(int pulsoID) {
        this.pulsoID = pulsoID;
    }

    public int getAmbulanciaID() {
        return ambulanciaID;
    }

    public void setAmbulanciaID(int ambulanciaID) {
        this.ambulanciaID = ambulanciaID;
    }
}
