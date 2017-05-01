package com.jegg.reforest.Entidades;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oscarvc on 29/04/17.
 */

@DatabaseTable(tableName = Constantes.TABLA_DEPARTAMENTO)
public class Departamento {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_DEPARTAMENTO)
    private int id;

    @DatabaseField(columnName = Constantes.NOMBRE_DEPARTAMENTO, canBeNull = false)
    private String nombre;

    @ForeignCollectionField
    private ForeignCollection<Municipio> municipios;

    public ForeignCollection<Municipio> getMunicipios() {
      /*  ArrayList<Municipio> municipiosList = new ArrayList<Municipio>();
        for (Municipio m : municipiosList){
            municipiosList.add(m);
        }*/
        return municipios;
    }

    public void setMunicipios(ForeignCollection<Municipio> municipios) {
        this.municipios = municipios;
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


    public Departamento() {
    }

    public Departamento(String nombre) {
        this.nombre = nombre;
    }

}
