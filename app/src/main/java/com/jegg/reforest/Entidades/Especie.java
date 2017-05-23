package com.jegg.reforest.Entidades;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

@DatabaseTable(tableName = Constantes.TABLA_ESPECIE)
public class Especie {

    @DatabaseField(columnName = Constantes.ID_ESPECIE, id = true )
    private String id;

    @DatabaseField(columnName = Constantes.ESPECIE_ESPECIE, canBeNull = false)
    private String nombre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Especie() {
    }

    public Especie(String nombre) {

        this.nombre = nombre;
        this.id = Constantes.SERIAL + Constantes.secureRandom.nextInt();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
