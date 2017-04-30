package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

@DatabaseTable(tableName = Constantes.TABLA_ESPECIE)
public class Especie {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_ESPECIE)
    private int id;

    @DatabaseField(columnName = Constantes.ESPECIE_ESPECIE, canBeNull = false)
    private String nombre;


    public Especie() {
    }

    public Especie(String nombre) {

        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
