package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Actividad;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.R;
import com.jegg.reforest.Servicios.SinconizacionService;
import com.jegg.reforest.asincronas.PostAsyncrona;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Gson actJson = new Gson();
    basededatos datosReforest;
    //String PATH_BASE_DE_DATOS = "/data/data/com.jegg.reforest/databases/datosReforest.db";

    String PATH_BASE_DE_DATOS = "datosReforest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, SinconizacionService.class));

        if (existeBaseDatos()){
            Log.e("base de datos","");
        }

        datosReforest = OpenHelperManager.getHelper(MainActivity.this,
                        basededatos.class);

            (findViewById(R.id.btn_acceso_main)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IniciarSesion();
                }
            });

        try{
            insertarActividadesEnBd();
            insertarcrearEstadosEnBd();
        }catch (SQLException e){

        }
        }


    private void insertarcrearEstadosEnBd() throws SQLException {

        Dao estadosDao = datosReforest.getEstadoDao();
        List<Estado> listEstados = new ArrayList<>();

            listEstados.add(new Estado("Enfrema"));
        listEstados.add(new Estado("Faltante"));
        listEstados.add(new Estado("Excelente"));
        listEstados.add(new Estado("Muerta"));

        for (int i = 0; i<listEstados.size(); i++){
            estadosDao.create(listEstados.get(i));
        }


    }

    private void insertarActividadesEnBd() throws SQLException {

        Dao actividadesDao = datosReforest.getActividadsDao();
        List<Actividad> listActividades = new ArrayList<>();

        listActividades.add(new Actividad("Sembrar o Plantar"));
        listActividades.add(new Actividad("Abonar"));
        listActividades.add(new Actividad("Fertilización"));
        listActividades.add(new Actividad("Control de Malezas"));
        listActividades.add(new Actividad("Sustitución de plantas"));
        listActividades.add(new Actividad("Preparar terreno"));
        listActividades.add(new Actividad("Enfermedades"));
        listActividades.add(new Actividad("Estado del Arbol"));
        for (int i = 0; i<listActividades.size(); i++){
            actividadesDao.create(listActividades.get(i));
        }
        Actividad prueba = new Actividad("Prueba5");
        prueba.setId(16);
        //actividadesDao.create(prueba);


        Log.e("actividad cadena", prueba.toString());
        //enviarActividad(prueba.toString());

    }

    private void enviarActividad(String s) {

        PostAsyncrona post = new PostAsyncrona(s, MainActivity.this, new PostAsyncrona.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Log.e("Actividad", "Enviada");
                Log.e("Output", output);
            }
        });

        try {
            post.execute("http://181.58.69.50:8080/servicios/actividades/").get();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private boolean existeBaseDatos() {

        boolean b = false;

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
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        OpenHelperManager.releaseHelper();
        super.onDestroy();
    }

}
