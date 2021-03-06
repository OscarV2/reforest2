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

public class SincronizacionService implements SincronizacionExitosa{

    private SyncServiceUtils utils;
    private SyncServiceStopped syncServiceStopped;
    private Context context;

    public SincronizacionService(Context context, SyncServiceStopped syncServiceStopped) {
        this.syncServiceStopped = syncServiceStopped;
        this.context = context;
    }

    public void comenzar(String msg){
        init();
        ConnectivityManager conMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMan.getActiveNetworkInfo() != null &&
                conMan.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED){

            if (utils.checkPersonas() && msg.equals("downloadUsers")){         // no hay usuarios en la app, se procede a bajarlos de la api
                getUsuariosFromApi();

            }else if (msg.equals("autoSync")){      // sincronizacion automatica enabled

                utils.llenarListasSync();

                if (utils.consultarTablas()){ // si hay datos para Sync

                    utils.sincronizar();
                }else{

                    syncServiceStopped.onSyncFinished("nada");

                }
            }else if(!utils.checkPersonas() && msg.equals("downloadUsers")){
                syncServiceStopped.onSyncFinished("nada");
            }
        }else {
            // wifi disabled
            Log.e("wifi","disabled");
            syncServiceStopped.onSyncFinished("wifidisabled");
        }
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
