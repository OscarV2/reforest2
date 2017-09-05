package com.jegg.reforest.Entidades;


import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = Constantes.TABLA_LOTE)
public class Lote {

    @DatabaseField(columnName = Constantes.ID_LOTE, id = true, unique = true)
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

    @DatabaseField(columnName = Constantes.UPLOADED)
    private transient boolean uploaded;

    @DatabaseField(columnName = "persona_lote", canBeNull = false, foreign = true)
    private transient Persona persona;

    @DatabaseField(columnName = "muni", foreign = true)
    private transient Municipio municipio;

    @ForeignCollectionField
    private transient ForeignCollection<Arbol> arboles;

    public List<Arbol> getArboles() {

        CloseableWrappedIterable<Arbol> iterable = arboles.getWrappedIterable();
        List<Arbol> arbols = new ArrayList<>();

        for (Arbol arbol : iterable){

            arbols.add(arbol);
        }

        return arbols;
    }

    public Lote() {
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Lote(String nombre, String fecha, Double area, Municipio municipio,
                String delimitacion, Persona persona) {
        this.nombre = nombre;
        this.fecha_creacion = fecha;
        this.area = area;
        this.municipio = municipio;
        this.delimitacion = delimitacion;
        this.persona = persona;

        this.municipio_id = 11;
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

        String[] cadenaPuntos = delimitacion.split(",");

        for (int i = 0; i<cadenaPuntos.length; i++){
            String[] puntoLatLng = cadenaPuntos[i].split(" ");

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

    public Double getArea() {
        return area;
    }

    public boolean isUploaded() {
        return uploaded;
    }

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }
}
