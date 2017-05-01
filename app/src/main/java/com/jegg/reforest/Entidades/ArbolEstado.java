package com.jegg.reforest.Entidades;

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
}
