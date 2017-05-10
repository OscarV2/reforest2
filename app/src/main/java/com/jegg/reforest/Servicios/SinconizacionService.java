package com.jegg.reforest.Servicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.Entidades.User;
import com.jegg.reforest.NetWatcher;
import com.jegg.reforest.Utils.Constantes;
import com.jegg.reforest.asincronas.GetAsyncrona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SinconizacionService extends Service {

    private basededatos datosReforest;
    private int idPersona;
    private Context c;
    List<Persona> listaUsuarios = new ArrayList<>();
    Persona persona;
    GetAsyncrona getAsync;
    Dao daoDesarrolloAct, daoActividad, daoEspecie,
            daoArbolEspecie, daoEstado, daoArbolEstado,
            daoArboles, daoAltura, daoLotes,
            daoPersonas;

    Gson gson = new Gson();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        init();
        getPersonasLocal();
        ConnectivityManager conMan = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        getAsync = new GetAsyncrona();
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED){
            Log.e("Wifi","conectado");
            if (listaUsuarios.size() == 0){
                Log.e("no hay","usuarios");
                getUsuariosFromApi();
            }else {

            }
            
        }

        return START_STICKY;
    }

    private void getPersonasLocal() {

        try {

            listaUsuarios = daoPersonas.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void getUsuariosFromApi() {

        try {
            String usuarios = getAsync.execute(Constantes.GET_PERSONAS).get();
            //JSONObject usersJson = new JSONObject(usuarios);
            if (!(usuarios.equals(""))){
                Persona[] users = gson.fromJson(usuarios, Persona[].class);

                if (users.length > 0){

                    Log.e("users tiene","registros");
                    Log.e("users email 1",
                            users[1].getCorreo());

                    Log.e("users email 1",
                            users[1].getClave());

                    for(Persona p : users){
                        daoPersonas.create(p);
                    }

                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void init() {

        getAsync = new GetAsyncrona();
        datosReforest = OpenHelperManager.getHelper(getApplicationContext(), basededatos.class);

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

    @Override
    public void onDestroy() {
        Log.e("servicio","onDestroy");
        super.onDestroy();
    }
}
//    NetWatcher watcher = new NetWatcher();

//    this.registerReceiver(watcher, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
