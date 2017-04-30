package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

/**
 * Created by oscarvc on 29/04/17.
 */
@DatabaseTable(tableName = Constantes.TABLA_ESTADO)
public class Estado {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_ESTADO)
    private int id;

    @DatabaseField(columnName = Constantes.ESTADO_ESTADO, canBeNull = false)
    private String nombre;

    public Estado(String nombre) {
        this.nombre = nombre;
    }

    public Estado() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
