package com.jegg.reforest.DBdatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.jegg.reforest.Entidades.Actividad;
import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.Departamento;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Municipio;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.R;

import java.sql.SQLException;


public class basededatos extends OrmLiteSqliteOpenHelper {

    private static String DATABASE_NAME = "datosReforest";
    private static int DATABASE_VERSION = 1;

    // Objetos DAO que se usaran para acceder a la tabla Arbol
    private Dao<Arbol, String> arbolDao = null;
    private RuntimeExceptionDao<Arbol, Integer> arbolRuntimeDao = null;

    // Objetos DAO que se usaran para acceder a la tabla Lote
    private Dao<Lote, String> loteDao = null;
    private RuntimeExceptionDao<Lote, Integer> loteRuntimeDao = null;

    // Objetos DAO que se usaran para acceder a la tabla Altura
    private Dao<Altura, Integer> alturaDao = null;
    private RuntimeExceptionDao<Altura, Integer> alturaRuntimeDao = null;

    // Objetos DAO que se usaran para acceder a la tabla Estado
    private Dao<Estado, Integer> estadoDao = null;
    private RuntimeExceptionDao<Estado, Integer> estadosRuntimeDao = null;

    // Objetos DAO que se usaran para acceder a la tabla ESpecie
    private Dao<Especie, Integer> especiesDao = null;
    private RuntimeExceptionDao<Especie, Integer> especieRuntimeDao = null;

    // Objetos DAO que se usaran para acceder a la tabla persona
    private Dao<Persona, Integer> personasDao = null;
    private RuntimeExceptionDao<Persona, Integer> personasRuntimeDao = null;

    // Objetos DAO que se usaran para acceder a la tabla departamento
    private Dao<Departamento, Integer> departamentosDao = null;
    private RuntimeExceptionDao<Departamento, Integer> departamentosRuntimeDao = null;

    // Objetos DAO que se usaran para acceder a la tabla municipio
    private Dao<Municipio, Integer> municipiosDao = null;
    private RuntimeExceptionDao<Municipio, Integer> municipiosRuntimeDao = null;

    // Objetos DAO que se usaran para acceder a la tabla actividad
    private Dao<Actividad, Integer> actividadsDao = null;
    private RuntimeExceptionDao<Actividad, Integer> actividadsRuntimeDao = null;

    // Objetos DAO que se usaran para acceder a la tabla desarrolloActividades
    private Dao<DesarrolloActividades, Integer> desarrolloActividadesDao = null;
    private RuntimeExceptionDao<DesarrolloActividades, Integer> desarrolloActividadesRuntimeDao = null;

    // Objetos DAO que se usaran para acceder a la tabla EstadoArbol
    private Dao<ArbolEstado, Integer> arbolEstadosDao = null;
    private RuntimeExceptionDao<ArbolEstado, Integer> arbolEstadosRuntimeDao = null;

    // Objetos DAO que se usaran para acceder a la tabla EspecieArbol
    private Dao<ArbolEspecie, Integer> arbolEspeciesDao = null;
    private RuntimeExceptionDao<ArbolEspecie, Integer> arbolEspeciesRuntimeDao = null;




    public basededatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormliteconfig);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {
            Log.e(basededatos.class.getSimpleName(), "onCreate");

            TableUtils.createTable(connectionSource, Arbol.class);
            TableUtils.createTable(connectionSource, Departamento.class);

            TableUtils.createTable(connectionSource, Altura.class);
            TableUtils.createTable(connectionSource, Municipio.class);

            TableUtils.createTable(connectionSource, Lote.class);
            TableUtils.createTable(connectionSource, Persona.class);

            TableUtils.createTable(connectionSource, Estado.class);
            TableUtils.createTable(connectionSource, ArbolEstado.class);

            TableUtils.createTable(connectionSource, Especie.class);
            TableUtils.createTable(connectionSource, ArbolEspecie.class);

            TableUtils.createTable(connectionSource, Actividad.class);
            TableUtils.createTable(connectionSource, DesarrolloActividades.class);

            Log.e("tablas","creadas");

        }catch (SQLException ex){
            Log.e("no se pudo","crear la db");
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void close() {
        super.close();
        alturaDao = null;
        alturaRuntimeDao = null;
        arbolEstadosDao = null;
        arbolEstadosRuntimeDao = null;

        arbolEspeciesDao = null;
        arbolEspeciesRuntimeDao = null;

        especiesDao = null;
        especieRuntimeDao = null;
        estadoDao = null;
        estadosRuntimeDao = null;

        loteDao = null;
        loteRuntimeDao = null;
        personasDao = null;
        personasRuntimeDao = null;

        municipiosDao = null;
        municipiosRuntimeDao = null;
        departamentosDao = null;
        departamentosRuntimeDao = null;

        actividadsDao = null;
        actividadsRuntimeDao = null;
        desarrolloActividadesDao = null;
        desarrolloActividadesRuntimeDao = null;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource source, int oldVersion, int newVersion) {

    }

    public Dao<Arbol, String> getArbolDao() throws SQLException {
        if (arbolDao == null) arbolDao = getDao(Arbol.class);
        return arbolDao;
    }

    public RuntimeExceptionDao<Arbol, Integer> getArbolRuntimeDao() {
        if (arbolRuntimeDao == null) arbolRuntimeDao = getRuntimeExceptionDao(Arbol.class);
        return arbolRuntimeDao;
    }

    public Dao<Lote, String> getLoteDao() throws SQLException {
        if (loteDao == null) loteDao = getDao(Lote.class);
        return loteDao;
    }

    public RuntimeExceptionDao<Lote, Integer> getLoteRuntimeDao() {
        if (loteRuntimeDao == null) loteRuntimeDao = getRuntimeExceptionDao(Lote.class);
        return loteRuntimeDao;
    }

    public Dao<Altura, Integer> getAlturaDao() throws SQLException {
        if (alturaDao == null) alturaDao = getDao(Altura.class);
        return alturaDao;
    }

    public RuntimeExceptionDao<Altura, Integer> getAlturaRuntimeDao() {
        if (alturaRuntimeDao == null) alturaRuntimeDao = getRuntimeExceptionDao(Altura.class);
        return alturaRuntimeDao;
    }

    public Dao<Estado, Integer> getEstadoDao() throws SQLException {

        if (estadoDao == null) estadoDao = getDao(Estado.class);
        return estadoDao;
    }


    public RuntimeExceptionDao<Estado, Integer> getEstadosRuntimeDao() {
        if (estadosRuntimeDao == null) estadosRuntimeDao = getRuntimeExceptionDao(Estado.class);
        return estadosRuntimeDao;
    }


    public Dao<Especie, Integer> getEspeciesDao() throws SQLException {
        if (especiesDao == null) especiesDao = getDao(Especie.class);
        return especiesDao;
    }

    public RuntimeExceptionDao<Especie, Integer> getEspecieRuntimeDao() {
        if (especieRuntimeDao == null) especieRuntimeDao = getRuntimeExceptionDao(Especie.class);
        return especieRuntimeDao;
    }

    public Dao<Persona, Integer> getPersonasDao() throws SQLException {
        if (personasDao == null) personasDao = getDao(Persona.class);
        return personasDao;
    }

    public RuntimeExceptionDao<Persona, Integer> getPersonasRuntimeDao() {
        if (personasRuntimeDao == null) personasRuntimeDao = getRuntimeExceptionDao(Persona.class);
        return personasRuntimeDao;
    }

    public Dao<Departamento, Integer> getDepartamentosDao() throws SQLException {
        if (departamentosDao == null) departamentosDao = getDao(Departamento.class);
        return departamentosDao;
    }

    public RuntimeExceptionDao<Departamento, Integer> getDepartamentosRuntimeDao() {
        if (departamentosRuntimeDao == null) departamentosRuntimeDao = getRuntimeExceptionDao(Departamento.class);
        return departamentosRuntimeDao;
    }


    public Dao<Municipio, Integer> getMunicipiosDao() throws SQLException {
        if (municipiosDao == null) municipiosDao = getDao(Municipio.class);
        return municipiosDao;
    }

    public RuntimeExceptionDao<Municipio, Integer> getMunicipiosRuntimeDao() {
        if (municipiosRuntimeDao == null) municipiosRuntimeDao = getRuntimeExceptionDao(Municipio.class);
        return municipiosRuntimeDao;
    }

    public Dao<Actividad, Integer> getActividadsDao() throws SQLException {
        if (actividadsDao == null) actividadsDao = getDao(Actividad.class);
        return actividadsDao;
    }

    public RuntimeExceptionDao<Actividad, Integer> getActividadsRuntimeDao() {
        if (actividadsRuntimeDao == null) actividadsRuntimeDao = getRuntimeExceptionDao(Actividad.class);
        return actividadsRuntimeDao;
    }

    public Dao<DesarrolloActividades, Integer> getDesarrolloActividadesDao() throws SQLException {
        if (desarrolloActividadesDao == null) desarrolloActividadesDao = getDao(DesarrolloActividades.class);
        return desarrolloActividadesDao;
    }

    public RuntimeExceptionDao<DesarrolloActividades, Integer> getDesarrolloActividadesRuntimeDao() {
        if (desarrolloActividadesRuntimeDao == null) desarrolloActividadesRuntimeDao = getRuntimeExceptionDao(DesarrolloActividades.class);
        return desarrolloActividadesRuntimeDao;
    }

    public Dao<ArbolEstado, Integer> getArbolEstadosDao() throws SQLException {
        if (arbolEstadosDao == null) arbolEstadosDao = getDao(ArbolEstado.class);
        return arbolEstadosDao;
    }

    public RuntimeExceptionDao<ArbolEstado, Integer> getArbolEstadosRuntimeDao() {
        if (arbolEstadosRuntimeDao == null) arbolEstadosRuntimeDao = getRuntimeExceptionDao(ArbolEstado.class);
        return arbolEstadosRuntimeDao;
    }

    public Dao<ArbolEspecie, Integer> getArbolEspeciesDao() throws SQLException {
        if (arbolEspeciesDao == null) arbolEspeciesDao  = getDao(ArbolEspecie.class);
        return arbolEspeciesDao;
    }

    public RuntimeExceptionDao<ArbolEspecie, Integer> getArbolEspeciesRuntimeDao() {
        if (arbolEspeciesRuntimeDao == null) arbolEspeciesRuntimeDao = getRuntimeExceptionDao(ArbolEspecie.class);
        return arbolEspeciesRuntimeDao;
    }

}
