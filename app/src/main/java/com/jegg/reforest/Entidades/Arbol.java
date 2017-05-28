package com.jegg.reforest.Entidades;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = Constantes.TABLA_ARBOL)
public class Arbol {

    @DatabaseField(columnName = Constantes.ID_ARBOL, id = true)
    private String id;

    @DatabaseField(columnName = "numArbol")
    private int numArbol;

    @DatabaseField(columnName = Constantes.COORDENADAS_ARBOL, canBeNull = false)
    private String coodenadas;

    @DatabaseField(columnName = Constantes.FECHA_ARBOL)
    private String fecha_sembrado;

    @DatabaseField(columnName = Constantes.LOTE_ID_ARBOL)
    private String lote_id;

    @DatabaseField(columnName = "lote", foreign = true, foreignAutoRefresh = true)
    private transient Lote lote;

    @DatabaseField(columnName = Constantes.UPLOADED)
    private transient boolean uploaded;

    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }

    @ForeignCollectionField
    private transient ForeignCollection<ArbolEspecie> arbolEspecies;

    @ForeignCollectionField
    private transient ForeignCollection<ArbolEstado> arbolEstados;

    @ForeignCollectionField
    private transient ForeignCollection<Altura> alturas;

    @ForeignCollectionField
    private transient ForeignCollection<DesarrolloActividades> desarrolloActividades;

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

    public List<Altura> getAlturas() {

        List<Altura> listaAlturas = new ArrayList<>();
        CloseableWrappedIterable<Altura> iterable = alturas.getWrappedIterable();
        for (Altura al : iterable){

            listaAlturas.add(al);
        }

        return listaAlturas;
    }

    public List<ArbolEspecie> getArbolEspecie() {

        List<ArbolEspecie> listaArbolEspecie = new ArrayList<>();
        CloseableWrappedIterable<ArbolEspecie> iterable = arbolEspecies.getWrappedIterable();
        for (ArbolEspecie arbolEspecie : iterable){

            listaArbolEspecie.add(arbolEspecie);
        }

        return listaArbolEspecie;
    }

    public ForeignCollection<DesarrolloActividades> getDesarrolloActividades() {
        return desarrolloActividades;
    }

    public Estado getLastEstado(){

        Estado estado = null;
        List<ArbolEstado> listAe = new ArrayList<>();
        CloseableWrappedIterable<ArbolEstado> iterable = arbolEstados.getWrappedIterable();
        for (ArbolEstado arbolEstado : iterable){

            listAe.add(arbolEstado);

        }
        if (listAe.size()>0){
            estado = listAe.get(listAe.size()-1).getEstado();
        }

        return estado;
    }
    public Arbol() {
    }

    public Arbol(String coordenadas, String fecha, Lote lote) {
        this.coodenadas = coordenadas;
        this.fecha_sembrado = fecha;
        this.lote = lote;

        this.lote_id = lote.getId();
        this.id = Constantes.SERIAL + Constantes.secureRandom.nextInt();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha_sembrado() {
        return fecha_sembrado;
    }

    public void setFecha_sembrado(String fecha_sembrado) {
        this.fecha_sembrado = fecha_sembrado;
    }

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }

    public int getNumArbol() {
        return numArbol;
    }

    public void setNumArbol(int numArbol) {
        this.numArbol = numArbol;
    }

    public LatLng getPosicion(){

        String[] coordenadasArray = coodenadas.split(" ");
        double lat = Double.parseDouble(coordenadasArray[0]);
        double lng = Double.parseDouble(coordenadasArray[1]);

        return new LatLng(lat, lng);

    }
/*
    public boolean estaSembrado(){

        boolean estasembrado = false;
        try {
            CloseableWrappedIterable<DesarrolloActividades> iterable = desarrolloActividades.getWrappedIterable();
            for (DesarrolloActividades da : iterable){
                if (da.getIdActividad().getNombre().equals("Sembrar o Plantar")){
                    estasembrado = true;
                }
            }

        }catch (NullPointerException e){
            estasembrado = false;
        }

        return estasembrado;
    }
*/
    public String getLote_id() {
        return lote_id;
    }

    public void setLote_id(String lote_id) {
        this.lote_id = lote_id;
    }
}







