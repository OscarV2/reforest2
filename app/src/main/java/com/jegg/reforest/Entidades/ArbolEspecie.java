package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

@DatabaseTable(tableName = Constantes.TABLA_ESPECIE_ARBOL)
public class ArbolEspecie {

    @DatabaseField(columnName = "idArbolEspecie", unique = true)
    private transient int id;

    @DatabaseField(columnName = Constantes.ARBOL_ESPECIE_ARBOL, foreign = true, foreignAutoRefresh = true)
    private transient Arbol arbol;

    @DatabaseField(columnName = Constantes.ESPECIE_ESPECIE_ARBOL, foreign = true, foreignAutoRefresh = true)
    private transient Especie especie;

   @DatabaseField(columnName = Constantes.ARBOL_ESPECIE_ARBOL + "2")
   private String arbol_id;

    @DatabaseField(columnName = Constantes.ESPECIE_ESPECIE_ARBOL+ "2")
    private int especie_id;

    @DatabaseField(columnName = Constantes.UPLOADED)
    private transient boolean uploaded;

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public Arbol getArbol() {
        return arbol;
    }

    public int getEspecie_id() {
        return especie_id;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public void setArbol(Arbol arbol) {
        this.arbol = arbol;
    }

    public ArbolEspecie(Arbol arbol, Especie especie) {

        this.arbol = arbol;
        this.especie = especie;
        this.arbol_id = arbol.getId();
        this.especie_id = especie.getId();
    }

    public int getId() {
        return id;
    }

    public Especie getEspecie() {
        return especie;
    }

    public ArbolEspecie() {

    }

}
