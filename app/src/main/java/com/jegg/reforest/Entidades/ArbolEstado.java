package com.jegg.reforest.Entidades;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

@DatabaseTable(tableName = Constantes.TABLA_ARBOL_ESTADO)
public class ArbolEstado {

    @DatabaseField(generatedId =  true, columnName = "id")
    private transient int id;

    @DatabaseField(columnName = Constantes.ID_ARBOL_ESTADO, foreign = true, foreignAutoRefresh = true)
    private Arbol arbol;

    @DatabaseField(columnName = Constantes.ESTADO_ARBOL_ESTADO, foreign = true, foreignAutoRefresh = true)
    private Estado estado;

    @DatabaseField(columnName = Constantes.ARBOL_ESPECIE_ARBOL + "2")
    private String arbol_id;

    @DatabaseField(columnName = Constantes.ESTADO_ARBOL_ESTADO + "2")
    private int estado_id;

    @DatabaseField(columnName = Constantes.UPLOADED)
    private transient boolean uploaded;
/*
    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
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
*/
    public ArbolEstado(Arbol arbol, Estado estado) {

        this.estado = estado;
        this.arbol = arbol;
        this.arbol_id = arbol.getId();
        this.estado_id = estado.getId();
    }

    public int getId() {
        return id;
    }

    public String getArbol_id() {
        return arbol_id;
    }

    public void setArbol_id(String arbol_id) {
        this.arbol_id = arbol_id;
    }

    public int getEstado_id() {
        return estado_id;
    }

    public void setEstado_id(int estado_id) {
        this.estado_id = estado_id;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public Estado getEstado() {
        return estado;
    }

    public ArbolEstado() {

    }
}
