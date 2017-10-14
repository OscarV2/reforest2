package com.jegg.reforest.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
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
import com.jegg.reforest.Entidades.Tallo;
import com.jegg.reforest.SincronizacionExitosa;
import com.jegg.reforest.api.ReforestApiAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by oscarvc on 10/05/17.
 * kmnḱnṕ
 */

public class SyncServiceUtils {

    private Context context;
    private UpdateDb updateDb;
    private SincronizacionExitosa sinc;
    public Dao<DesarrolloActividades, Integer> daoDesarrolloAct;
    public Dao<Especie, Integer> daoEspecie;
    public Dao<Arbol, String> daoArboles;
    public Dao<ArbolEstado, Integer> daoArbolEstado;
    public Dao<ArbolEspecie, Integer> daoArbolEspecie;
    public Dao<Altura, Integer> daoAltura;
    public Dao<Lote, String> daoLotes;
    public Dao<Estado, Integer> daoEstado;
    public Dao<Persona, Integer> daoPersonas;
    public Dao<Actividad, Integer> daoActividad;
    public Dao<Tallo, Integer> daoTallo;

    private List<Persona> listaUsuarios = new ArrayList<>();
    private List<Lote> listaLotes = new ArrayList<>();
    private List<Arbol> listaArbol = new ArrayList<>();
    private List<DesarrolloActividades> listaDesarrolloAct = new ArrayList<>();
    private List<Altura> listaAltura = new ArrayList<>();
    private List<ArbolEstado> listaArbolEstado = new ArrayList<>();
    private List<ArbolEspecie> listaArbolEspecie = new ArrayList<>();
    private List<Tallo> listaTallos = new ArrayList<>();

    public boolean consultarTablas(){

        return  listaAltura.size() > 0 || listaArbol.size() > 0
                || listaArbolEspecie.size()  > 0 || listaArbolEstado.size() > 0
                || listaDesarrolloAct.size() > 0 || listaLotes.size() > 0;


    }

    public void llenarListasSync(){

        try {
            // llenar lista con registros no sincronizados
            QueryBuilder<Lote, String> qBLote = daoLotes.queryBuilder();

            Where where = qBLote.where();
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

            QueryBuilder<Tallo, Integer> qBTallo = daoTallo.queryBuilder();

            where = qBTallo.where();
            where.eq(Constantes.UPLOADED, false);
            PreparedQuery<Tallo> pQTallo = qBTallo.prepare();
            listaTallos = daoTallo.query(pQTallo);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void sincronizar(){

        if (listaLotes.size() > 0){

            sincronizarLotes(listaLotes);
        }

        if (listaArbol.size() > 0){

            sincronizarArboles(listaArbol);
        }

        if (listaAltura.size() > 0){

            sincronizarAlturas(listaAltura);
        }

        if (listaArbolEstado.size() > 0){

            sincronizarArbolEstado(listaArbolEstado);
        }

        if (listaArbolEspecie.size() > 0){

            sincronizarArbolEspecie(listaArbolEspecie);
        }

        if (listaDesarrolloAct.size() > 0){

            sincronizarDesarrolloAct(listaDesarrolloAct);
        }

        if (listaTallos.size() > 0){

            sincronizarTallos(listaTallos);
        }
    }

    public SyncServiceUtils(Context context) {

        this.context = context;
        init();
    }

    public void init(){
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

            daoTallo = datosReforest.getTallosDao();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SyncServiceUtils(Context context, SincronizacionExitosa sincronizacionExitosa) {

        this.context = context;
        this.updateDb = new UpdateDb(context);
        this.sinc = sincronizacionExitosa;
        init();
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

    private void sincronizarLotes(final List<Lote> listaLotes) {

        //sinc lotes
        for (final Lote lote : listaLotes){

            Call<ResponseBody> subirLotes = ReforestApiAdapter.getApiService().postLotes(lote);
            subirLotes.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    Log.e("postLotes toString",response.toString());
                    if (response.isSuccessful()){

                        Log.e("subirLote","Sucessful");
                        try {
                            updateDb.updateLote(lote);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else {
                        Log.e("postLotes toString",response.toString());
                        //                    Log.e("resultado postLotes",response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {

                    sinc.exitosa(false);
                    Log.e("subirLote","Fallo");
                }
            });
        }

    }

    private void sincronizarArboles(List<Arbol> listaArbol) {

        //sinc arboles
        for (final Arbol arbol : listaArbol){

            Call<ResponseBody> subirArbol = ReforestApiAdapter.getApiService().postArbol(arbol);
            subirArbol.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()){

                        Log.e("subirArbol","Sucessful");
                        try {
                            updateDb.updateAbol(arbol);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    sinc.exitosa(false);
                    Log.e("subirArbol","Fallo");
                }
            });
        }
    }

    private void sincronizarArbolEstado(List<ArbolEstado> listaArbolEstado) {

        if (listaArbolEstado.size() > 0){

            //sinc Estado
            for (final ArbolEstado arbolEstado : listaArbolEstado){

                Call<ResponseBody> subirEspecie = ReforestApiAdapter.getApiService().postEstadoArbol(arbolEstado);
                subirEspecie.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()){
                            Log.e("subirArbolEstado","Sucessful");
                            try {
                                updateDb.updateArbolEstado(arbolEstado);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        sinc.exitosa(false);
                        Log.e("subirArbolEstado","Fallo");
                    }
                });
            }
        }else{Log.e("esta vacia", "arbolEstado");}

    }

    private void sincronizarArbolEspecie(List<ArbolEspecie> listaArbolEspecie) {

        //sinc ArbolEspecie
        for (final ArbolEspecie arbolEspecie : listaArbolEspecie){

            Call<ResponseBody> subirArbolEspecie = ReforestApiAdapter.getApiService().postEspecieArbol(arbolEspecie);
            subirArbolEspecie.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()){
                        Log.e("subirArbolEspecie","Sucessful");
                        try {
                            updateDb.update(arbolEspecie);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    sinc.exitosa(false);
                    Log.e("subirArbolEspecie","Fallo");
                }
            });
        }
    }

    private void sincronizarAlturas(List<Altura> listaAltura) {

        //sinc Alturas
        for (final Altura altura : listaAltura){
            //Log.e("sincronizando alturas","dentro del for");

            Call<ResponseBody> subirAltura = ReforestApiAdapter.getApiService().postAltura(altura);
            subirAltura.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()){
                        Log.e("subirAltura","Sucessful");
                        try {
                            updateDb.updateAltura(altura);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    sinc.exitosa(false);
                    Log.e("subirAltura","Fallo");
                }
            });


        }
    }

    private void sincronizarTallos(List<Tallo> listaTallo) {

        //sinc Tallos
        for (final Tallo tallo : listaTallo){

            Call<ResponseBody> subirTallos = ReforestApiAdapter.getApiService().postTallo(tallo);
            subirTallos.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()){
                        Log.e("subirAltura","Sucessful");
                        try {
                            updateDb.updateTallo(tallo);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    sinc.exitosa(false);
                    Log.e("subirTallo","Fallo");
                }
            });
        }
    }

    private void sincronizarDesarrolloAct(final List<DesarrolloActividades> listaDesarrolloAct) {

        Log.e("numero de actividades", String.valueOf(listaDesarrolloAct.size()));
        for (final DesarrolloActividades dsa : listaDesarrolloAct){

            Call<ResponseBody> subirDsaAct = ReforestApiAdapter.getApiService().postDesarrolloAct(dsa);
            subirDsaAct.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    Log.e("onResponse","desarrolloactividades");
                    Log.e("respuesta",response.toString());
                    if (response.isSuccessful()){
                        Log.e("response","desarrolloactividades fue exitosa.");
                        Log.e("","");
                        try {
                            updateDb.updateDesaAct(dsa);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Log.e("subirDesarrollo", "es successfull");
                        if (dsa == listaDesarrolloAct.get(listaDesarrolloAct.size() - 1)){

                            sinc.exitosa(true);
                            Log.e("ultimo " , "desarrolloActividad ");
                        }
                    }else {
                        try {
                            updateDb.updateDesaAct(dsa);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Log.e("onResponse","desarrolloactividades no fue exitosa");
                        if (dsa == listaDesarrolloAct.get(listaDesarrolloAct.size() - 1)){

                            sinc.exitosa(true);
                            Log.e("ultimo " , "desarrolloActividad ");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    sinc.exitosa(false);
                    Log.e("subirDesa","Fallo");
                }
            });

        }
    }
}