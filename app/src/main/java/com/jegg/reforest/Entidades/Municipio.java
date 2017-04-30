package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

/**
 * Created by oscarvc on 29/04/17.
 */
@DatabaseTable(tableName = Constantes.TABLA_MUNICIPIO)
public class Municipio {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_MUNICIPIO)
    private int id;

    @DatabaseField(columnName = Constantes.NOMBRE_MUNICIPIO, canBeNull = false)
    private String nombre;

    @DatabaseField(columnName = Constantes.DEPARTAMENTO_MUNICIPIO, canBeNull = false, foreign = true)
    private Departamento idDepartamento;

    public Municipio(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Departamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Municipio() {
    }
}
