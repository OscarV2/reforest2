package com.jegg.reforest.Entidades;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

import java.sql.Date;
import java.text.SimpleDateFormat;
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

    @Override
    public String toString() {

        JsonObject objetoJson = new JsonObject();

        objetoJson.addProperty("id", String.valueOf(id) + Constantes.SERIAL);
        objetoJson.addProperty(Constantes.NOMBRE_LOTE, nombre);
        objetoJson.addProperty("fecha_creacion", Constantes.sdf.format(fecha));
        objetoJson.addProperty(Constantes.AREA_LOTE, area);
        objetoJson.addProperty(Constantes.DELIMITACION, delimitacion);
        objetoJson.addProperty(Constantes.MUNICIPIO_LOTE, 1);

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

        Log.e("delimitacion", delimitacion);
        String[] cadenaPuntos = delimitacion.split(",");

        for (String s : cadenaPuntos){

            Log.e("punto", s);
        }

        for (int i = 0; i<cadenaPuntos.length; i++){
            String[] puntoLatLng = cadenaPuntos[1].split(" ");


            puntos.add(new LatLng(Double.parseDouble(puntoLatLng[0]) ,
                    Double.parseDouble(puntoLatLng[1])));
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
