package com.jegg.reforest.Utils;

import android.content.Context;
import android.util.Log;

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
import com.jegg.reforest.api.ReforestApiAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oscarvc on 10/05/17.
 */

public class SyncServiceUtils {

    private Context context;
    private basededatos datosReforest;
    Dao<DesarrolloActividades, Integer> daoDesarrolloAct;
    Dao<Actividad, Integer> daoActividad;
    Dao<Especie, Integer> daoEspecie;
    Dao<Arbol, String> daoArboles;
    Dao<Estado, Integer> daoEstado;
    Dao<ArbolEstado, Integer> daoArbolEstado;
    Dao<ArbolEspecie, Integer> daoArbolEspecie;
    Dao<Altura, Integer> daoAltura;
    Dao<Lote, String> daoLotes;
    Dao<Persona, Integer> daoPersonas;

    private List<Persona> listaUsuarios = new ArrayList<>();
    private List<Lote> listaLotes = new ArrayList<>();
    private List<Arbol> listaArbol = new ArrayList<>();
    private List<DesarrolloActividades> listaDesarrolloAct = new ArrayList<>();
    private List<Altura> listaAltura = new ArrayList<>();
    List<Estado> listaEstados = new ArrayList<>();
    private List<ArbolEstado> listaArbolEstado = new ArrayList<>();
    private List<ArbolEspecie> listaArbolEspecie = new ArrayList<>();
    private List<Especie> listaEspecie = new ArrayList<>();

    public boolean checkTablas() {

        boolean siHayRegistros = false;
        consultarTablas();
        if (!(listaDesarrolloAct.size() == 0 || listaLotes.size() == 0 || listaArbol.size() == 0)) {
            siHayRegistros = true;
        }

        return siHayRegistros;
    }

    private void consultarTablas() {

        try {
            listaArbol = daoArboles.queryForAll();
            listaLotes = daoLotes.queryForAll();
            listaEspecie = daoEspecie.queryForAll();

            // llenar lista con registros no sincronizados
            llenarListasSync();
            //listaDesarrolloAct = daoDesarrolloAct.queryForAll();
            //listaAltura = daoAltura.queryForAll();
            //listaArbolEspecie = daoArbolEspecie.queryForAll();
            //listaArbolEstado = daoArbolEstado.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void llenarListasSync() throws SQLException {

        QueryBuilder<DesarrolloActividades, Integer> queryBuilder = daoDesarrolloAct.queryBuilder();

        Where where = queryBuilder.where();
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

        Log.e("numero lotes", String.valueOf(listaLotes.size()));
        Log.e("numero arboles", String.valueOf(listaArbol.size()));
        Log.e("numDesaAct ", String.valueOf(listaDesarrolloAct.size()));
        Log.e("numAlturas ", String.valueOf(listaAltura.size()));
        Log.e("numEspecies ", String.valueOf(listaEspecie.size()));
        Log.e("numArbolEstados ", String.valueOf(listaArbolEstado.size()));
        Log.e("numArbolEspecies ", String.valueOf(listaArbolEspecie.size()));

        SyncApi sincronizarBaseDatos = new SyncApi(datosReforest);
/*
        sincronizarBaseDatos.sincronizarLotes(listaLotes);
        sincronizarBaseDatos.sincronizarArboles(listaArbol);

        sincronizarBaseDatos.sincronizarEspecie(listaEspecie);

        sincronizarBaseDatos.sincronizarArbolEspecie(listaArbolEspecie);

        sincronizarBaseDatos.sincronizarAlturas(listaAltura);
        sincronizarBaseDatos.sincronizarArbolEstado(listaArbolEstado);

        sincronizarBaseDatos.sincronizarDesarrolloAct(listaDesarrolloAct);
  */
    }

    public SyncServiceUtils(Context context) {
        this.context = context;

        this.datosReforest = OpenHelperManager.getHelper(context, basededatos.class);

        try {
            daoLotes = datosReforest.getLoteDao();

            daoArboles = datosReforest.getArbolDao();
            daoActividad = datosReforest.getActividadsDao();
            daoDesarrolloAct = datosReforest.getDesarrolloActividadesDao();

            daoEspecie = datosReforest.getEspeciesDao();
            daoArbolEspecie = datosReforest.getArbolEspeciesDao();

            daoEstado = datosReforest.getEstadoDao();
            daoArbolEstado = datosReforest.getArbolEstadosDao();

            daoAltura = datosReforest.getAlturaDao();
            daoPersonas = datosReforest.getPersonasDao();

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