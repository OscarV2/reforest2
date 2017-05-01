package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

/**
 * Created by oscarvc on 29/04/17.
 */
@DatabaseTable(tableName = Constantes.TABLA_ESPECIE_ARBOL)
public class ArbolEspecie {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_ESPECIE_ARBOL)
    private int id;

    @DatabaseField(columnName = Constantes.ARBOL_ESPECIE_ARBOL, foreign = true, foreignAutoRefresh = true)
    private Arbol arbol;

    @DatabaseField(columnName = Constantes.ESPECIE_ESPECIE_ARBOL, foreign = true, foreignAutoRefresh = true)
    private Especie especie;


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

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
        this.especie = especie;
    }

    public ArbolEspecie(Arbol arbol, Especie especie) {

        this.arbol = arbol;
        this.especie = especie;
    }

    public ArbolEspecie() {

    }
}
