package com.grupo2.iomt.entity;

/**
 * The type Visualizar pulso app.
 * @author Richard Ruales
 * @version 1.0
 */
public class visualizarPulsoApp {

    private String nombrePulso;
    private String fecha;
    private String hora;
    private String descripcion;
    private int numPulsos;

    /**
     * Instantiates a new Visualizar pulso app.
     *
     * @param nombrePulso the nombre pulso
     * @param fecha       the fecha
     * @param hora        the hora
     * @param descripcion the descripcion
     * @param numPulsos   the num pulsos
     */
    public visualizarPulsoApp(String nombrePulso, String fecha, String hora, String descripcion, int numPulsos) {
        this.nombrePulso = nombrePulso;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.numPulsos = numPulsos;
    }


    /**
     * Gets nombre pulso.
     *
     * @return the nombre pulso
     */
    public String getNombrePulso() {
        return nombrePulso;
    }

    /**
     * Sets nombre pulso.
     *
     * @param nombrePulso the nombre pulso
     */
    public void setNombrePulso(String nombrePulso) {
        this.nombrePulso = nombrePulso;
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

    /**
     * Gets num pulsos.
     *
     * @return the num pulsos
     */
    public int getNumPulsos() {
        return numPulsos;
    }

    /**
     * Sets num pulsos.
     *
     * @param numPulsos the num pulsos
     */
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
