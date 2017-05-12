package com.jegg.reforest.Entidades;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

@DatabaseTable(tableName = Constantes.TABLA_ARBOL_ESTADO)
public class ArbolEstado {

    @DatabaseField(generatedId = true, columnName = Constantes.ARBOL_ID)
    private int id;

    @DatabaseField(columnName = Constantes.ID_ARBOL_ESTADO, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Arbol arbol;

    @DatabaseField(columnName = Constantes.ESTADO_ARBOL_ESTADO, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Estado estado;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Arbol getArbol() {
        return arbol;
    }

    public void setArbol(Arbol arbol) {
        this.arbol = arbol;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ArbolEstado(Arbol arbol, Estado estado) {
        this.arbol = arbol;
        this.estado = estado;
    }

    public ArbolEstado() {

    }

    @Override
    public String toString() {

        JsonObject objetoJson = new JsonObject();
        objetoJson.addProperty(Constantes.ARBOL_ID, String.valueOf(arbol.getId()) + Constantes.SERIAL);
        objetoJson.addProperty(Constantes.ESTADO_ARBOL_ESTADO, estado.getId());

        Gson gson = new Gson();

        return gson.toJson(objetoJson);
    }
}
