package com.jegg.reforest.Entidades;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

import java.sql.Date;
import java.util.ArrayList;

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

        JsonObject objetoJson = new JsonObject();
        objetoJson.addProperty("id", id);
        objetoJson.addProperty(Constantes.NOMBRE_LOTE, nombre);
        objetoJson.addProperty(Constantes.FECHA_LOTE, fecha.toString());
        objetoJson.addProperty(Constantes.AREA_LOTE, area);
        objetoJson.addProperty(Constantes.DELIMITACION, delimitacion);
        objetoJson.addProperty(Constantes.MUNICIPIO_LOTE, municipio.getId());

        Gson gson = new Gson();

        return gson.toJson(objetoJson);
    }

    public Lote() {
    }

    public Lote(String nombre, Date fecha, Double area, Municipio municipio, String delimitacion) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.area = area;
        this.municipio = municipio;
        this.delimitacion = delimitacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<LatLng> getPuntos(){

        ArrayList<LatLng> puntos = new ArrayList<>();

        String[] cadenaPuntos = delimitacion.split(",");

        for (int i = 0; i<cadenaPuntos.length; i+=2){

            puntos.add(new LatLng(Double.parseDouble(cadenaPuntos[i]) ,
                    Double.parseDouble(cadenaPuntos[i+1])));

        }

        return puntos;
    }


    public String getDelimitacion() {
        return delimitacion;
    }

    public void setDelimitacion(String delimitacion) {
        this.delimitacion = delimitacion;
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
