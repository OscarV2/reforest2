package com.jegg.reforest.Servicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.Utils.CerrarDialogo;
import com.jegg.reforest.Utils.SyncServiceUtils;
import com.jegg.reforest.api.ReforestApiAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinconizacionService extends Service {

    SharedPreferences prefs;
    private SyncServiceUtils utils;
    CerrarDialogo cerrarDialogo;
    private final IBinder mBinder = new LocalBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        init();
        ConnectivityManager conMan = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMan.getActiveNetworkInfo() != null &&
                conMan.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED){

            if (utils.checkPersonas()){         // no hay usuarios en la app, se procede a bajarlos de la api
                Log.e("no hay","usuarios");
                getUsuariosFromApi();

            }else{                // sincronizacion automatica enabled

                Log.e("automatyc","able");
                utils.consultarTablas();
                utils.sincronizar();
                stopSelf();
            }
        }else {
            // wifi disabled
            Log.e("wifi","disabled");
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
                    stopSelf();
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Persona>> call, @NonNull Throwable t) {

                Log.e("falla","peticion personas");
                stopSelf();
            }
        });
    }

    private void init() {

        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        utils = new SyncServiceUtils(getApplicationContext());
    }

    public class LocalBinder extends Binder {
        public SinconizacionService getServiceInstance(){
            return SinconizacionService.this;
        }
    }

    @Override
    public void onDestroy() {
        utils.releaseHelper();

        Intent i = new Intent();
        i.setAction("stop");
        Log.e("servicio","stopped");
        sendBroadcast(i);
        super.onDestroy();
    }

}
