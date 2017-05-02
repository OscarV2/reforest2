package com.jegg.reforest.Utils;

/**
 * Created by oscarvc on 2/05/17.
 */

public class ItemLote {

    private String nombre, fecha;

    public ItemLote(String nombre, String fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
