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

@DatabaseTable(tableName = Constantes.TABLA_ARBOL)
public class Arbol {

    @DatabaseField(columnName = Constantes.ID_ARBOL, id = true)
    private String id;

    @DatabaseField(columnName = "numArbol")
    private transient  int numArbol;

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

    @ForeignCollectionField
    private transient ForeignCollection<ArbolEspecie> arbolEspecies;

    @ForeignCollectionField
    private transient ForeignCollection<ArbolEstado> arbolEstados;

    @ForeignCollectionField
    private transient ForeignCollection<Altura> alturas;

    @ForeignCollectionField
    private transient ForeignCollection<DesarrolloActividades> desarrolloActividades;

    public List<ArbolEspecie> getArbolEspecie() {

        List<ArbolEspecie> listaArbolEspecie = new ArrayList<>();
        CloseableWrappedIterable<ArbolEspecie> iterable = arbolEspecies.getWrappedIterable();
        for (ArbolEspecie arbolEspecie : iterable){

            listaArbolEspecie.add(arbolEspecie);
        }

        return listaArbolEspecie;
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

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
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

}







