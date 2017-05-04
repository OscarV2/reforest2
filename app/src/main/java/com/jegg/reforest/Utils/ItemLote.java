package com.jegg.reforest.Utils;

/**
 * Created by oscarvc on 2/05/17.
 */

public class ItemLote {

    private String nombre, fecha;
    private int numArboles;
    private double area;

    public ItemLote(String nombre, String fecha, int numArboles, double area) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.numArboles = numArboles;
        this.area = area;
    }

    public String getNombre() {
        return nombre;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getNumArboles() {
        return numArboles;
    }

    public void setNumArboles(int numArboles) {
        this.numArboles = numArboles;
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
