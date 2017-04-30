package com.jegg.reforest.Entidades;

import com.j256.ormlite.field.DatabaseField;
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

    @DatabaseField(columnName = Constantes.LOTE_ID_ARBOL, foreign = true)
    private Lote lote;

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
}
