package com.jegg.reforest.Entidades;


import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

import java.sql.Date;

@DatabaseTable(tableName = Constantes.TABLA_LOTE)
public class Lote {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_LOTE)
    private int id;

    @DatabaseField(columnName = Constantes.NOMBRE_LOTE, canBeNull = false)
    private String nombre;

    @DatabaseField(columnName = Constantes.FECHA_LOTE, canBeNull = false)
    private Date fecha;

    @DatabaseField(columnName = Constantes.AREA_LOTE, canBeNull = false)
    private Double area;

    @DatabaseField(columnName = Constantes.DELIMITACION, canBeNull = false)
    private String delimitacion;

    @DatabaseField(columnName = Constantes.MUNICIPIO_LOTE, canBeNull = false, foreign = true)
    private Municipio municipio;

    @ForeignCollectionField
    private ForeignCollection<Arbol> arboles;

    public ForeignCollection<Arbol> getArboles() {
        return arboles;
    }

    public void setArboles(ForeignCollection<Arbol> arboles) {
        this.arboles = arboles;
    }

    @Override
    public String toString() {
        String objetoJson = "{"+"'id':"+String.valueOf(id)+
                ", 'nombre':" + nombre +
                ", 'fecha':" + fecha.toString() +
                ", 'area':" + String.valueOf(area) +
                ", 'delimitacion':" + delimitacion +
                ", 'municipio_id':" + String.valueOf(municipio.getId()) +

                "}";
        return super.toString();
    }

    public Lote() {
    }

    public Lote(String nombre, Date fecha, Double area, Municipio municipio) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.area = area;
        this.municipio = municipio;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
}
