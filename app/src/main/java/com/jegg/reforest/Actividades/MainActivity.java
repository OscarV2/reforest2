package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableIterable;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.jegg.reforest.Actividades.IniciarSesion;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Actividad;
import com.jegg.reforest.Entidades.Departamento;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.Entidades.Municipio;
import com.jegg.reforest.R;
import com.jegg.reforest.Servicios.SinconizacionService;
import com.jegg.reforest.asincronas.PostAsyncrona;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    basededatos datosReforest;
    String PATH_BASE_DE_DATOS = "/data/data/com.jegg.reforest/databases/datosReforest.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, SinconizacionService.class));
        datosReforest = OpenHelperManager.getHelper(MainActivity.this,
                        basededatos.class);

//        try {
           /* deptdao = datosReforest.getDepartamentosDao();
            munidao = datosReforest.getMunicipiosDao();
            Departamento d = new Departamento("Cesar");


            deptdao = datosReforest.getDepartamentosDao();
            Departamento a = (Departamento) deptdao.queryForId(1);

            ForeignCollection<Municipio> k = a.getMunicipios();
            CloseableWrappedIterable<Municipio> m3 = k.getWrappedIterable();

            for (Municipio muni : m3){
                Log.e("muni: ", muni.getNombre());
            }
          m3.close();

             /*   deps = datosReforest.getDepartamentosDao().queryForAll();
                if (deps.get(0) != null){
                    for (Departamento departamento : deps){
                        Log.e("Departamentoenbd:", departamento.getNombre());
                        List<Municipio> listMuni = departamento.getMunicipios();
                        for (Municipio municipio : listMuni){
                            Log.e("municipio:", municipio.getNombre());
                            Log.d("deptmunicipio:", municipio.getIdDepartamento().getNombre());
                        }
                    }
                }else {

                }*/
/*
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            OpenHelperManager.releaseHelper();

        }
*/
/*
        SQLiteDatabase bdReforest = null;
        if (!existeBaseDatos()){
            // no existe la base de datos
            Log.e("BD","NO EXISTE");
            datosReforest = new basededatos(MainActivity.this, "datosReforest", 1);
            Log.e("nombre DB: ",datosReforest.getDatabaseName());
            Log.e("PATH DB: ", getApplicationContext().getDatabasePath(datosReforest.getDatabaseName()).getPath());

        }
*/
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
        Actividad prueba = new Actividad("Prueba3");
        actividadesDao.create(prueba);

        enviarActividad(prueba.toString());

    }

    private void enviarActividad(String s) {

        PostAsyncrona post = new PostAsyncrona(s, MainActivity.this, new PostAsyncrona.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                Log.e("Actividad", "Enviada");
            }
        });

        try {
            post.execute("http://181.58.69.50:8080/servicios/actividades/").get();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private boolean existeBaseDatos() {

        File dbFile = getApplicationContext().getDatabasePath(PATH_BASE_DE_DATOS);
        return dbFile.exists();
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
