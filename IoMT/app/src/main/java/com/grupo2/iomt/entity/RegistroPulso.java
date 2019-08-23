package com.grupo2.iomt.entity;

/**
 * The type Registro pulso.
 * @author Allan Orellana
 * @version 1.0
 */
public class RegistroPulso {
    /**
     * The Id.
     */
    int id;
    /**
     * The Fecha.
     */
    String fecha;
    /**
     * The Hora.
     */
    String hora;
    /**
     * The Pulso id.
     */
    int pulsoID;
    /**
     * The Ambulancia id.
     */
    int ambulanciaID;
    /**
     * The Ambulancia.
     */
    Ambulancia ambulancia;
    /**
     * The Pulso.
     */
    Pulso pulso;

    /**
     * Instantiates a new Registro pulso.
     *
     * @param id         the id
     * @param fecha      the fecha
     * @param hora       the hora
     * @param pulso      the pulso
     * @param ambulancia the ambulancia
     */
    public RegistroPulso(int id, String fecha, String hora, int pulso, int ambulancia) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.pulsoID = pulso;
        this.ambulanciaID = ambulancia;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets fecha.
     *
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Sets fecha.
     *
     * @param fecha the fecha
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Gets hora.
     *
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * Sets hora.
     *
     * @param hora the hora
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * Gets pulso.
     *
     * @return the pulso
     */
    public Pulso getPulso() {
        return pulso;
    }

    /**
     * Sets pulso.
     *
     * @param pulso the pulso
     */
    public void setPulso(Pulso pulso) {
        this.pulso = pulso;
    }

    /**
     * Gets ambulancia.
     *
     * @return the ambulancia
     */
    public Ambulancia getAmbulancia() {
        return ambulancia;
    }

    /**
     * Sets ambulancia.
     *
     * @param ambulancia the ambulancia
     */
    public void setAmbulancia(Ambulancia ambulancia) {
        this.ambulancia = ambulancia;
    }

    /**
     * Gets pulso id.
     *
     * @return the pulso id
     */
    public int getPulsoID() {
        return pulsoID;
    }

    /**
     * Sets pulso id.
     *
     * @param pulsoID the pulso id
     */
    public void setPulsoID(int pulsoID) {
        this.pulsoID = pulsoID;
    }

    /**
     * Gets ambulancia id.
     *
     * @return the ambulancia id
     */
    public int getAmbulanciaID() {
        return ambulanciaID;
    }

    /**
     * Sets ambulancia id.
     *
     * @param ambulanciaID the ambulancia id
     */
    public void setAmbulanciaID(int ambulanciaID) {
        this.ambulanciaID = ambulanciaID;
    }
}
