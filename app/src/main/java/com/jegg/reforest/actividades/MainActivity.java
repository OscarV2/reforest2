package com.jegg.reforest.actividades;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Actividad;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.R;
import com.jegg.reforest.Servicios.SinconizacionService;
import com.jegg.reforest.Utils.HandleEspecies;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    basededatos datosReforest;
    SharedPreferences prefs;
    String PATH_BASE_DE_DATOS = "datosReforest";
    private ProgressDialog progressDialog = null;
    MyReceiver myReceiver;
    boolean inicioSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        inicioSesion = prefs.getBoolean("inicio_sesion", false);
        Log.e("Bool InicioSesion", String.valueOf(inicioSesion));


        datosReforest = OpenHelperManager.getHelper(MainActivity.this,
                basededatos.class);

        (findViewById(R.id.btn_acceso_main)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inicioSesion){

                    irMenu();
                }else{

                    IniciarSesion();
                    if (existeBaseDatos()){
                        Log.e("base de datos","");
                    }
                }
            }
        });

        try{
            insertarActividadesEnBd();
            insertarcrearEstadosEnBd();
            insertarEspecies();
        }catch (SQLException e){

            e.printStackTrace();
        }
        }

    private void irMenu() {

        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    private void insertarEspecies(){

        HandleEspecies handle = new HandleEspecies(this);
        String[] lista = handle.getListaEspecies();
        try {
            Dao<Especie, Integer> especiesDao = datosReforest.getEspeciesDao();
            for (String especie : lista){
                especiesDao.create(new Especie(especie));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertarcrearEstadosEnBd() throws SQLException {

        Dao<Estado, Integer> estadosDao = datosReforest.getEstadoDao();
        List<Estado> listEstados = new ArrayList<>();

        listEstados.add(new Estado("Bueno"));
        listEstados.add(new Estado("Enfremo"));
        listEstados.add(new Estado("Resiembra"));
        listEstados.add(new Estado("Erradicado"));

        for (int i = 0; i<listEstados.size(); i++){
            estadosDao.create(listEstados.get(i));
        }


    }

    private void insertarActividadesEnBd() throws SQLException {

        Dao<Actividad, Integer> actividadesDao = datosReforest.getActividadsDao();
        List<Actividad> listActividades = new ArrayList<>();

        listActividades.add(new Actividad("Preparar terreno y sembrar"));
        listActividades.add(new Actividad("Abonar"));
        listActividades.add(new Actividad("Control de Malezas"));
        listActividades.add(new Actividad("Fertilización"));
        listActividades.add(new Actividad("Sustitución de plantas"));
        listActividades.add(new Actividad("Enfermedades"));
        listActividades.add(new Actividad("Estado del Arbol"));
        for (int i = 0; i<listActividades.size(); i++){
            actividadesDao.create(listActividades.get(i));
        }

    }

    private void sincronizando(){

      //  progressDialog = ProgressDialog.show(MainActivity.this, "Sincronizando...", "Por favor espere..", true);

//        progressDialog.setCancelable(false);
  //      if (progressDialog.isShowing()){

            Log.e("proegress", "showing");
            startService(new Intent(MainActivity.this, SinconizacionService.class));
    //    }
    }

    private class MyReceiver extends BroadcastReceiver {
        //Recibo Mi posicion
        @Override
        public void onReceive(Context arg0, Intent arg1) {
/*
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
                Log.e("progress","dissmiss");
            }
*/
        }
    }

    @Override
    protected void onStart() {

        //Register BroadcastReceiver
        //to receive event from our service
    //    myReceiver = new MyReceiver();
    //    IntentFilter intentFilter = new IntentFilter();
    //    intentFilter.addAction(SinconizacionService.MY_ACTION);
    //    registerReceiver(myReceiver, intentFilter);

        sincronizando();
        super.onStart();
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
       // unregisterReceiver(myReceiver);
       // progressDialog.dismiss();
        OpenHelperManager.releaseHelper();
        super.onDestroy();
    }

}
