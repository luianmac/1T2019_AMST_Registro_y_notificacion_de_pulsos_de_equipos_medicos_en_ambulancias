package com.grupo2.iomt.entity;

public class visualizarPulsoApp {

    private int id;
    private String fecha;
    private String hora;
    private String pulso;
    private int ambulancia;



    public visualizarPulsoApp(int id, String fecha, String hora, String pulso, int ambulancia) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.pulso = pulso;
        this.ambulancia = ambulancia;
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

    public String getPulso() {
        return pulso;
    }

    public void setPulso(String pulso) {
        this.pulso = pulso;
    }

    public int getAmbulancia() {
        return ambulancia;
    }

    public void setAmbulancia(int ambulancia) {
        this.ambulancia = ambulancia;
    }

    @Override
    public String toString() {
        return  "ID:        " + id + "\n" +
                "Fecha:     " + fecha + "\n" +
                "Hora:      " + hora + "\n" +
                "Pulso:     " + pulso + "\n" +
                "Ambulancia:" + ambulancia;
    }
}
