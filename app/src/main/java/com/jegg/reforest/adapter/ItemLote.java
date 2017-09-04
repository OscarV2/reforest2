package com.jegg.reforest.adapter;

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

    public int getNumArboles() {
        return numArboles;
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
