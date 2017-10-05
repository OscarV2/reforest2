package com.jegg.reforest.actividades;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.jegg.reforest.R;
import com.jegg.reforest.Servicios.SinconizacionService;
import com.jegg.reforest.SyncServiceStopped;
import com.jegg.reforest.Utils.MainActivityAux;

import java.io.File;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity implements SyncServiceStopped{

    SharedPreferences prefs;
    String PATH_BASE_DE_DATOS = "datosReforest";

    ProgressDialog progressDialog;
    boolean inicioSesion, autoSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        inicioSesion = prefs.getBoolean("inicio_sesion", false);
        autoSync = prefs.getBoolean("automatic_sync", false);

        progressDialog = new ProgressDialog(MainActivity.this);

        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Cargando...");
        progressDialog.setMessage("Por favor espere..");
        progressDialog.setCancelable(false);
        final MainActivityAux main = new MainActivityAux(getApplicationContext());

        if (!existeBaseDatos()) {
            Log.e("base de datos", "no hay");

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {

                        main.insertarEspecies();
                        main.insertarActividadesEnBd();
                        main.insertarcrearEstadosEnBd();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

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
        sincronizando();
        }

    private void irMenu() {

        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    private void sincronizando(){

        SinconizacionService sin = new SinconizacionService(MainActivity.this, this);

        if (!inicioSesion){
            progressDialog.show();
            sin.comenzar("downloadUsers");
        }else if (autoSync){
            progressDialog.show();
            sin.comenzar("autoSync");
                   // Intent intent = new Intent(MainActivity.this, SinconizacionService.class);
                  //  startService(intent);
            }
    }

    @Override
    public void onSyncFinished(String msg) {

        Log.e("mensaje", msg);
        switch (msg) {
            case "SyncUsuariosSuccess":
                try {
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "stop":
                try {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Sincronizacion exitosa.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "SyncFiled":
                try {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Sincronizacion fallida, Por favor intente mas tarde.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                progressDialog.dismiss();
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
        super.onDestroy();
    }
}
