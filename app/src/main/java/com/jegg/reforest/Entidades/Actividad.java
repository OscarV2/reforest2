package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

@DatabaseTable(tableName = Constantes.TABLA_ACTIVIDADES)
public class Actividad {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_ACTIVIDADES)
    private int id;

    @DatabaseField(columnName = Constantes.NOMBRE_ACTIVIDADES, canBeNull = false)
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Actividad(String nombre) {

        this.nombre = nombre;
    }

    public Actividad() {

    }
}
