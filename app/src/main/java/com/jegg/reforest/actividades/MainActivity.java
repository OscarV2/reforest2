package com.jegg.reforest.actividades;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jegg.reforest.R;
import com.jegg.reforest.Servicios.SinconizacionService;
import com.jegg.reforest.Utils.CerrarDialogo;
import com.jegg.reforest.Utils.MainActivityAux;

import java.io.File;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity{

    SharedPreferences prefs;
    String PATH_BASE_DE_DATOS = "datosReforest";

    ProgressDialog progressDialog;
    boolean inicioSesion, autoSync;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        inicioSesion = prefs.getBoolean("inicio_sesion", false);
        autoSync = prefs.getBoolean("automatic_sync", false);

        myReceiver = new MyReceiver();

        progressDialog = new ProgressDialog(MainActivity.this);

        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Cargando...");
        progressDialog.setMessage("Por favor espere..");
        progressDialog.setCancelable(false);

        if (!existeBaseDatos()){
            Log.e("base de datos","no hay");
            new Handler().postDelayed(new Runnable() {
              @Override
            public void run() {

            progressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        MainActivityAux main = new MainActivityAux(getApplicationContext());
                        main.insertarEspecies();
                        main.insertarActividadesEnBd();
                        main.insertarcrearEstadosEnBd();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

                  }
                    }, 1000);

        }

        (findViewById(R.id.btn_acceso_main)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inicioSesion){

                    irMenu();
                }else{
                    IniciarSesion();
                }
            }
        });
        }

    private void irMenu() {

        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    private class SincTask extends AsyncTask<Context, Void, Boolean>{
        @Override
        protected Boolean doInBackground(Context... contexts) {

            Intent intent = new Intent(contexts[0], SinconizacionService.class);
            startService(intent);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
//            progressDialog.dismiss();
            if (autoSync){

                Toast.makeText(MainActivity.this, "Datos sincronizados exitosamente.", Toast.LENGTH_LONG).show();
            }
            super.onPostExecute(aBoolean);
        }
    }

    private void sincronizando(){

        if (autoSync || (!inicioSesion)){
            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {


                    SincTask sincronizar = new SincTask();
                    sincronizar.execute(MainActivity.this);

                }
            }, 1500);

        }
    }

    @Override
    protected void onStart() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("stop");
        registerReceiver(myReceiver, intentFilter);
        sincronizando();
        super.onStart();
    }

    private class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("stop")) {
                try {
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean existeBaseDatos() {

        boolean b;

            File dbFile = getApplicationContext().getDatabasePath(PATH_BASE_DE_DATOS);
            b = dbFile.exists();
        if (b){
            Log.e("Si","existe.");
        }else {
            Log.e("No","existe.");
        }
        return b;
    }

    public void IniciarSesion(){

        Intent intent = new Intent(this, IniciarSesion.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(myReceiver);
        super.onDestroy();
    }
}
