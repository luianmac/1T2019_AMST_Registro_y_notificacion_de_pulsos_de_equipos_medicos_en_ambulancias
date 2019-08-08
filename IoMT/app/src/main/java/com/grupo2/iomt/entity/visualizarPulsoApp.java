package com.grupo2.iomt.entity;

public class visualizarPulsoApp {

    private String nombrePulso;
    private String fecha;
    private String hora;
    private String descripcion;
    private int numPulsos;

    public visualizarPulsoApp(String nombrePulso, String fecha, String hora, String descripcion, int numPulsos) {
        this.nombrePulso = nombrePulso;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.numPulsos = numPulsos;
    }


    public String getNombrePulso() {
        return nombrePulso;
    }

    public void setNombrePulso(String nombrePulso) {
        this.nombrePulso = nombrePulso;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumPulsos() {
        return numPulsos;
    }

    public void setNumPulsos(int numPulsos) {
        this.numPulsos = numPulsos;
    }

    @Override
    public String toString() {
        return  "Nombre de Pulso: " + nombrePulso + "\n" +
                "Fecha:           " + fecha + "\n" +
                "Hora:            " + hora + "\n" +
                "Descripcion:     " + descripcion + "\n" +
                "Numero de Pulsos " + numPulsos;
    }
}
