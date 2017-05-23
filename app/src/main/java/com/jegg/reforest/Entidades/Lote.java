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

    @DatabaseField(columnName = Constantes.ID_LOTE, id = true)
    private String id;

    @DatabaseField(columnName = Constantes.NOMBRE_LOTE, canBeNull = false)
    private String nombre;

    @DatabaseField(columnName = Constantes.FECHA_LOTE, canBeNull = false)
    private String fecha_creacion;

    @DatabaseField(columnName = Constantes.AREA_LOTE, canBeNull = false)
    private Double area;

    @DatabaseField(columnName = Constantes.DELIMITACION, canBeNull = false)
    private String delimitacion;

    @DatabaseField(columnName = Constantes.MUNICIPIO_LOTE)
    private int municipio_id;

    @DatabaseField(columnName = "muni", canBeNull = false, foreign = true)
    private transient Municipio municipio;

    @ForeignCollectionField
    private transient ForeignCollection<Arbol> arboles;

    public ForeignCollection<Arbol> getArboles() {
        return arboles;
    }

    public Lote() {
    }

    public Lote(String nombre, String fecha, Double area, Municipio municipio, String delimitacion) {
        this.nombre = nombre;
        this.fecha_creacion = fecha;
        this.area = area;
        this.municipio = municipio;
        this.delimitacion = delimitacion;

        this.municipio_id = municipio.getId();
        this.id = Constantes.SERIAL + Constantes.secureRandom.nextInt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Double getArea() {
        return area;
    }

    public int getMunicipio_id() {
        return municipio_id;
    }

    public void setMunicipio_id(int municipio_id) {
        this.municipio_id = municipio_id;
    }
}
