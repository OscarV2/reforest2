package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
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

    public ArbolEstado(Arbol arbol, Estado estado) {

        this.estado = estado;
        this.arbol = arbol;
        this.arbol_id = arbol.getId();
        this.estado_id = estado.getId();
    }

    public int getId() {
        return id;
    }

    public Estado getEstado() {
        return estado;
    }

    public ArbolEstado() {

    }
}
