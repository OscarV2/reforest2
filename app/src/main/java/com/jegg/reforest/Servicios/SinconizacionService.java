package com.jegg.reforest.Servicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.Utils.SyncServiceUtils;
import com.jegg.reforest.api.ReforestApiAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinconizacionService extends Service {

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
        ConnectivityManager conMan = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMan.getActiveNetworkInfo() != null &&
                conMan.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED){

            //Log.e("Wifi","conectado");          // Wifi conectado o hay datos

            if (utils.checkPersonas()){         // no hay usuarios en la app, se procede a bajarlos de la api
                Log.e("no hay","usuarios");
                getUsuariosFromApi();
                stopSelf();

            }else{                // sincronizacion automatica enabled

                utils.consultarTablas();
                utils.sincronizar();
                stopSelf();
                Log.e("automatyc","able");
            }
        }else {
            // wifi disabled
            stopSelf();
        }
        return START_STICKY;
    }

    private void getUsuariosFromApi() {

        Call<List<Persona>> getPersonas = ReforestApiAdapter.getApiService().getPersonas();
        getPersonas.enqueue(new Callback<List<Persona>>() {
            @Override
            public void onResponse(@NonNull Call<List<Persona>> call, @NonNull Response<List<Persona>> response) {

                if (response.isSuccessful()){

                    List<Persona> personaList = response.body();
                    if (personaList != null && personaList.size() > 0) {

                        for (Persona p : personaList) {
                            utils.crearPersona(p);
                        }
                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Persona>> call, @NonNull Throwable t) {

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
        utils.releaseHelper();
        Log.e("servicio","stopped");
        super.onDestroy();
    }

}
