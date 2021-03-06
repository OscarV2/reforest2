package com.jegg.reforest.Entidades;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

@DatabaseTable(tableName = Constantes.TABLA_ESTADO)
public class Estado {

    @DatabaseField(generatedId = true, columnName = Constantes.ID)
    private int id;

    @DatabaseField(columnName = Constantes.ESTADO_ESTADO, canBeNull = false)
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
