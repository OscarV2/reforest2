package com.jegg.reforest.Actividades;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jegg.reforest.Actividades.IniciarSesion;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Departamento;
import com.jegg.reforest.R;

import java.io.File;
import java.sql.SQLException;
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

        Dao dao;
        try {
            dao = datosReforest.getDepartamentosDao();

//            dao.create(d);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            OpenHelperManager.releaseHelper();

        }

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
