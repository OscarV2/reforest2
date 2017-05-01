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
import com.jegg.reforest.Entidades.Departamento;
import com.jegg.reforest.Entidades.Municipio;
import com.jegg.reforest.R;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    basededatos datosReforest;
    String PATH_BASE_DE_DATOS = "/data/data/com.jegg.reforest/databases/datosReforest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datosReforest = OpenHelperManager.getHelper(MainActivity.this,
                        basededatos.class);

        Dao deptdao;

        Dao munidao;
//        try {
           /* deptdao = datosReforest.getDepartamentosDao();
            munidao = datosReforest.getMunicipiosDao();
            Departamento d = new Departamento("Cesar");
            deptdao.create(d);

            Municipio m2 = new Municipio("Valledupar");
            Municipio m = new Municipio("Bosconia");

            m.setIdDepartamento(d);
            m2.setIdDepartamento(d);
            munidao.create(m);
            munidao.create(m2);
Log.e("todo", "bien");




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

            bdReforest = datosReforest.getWritableDatabase();
            bdReforest.close();

        }
*/
            (findViewById(R.id.btn_acceso_main)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IniciarSesion();
                }
            });
        }

    private boolean existeBaseDatos() {

        File dbFile = getApplicationContext().getDatabasePath(PATH_BASE_DE_DATOS);
        return dbFile.exists();
    }

    public void IniciarSesion(){
        Intent intent = new Intent(this, CrearArbol.class);
        startActivity(intent);
        finish();
    }
}
