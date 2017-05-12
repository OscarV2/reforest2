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

    @DatabaseField(generatedId = true, columnName = Constantes.ID_ESPECIE)
    private int id;

    @DatabaseField(columnName = Constantes.ESPECIE_ESPECIE, canBeNull = false)
    private String nombre;

    @ForeignCollectionField
    private ForeignCollection<ArbolEspecie> arbolEspecies;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ForeignCollection<ArbolEspecie> getArbolEspecies() {
        return arbolEspecies;
    }

    public void setArbolEspecies(ForeignCollection<ArbolEspecie> arbolEspecies) {
        this.arbolEspecies = arbolEspecies;
    }

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

    @Override
    public String toString() {

        JsonObject objetoJson = new JsonObject();

        objetoJson.addProperty(Constantes.ESPECIE_ESPECIE, nombre);

        Gson gson = new Gson();

        return gson.toJson(objetoJson);
    }
}
