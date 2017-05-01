package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

@DatabaseTable(tableName = Constantes.TABLA_ALTURA)
public class Altura {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_ALTURA)
    private int id;

    @DatabaseField(columnName = Constantes.ALTURA_ARBOL, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Arbol arbol;

    @DatabaseField(columnName = Constantes.MEDIDAS_ALTURA, canBeNull = false)
    private String medida;


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

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public Altura(Arbol arbol, String medida) {

        this.arbol = arbol;
        this.medida = medida;
    }

    public Altura() {

    }
}
