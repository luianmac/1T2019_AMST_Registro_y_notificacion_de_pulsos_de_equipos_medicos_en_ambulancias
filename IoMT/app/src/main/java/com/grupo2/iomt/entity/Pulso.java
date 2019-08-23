package com.grupo2.iomt.entity;

/**
 * The type Pulso.
 * @author Allan Orellana
 * @version 1.0
 */
public class Pulso {
    /**
     * The Id.
     */
    int id;
    /**
     * The Nombre.
     */
    String nombre;
    /**
     * The Num pulso.
     */
    int num_pulso;
    /**
     * The Descripcion.
     */
    String descripcion;

    /**
     * Instantiates a new Pulso.
     *
     * @param id          the id
     * @param nombre      the nombre
     * @param num_pulso   the num pulso
     * @param descripcion the descripcion
     */
    public Pulso(int id, String nombre, int num_pulso, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.num_pulso = num_pulso;
        this.descripcion = descripcion;
    }

    /**
     * Instantiates a new Pulso.
     */
    public Pulso() {
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
     * Gets nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets nombre.
     *
     * @param nombre the nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets num pulso.
     *
     * @return the num pulso
     */
    public int getNum_pulso() {
        return num_pulso;
    }

    /**
     * Sets num pulso.
     *
     * @param num_pulso the num pulso
     */
    public void setNum_pulso(int num_pulso) {
        this.num_pulso = num_pulso;
    }

    /**
     * Gets descripcion.
     *
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets descripcion.
     *
     * @param descripcion the descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
