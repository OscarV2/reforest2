package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

@DatabaseTable(tableName = Constantes.TABLA_ALTURA)
public class Altura {

    @DatabaseField(generatedId = true, columnName = "id", unique = true)
    private transient int id;

    @DatabaseField(columnName = Constantes.ALTURA_ARBOL, canBeNull = false, foreign = true, foreignAutoRefresh = true)
    private Arbol arbol;

    @DatabaseField(columnName = Constantes.ALTURA_ARBOL + "2")
    private String arbol_id;

    @DatabaseField(columnName = Constantes.MEDIDAS_ALTURA, canBeNull = false)
    private String medida;

    @DatabaseField(columnName = Constantes.UPLOADED)
    private transient boolean uploaded;

    public int getId() {
        return id;
    }

    public Altura(Arbol arbol, String medida) {

        this.arbol = arbol;
        this.medida = medida;
        this.arbol_id = arbol.getId();
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    public void setArbol(Arbol arbol) {
        this.arbol = arbol;
    }

    public Altura() {

    }
}
