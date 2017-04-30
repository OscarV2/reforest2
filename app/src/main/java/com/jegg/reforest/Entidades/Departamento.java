package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

/**
 * Created by oscarvc on 29/04/17.
 */

@DatabaseTable(tableName = Constantes.TABLA_DEPARTAMENTO)
public class Departamento {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_DEPARTAMENTO)
    private int id;

    @DatabaseField(columnName = Constantes.NOMBRE_DEPARTAMENTO, canBeNull = false)
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


    public Departamento() {
    }

    public Departamento(String nombre) {
        this.nombre = nombre;
    }

}
