package com.jegg.reforest.Utils;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Actividad;
import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Persona;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by oscarvc on 10/05/17.
 * kmnḱnṕ
 */

public class SyncServiceUtils {

    private Context context;
    Dao<DesarrolloActividades, Integer> daoDesarrolloAct;
    Dao<Especie, Integer> daoEspecie;
    Dao<Arbol, String> daoArboles;
    Dao<ArbolEstado, Integer> daoArbolEstado;
    Dao<ArbolEspecie, Integer> daoArbolEspecie;
    Dao<Altura, Integer> daoAltura;
    Dao<Lote, String> daoLotes;
    Dao<Estado, Integer> daoEstado;
    Dao<Persona, Integer> daoPersonas;
    Dao<Actividad, Integer> daoActividad;

    private List<Persona> listaUsuarios = new ArrayList<>();
    private List<Lote> listaLotes = new ArrayList<>();
    private List<Arbol> listaArbol = new ArrayList<>();
    private List<DesarrolloActividades> listaDesarrolloAct = new ArrayList<>();
    private List<Altura> listaAltura = new ArrayList<>();
    private List<ArbolEstado> listaArbolEstado = new ArrayList<>();
    private List<ArbolEspecie> listaArbolEspecie = new ArrayList<>();

    public void consultarTablas() {

        try {
            // llenar lista con registros no sincronizados
            llenarListasSync();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void llenarListasSync() throws SQLException {

        QueryBuilder<Lote, String> qBLote = daoLotes.queryBuilder();

        Where where =qBLote.where();
        where.eq(Constantes.UPLOADED, false);
        PreparedQuery<Lote> pQLote = qBLote.prepare();
        listaLotes = daoLotes.query(pQLote);

        QueryBuilder<Arbol, String> qBArbol = daoArboles.queryBuilder();

        where = qBArbol.where();
        where.eq(Constantes.UPLOADED, false);
        PreparedQuery<Arbol> pqArbol = qBArbol.prepare();
        listaArbol = daoArboles.query(pqArbol);

        QueryBuilder<DesarrolloActividades, Integer> queryBuilder = daoDesarrolloAct.queryBuilder();

        where = queryBuilder.where();
        where.eq(Constantes.UPLOADED, false);
        PreparedQuery<DesarrolloActividades> preparedQuery = queryBuilder.prepare();
        listaDesarrolloAct = daoDesarrolloAct.query(preparedQuery);

        QueryBuilder<Altura, Integer> qBAltura = daoAltura.queryBuilder();

        where = qBAltura.where();
        where.eq(Constantes.UPLOADED, false);
        PreparedQuery<Altura> pQAltura = qBAltura.prepare();
        listaAltura = daoAltura.query(pQAltura);

        QueryBuilder<ArbolEspecie, Integer> qBArbolEspecie = daoArbolEspecie.queryBuilder();

        where = qBArbolEspecie.where();
        where.eq(Constantes.UPLOADED, false);
        PreparedQuery<ArbolEspecie> pQArbolEspecie = qBArbolEspecie.prepare();
        listaArbolEspecie = daoArbolEspecie.query(pQArbolEspecie);

        QueryBuilder<ArbolEstado, Integer> qBArbolEstado = daoArbolEstado.queryBuilder();

        where = qBArbolEstado.where();
        where.eq(Constantes.UPLOADED, false);
        PreparedQuery<ArbolEstado> pQArbolEstado = qBArbolEstado.prepare();
        listaArbolEstado = daoArbolEstado.query(pQArbolEstado);

    }

    public void sincronizar(){

        SyncApi sincronizarBaseDatos = new SyncApi(context);

        if (listaAltura.size() > 0){

            sincronizarBaseDatos.sincronizarAlturas(listaAltura);
        }

        if (listaArbol.size() > 0){

            sincronizarBaseDatos.sincronizarArboles(listaArbol);
        }

        if (listaArbolEstado.size() > 0){

            sincronizarBaseDatos.sincronizarArbolEstado(listaArbolEstado);
        }

        if (listaLotes.size() > 0){

            sincronizarBaseDatos.sincronizarLotes(listaLotes);
        }

        if (listaArbolEspecie.size() > 0){

            sincronizarBaseDatos.sincronizarArbolEspecie(listaArbolEspecie);
        }

        if (listaDesarrolloAct.size() > 0){

            sincronizarBaseDatos.sincronizarDesarrolloAct(listaDesarrolloAct);
        }

    }

    public SyncServiceUtils(Context context) {
        this.context = context;

        basededatos datosReforest = OpenHelperManager.getHelper(context, basededatos.class);

        try {
            daoLotes = datosReforest.getLoteDao();

            daoArboles = datosReforest.getArbolDao();
            daoDesarrolloAct = datosReforest.getDesarrolloActividadesDao();

            daoEspecie = datosReforest.getEspeciesDao();
            daoArbolEspecie = datosReforest.getArbolEspeciesDao();

            daoArbolEstado = datosReforest.getArbolEstadosDao();

            daoAltura = datosReforest.getAlturaDao();
            daoPersonas = datosReforest.getPersonasDao();

            daoActividad = datosReforest.getActividadsDao();
            daoEstado = datosReforest.getEstadoDao();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkPersonas() {

        try {

            listaUsuarios = daoPersonas.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (listaUsuarios.size() == 0);
    }

    public void crearPersona(Persona persona) {

        try {
            daoPersonas.create(persona);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void releaseHelper(){
        OpenHelperManager.releaseHelper();
    }

    public List<Lote> getListaLotes(){

        try {
            listaLotes = daoLotes.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaLotes;

    }
}