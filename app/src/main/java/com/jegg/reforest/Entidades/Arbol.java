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

@DatabaseTable(tableName = Constantes.TABLA_ARBOL)
public class Arbol {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_ARBOL)
    private int id;

    @DatabaseField(columnName = Constantes.COORDENADAS_ARBOL, canBeNull = false)
    private String coordenadas;

    @DatabaseField(columnName = Constantes.FECHA_ARBOL)
    private Date fecha;

    @DatabaseField(columnName = Constantes.LOTE_ID_ARBOL, foreign = true, foreignAutoRefresh = true)
    private Lote lote;

    @ForeignCollectionField
    private ForeignCollection<ArbolEspecie> arbolEspecies;

    @ForeignCollectionField
    private ForeignCollection<ArbolEstado> arbolEstados;

    @ForeignCollectionField
    private ForeignCollection<Altura> alturas;

    @ForeignCollectionField
    private ForeignCollection<DesarrolloActividades> desarrolloActividades;

    public ForeignCollection<ArbolEspecie> getArbolEspecies() {
        return arbolEspecies;
    }

    public void setArbolEspecies(ForeignCollection<ArbolEspecie> arbolEspecies) {
        this.arbolEspecies = arbolEspecies;
    }

    public ForeignCollection<ArbolEstado> getArbolEstados() {
        return arbolEstados;
    }

    public void setArbolEstados(ForeignCollection<ArbolEstado> arbolEstados) {
        this.arbolEstados = arbolEstados;
    }

    public ForeignCollection<Altura> getAlturas() {
        return alturas;
    }

    public void setAlturas(ForeignCollection<Altura> alturas) {
        this.alturas = alturas;
    }

    public ForeignCollection<DesarrolloActividades> getDesarrolloActividades() {
        return desarrolloActividades;
    }

    public void setDesarrolloActividades(ForeignCollection<DesarrolloActividades> desarrolloActividades) {
        this.desarrolloActividades = desarrolloActividades;
    }

    public Arbol() {
    }

    public Arbol(String coordenadas, Date fecha, Lote lote) {
        this.coordenadas = coordenadas;
        this.fecha = fecha;
        this.lote = lote;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public LatLng getPosicion(){

        String[] coordenadasArray = coordenadas.split(",");
        double lat = Double.parseDouble(coordenadasArray[0]);
        double lng = Double.parseDouble(coordenadasArray[1]);

        return new LatLng(lat, lng);

    }

    @Override
    public String toString() {

        JsonObject objetoJson = new JsonObject();
        objetoJson.addProperty("id", id);
        objetoJson.addProperty(Constantes.COORDENADAS_ARBOL, coordenadas);
        objetoJson.addProperty(Constantes.FECHA_ARBOL, fecha.toString());
        objetoJson.addProperty(Constantes.LOTE_ID_ARBOL, lote.getId());

        Gson gson = new Gson();

        return gson.toJson(objetoJson);
    }

}







