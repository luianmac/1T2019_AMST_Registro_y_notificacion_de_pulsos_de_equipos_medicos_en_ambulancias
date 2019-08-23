package com.grupo2.iomt.entity;

/**
 * The type Ambulancia.
 * @author Allan Orellana
 * @version 1.0
 */
public class Ambulancia {
    /**
     * The Id.
     */
    int id;
    /**
     * The Placa.
     */
    String placa;
    /**
     * The Ocupado.
     */
    boolean ocupado;
    /**
     * The Conductor.
     */
    int conductor;

    /**
     * Instantiates a new Ambulancia.
     *
     * @param id        the id
     * @param placa     the placa
     * @param ocupado   the ocupado
     * @param conductor the conductor
     */
    public Ambulancia(int id, String placa, boolean ocupado, int conductor) {
        this.id = id;
        this.placa = placa;
        this.ocupado = ocupado;
        this.conductor = conductor;
    }

    /**
     * Instantiates a new Ambulancia.
     */
    public Ambulancia() {}

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
     * Gets placa.
     *
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Sets placa.
     *
     * @param placa the placa
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Is ocupado boolean.
     *
     * @return the boolean
     */
    public boolean isOcupado() {
        return ocupado;
    }

    /**
     * Sets ocupado.
     *
     * @param ocupado the ocupado
     */
    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    /**
     * Gets conductor.
     *
     * @return the conductor
     */
    public int getConductor() {
        return conductor;
    }

    /**
     * Sets conductor.
     *
     * @param conductor the conductor
     */
    public void setConductor(int conductor) {
        this.conductor = conductor;
    }
}
