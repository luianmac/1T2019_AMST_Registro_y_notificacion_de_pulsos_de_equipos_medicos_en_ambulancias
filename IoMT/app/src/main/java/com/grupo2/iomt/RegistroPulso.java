package com.grupo2.iomt;

public class RegistroPulso {
    int id;
    String fecah;
    String hora;
    int pulsoID;
    int ambulanciaID;
    Ambulancia ambulancia;
    Pulso pulso;

    public RegistroPulso(int id, String fecah, String hora, int pulso, int ambulancia) {
        this.id = id;
        this.fecah = fecah;
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

    public String getFecah() {
        return fecah;
    }

    public void setFecah(String fecah) {
        this.fecah = fecah;
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
