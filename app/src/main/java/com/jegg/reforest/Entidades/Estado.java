package com.jegg.reforest.Entidades;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

/**
 * Created by oscarvc on 29/04/17.
 */
@DatabaseTable(tableName = Constantes.TABLA_ESTADO)
public class Estado {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_ESTADO)
    private int id;

    @DatabaseField(columnName = Constantes.ESTADO_ESTADO, canBeNull = false)
    private String nombre;

    @ForeignCollectionField
    private ForeignCollection<ArbolEstado> arbolEstados;

    public int getId() {
        return id;
    }

    public ForeignCollection<ArbolEstado> getArbolEstados() {
        return arbolEstados;
    }

    public void setArbolEstados(ForeignCollection<ArbolEstado> arbolEstados) {
        this.arbolEstados = arbolEstados;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estado(String nombre) {
        this.nombre = nombre;
    }

    public Estado() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
