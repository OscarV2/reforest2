package com.jegg.reforest.Entidades;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.jegg.reforest.Utils.Constantes;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by oscarvc on 29/04/17.
 */
@DatabaseTable(tableName = Constantes.TABLA_MUNICIPIO)
public class Municipio {

    @DatabaseField(generatedId = true, columnName = Constantes.ID_MUNICIPIO)
    private int id;

    @DatabaseField(columnName = Constantes.NOMBRE_MUNICIPIO, canBeNull = false)
    private String nombre;

    @DatabaseField(columnName = Constantes.DEPARTAMENTO_MUNICIPIO,  foreign = true, foreignAutoRefresh = true)
    private Departamento idDepartamento;

    @ForeignCollectionField
    private ForeignCollection<Lote> lotes;

    public ForeignCollection<Lote> getLotes() {
        return lotes;
    }

    public void setLotes(ForeignCollection<Lote> lotes) {
        this.lotes = lotes;
    }

    public List<Lote> findByMunicipio(ConnectionSource cs, Municipio municipio) throws SQLException {
        Dao<Lote, Integer> dao = DaoManager.createDao(cs, Lote.class);
        return dao.queryForEq(Constantes.ID_MUNICIPIO, municipio.getId());

    }


    public Municipio(String nombre) {
        this.nombre = nombre;
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

    public Departamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Municipio() {
    }
}
