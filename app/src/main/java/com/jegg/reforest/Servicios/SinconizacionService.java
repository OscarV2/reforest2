package com.jegg.reforest.Servicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.Utils.Constantes;
import com.jegg.reforest.Utils.SyncServiceUtils;
import com.jegg.reforest.api.ReforestApiAdapter;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinconizacionService extends Service {


    private int idPersona;

    public final static String MY_ACTION = "MY_ACTION";



    SharedPreferences prefs;
    private SyncServiceUtils utils;

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
        }else {
            stopSelf();
        }
        return START_STICKY;
    }

    private void getUsuariosFromApi() {

        Call<List<Persona>> getPersonas = ReforestApiAdapter.getApiService().getPersonas();
        getPersonas.enqueue(new Callback<List<Persona>>() {
            @Override
            public void onResponse(Call<List<Persona>> call, Response<List<Persona>> response) {

                if (response.isSuccessful()){
                    Log.e("get personas", "successfull");
                    List<Persona> personaList = response.body();
                    if (personaList != null && personaList.size() > 0) {

                        for (Persona p : personaList) {
                            utils.crearPersona(p);
                        }
                    }
                }else {
                    //
                }

            }

            @Override
            public void onFailure(Call<List<Persona>> call, Throwable t) {
                stopSelf();
            }
        });

    }

    private void init() {

        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
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
