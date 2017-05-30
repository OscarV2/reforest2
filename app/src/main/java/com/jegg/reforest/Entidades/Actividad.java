package com.jegg.reforest.Entidades;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

@DatabaseTable(tableName = Constantes.TABLA_ACTIVIDADES)
public class Actividad {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_ACTIVIDADES)
    private int id;

    @DatabaseField(columnName = Constantes.NOMBRE_ACTIVIDADES, canBeNull = false)
    private String nombre;

    @ForeignCollectionField
    private ForeignCollection<DesarrolloActividades> desarrolloActividades;

    public Actividad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

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

    @Override
    public String toString() {

        JsonObject objetoJson = new JsonObject();
        objetoJson.addProperty("id", id);
        objetoJson.addProperty("nombre", nombre);

        Gson gson = new Gson();

        return gson.toJson(objetoJson);
    }
}













