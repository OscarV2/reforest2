package com.jegg.reforest.Servicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import com.jegg.reforest.Utils.Constantes;
import com.jegg.reforest.Utils.SyncServiceUtils;
import com.jegg.reforest.asincronas.GetAsyncrona;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SinconizacionService extends Service {


    private int idPersona;
    private Context c;
    public final static String MY_ACTION = "MY_ACTION";
    Persona persona;
    GetAsyncrona getAsync;

    SharedPreferences prefs;
    private SyncServiceUtils utils;
    Gson gson = new Gson();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        init();
        boolean automaticSync = prefs.getBoolean("automatic_sync",false);
        ConnectivityManager conMan = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        getAsync = new GetAsyncrona();
        if (conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED){
            Log.e("Wifi","conectado");          // Wifi conectado o hay datos

            if (utils.checkPersonas()){         // no hay usuarios en la app, se procede a bajarlos de la api
                Log.e("no hay","usuarios");
                getUsuariosFromApi();
                this.stopSelf();
            }else if(automaticSync){                // sincronizacion automatica enabled

                if(utils.checkTablas()){                             // si hay usuarios,
                    // check tablas
                    //hay tablas entonces se subiran todas las tablas a la api

                    utils.sincronizar();
                    this.stopSelf();

                }
            }else {
                Log.e("automaticSync","disabled");
                this.stopSelf();
            }

        }

        return START_STICKY;
    }

    private void getUsuariosFromApi() {

        try {
            String usuarios = getAsync.execute(Constantes.GET_PERSONAS).get();
            //JSONObject usersJson = new JSONObject(usuarios);
            if (!(usuarios.equals(""))){
                Persona[] users = gson.fromJson(usuarios, Persona[].class);

                if (users.length > 0){

                    Log.e("users tiene","registros");

                    for(Persona p : users){
                        utils.crearPersona(p);
                    }

                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void init() {

        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        getAsync = new GetAsyncrona();
        utils = new SyncServiceUtils(getApplicationContext());

    }

    @Override
    public void onDestroy() {
        Intent send = new Intent(MY_ACTION);
        sendBroadcast(send);
        Log.e("servicio","onDestroy");
        super.onDestroy();
    }

}
