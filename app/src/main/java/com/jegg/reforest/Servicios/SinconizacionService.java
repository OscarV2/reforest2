package com.jegg.reforest.Servicios;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.SincronizacionExitosa;
import com.jegg.reforest.SyncServiceStopped;
import com.jegg.reforest.Utils.SyncServiceUtils;
import com.jegg.reforest.api.ReforestApiAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinconizacionService implements SincronizacionExitosa{

    //private SharedPreferences prefs;
    private SyncServiceUtils utils;
    private SyncServiceStopped syncServiceStopped;
    private Context context;

    public SinconizacionService(Context context, SyncServiceStopped syncServiceStopped) {
        this.syncServiceStopped = syncServiceStopped;
        this.context = context;
    }

    public void comenzar(){
        init();
        ConnectivityManager conMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMan.getActiveNetworkInfo() != null &&
                conMan.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED){

            if (utils.checkPersonas()){         // no hay usuarios en la app, se procede a bajarlos de la api
                Log.e("no hay","usuarios");
                getUsuariosFromApi();

            }else{                // sincronizacion automatica enabled

                Log.e("automatyc","able");
                utils.llenarListasSync();
                utils.sincronizar();
                if (!utils.consultarTablas()){ // no hay datos para Sync
                    syncServiceStopped.onSyncFinished("nada");
                    Log.e("no hay","datos");
                }
            }
        }else {
            // wifi disabled
            Log.e("wifi","disabled");
            syncServiceStopped.onSyncFinished("wifidisabled");
        }
    }

    private void getUsuariosFromApi() {

        Log.e("in","getUsuariosFromApi");
        Call<List<Persona>> getPersonas = ReforestApiAdapter.getApiService().getPersonas();
        getPersonas.enqueue(new Callback<List<Persona>>() {
            @Override
            public void onResponse(@NonNull Call<List<Persona>> call, @NonNull Response<List<Persona>> response) {

                Log.e("in","Personas on response");
                if (response.isSuccessful()){

                    Log.e("in","response sucessfull");
                    List<Persona> personaList = response.body();
                    if (personaList != null && personaList.size() > 0) {

                        for (Persona p : personaList) {
                            utils.crearPersona(p);
                            if (p == personaList.get(personaList.size() - 1)){

                                Log.e("in","ultima persona");
                                syncServiceStopped.onSyncFinished("SyncUsuariosSuccess");
                                release();
                            }
                        }
                    }

                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Persona>> call, @NonNull Throwable t) {

                Log.e("falla","peticion personas");
                syncServiceStopped.onSyncFinished("Error");
                release();
            }
        });
    }

    private void init() {

    //    prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        utils = new SyncServiceUtils(context, this);
    }

    private void release(){
        utils.releaseHelper();
    }

    @Override
    public void exitosa(boolean success) {

        if(success){

            syncServiceStopped.onSyncFinished("stop");
            release();
            Log.e("exitosa","enviando mensaje stop");
        }else{
            Log.e("NO exitosa","enviando mensaje SyncFiled");

            syncServiceStopped.onSyncFinished("SyncFiled");
            release();
        }
    }
}
