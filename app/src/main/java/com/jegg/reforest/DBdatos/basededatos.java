package com.jegg.reforest.DBdatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jegg.reforest.Utils.Constantes;


public class basededatos extends SQLiteOpenHelper {


    public basededatos(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Constantes.CREATE_TABLA_ESTADO);
        db.execSQL(Constantes.CREATE_TABLA_ESPECIE);
        db.execSQL(Constantes.CREATE_TABLA_DEPARTAMENTO);
        db.execSQL(Constantes.CREATE_TABLA_USUARIOS);
        db.execSQL(Constantes.CREATE_TABLA_ACTIVIDADES);
        db.execSQL(Constantes.CREATE_TABLA_MUNICIPIO);
        db.execSQL(Constantes.CREATE_TABLA_LOTE);
        db.execSQL(Constantes.CREATE_TABLA_ARBOL);
        db.execSQL(Constantes.CREATE_TABLA_ESPECIE_ARBOL);
        db.execSQL(Constantes.CREATE_TABLA_PERSONAS);
        db.execSQL(Constantes.CREATE_TABLA_DESARROLLO_ACTIVIDADES);
        db.execSQL(Constantes.CREATE_TABLA_ARBOL_ESTADO);
        db.execSQL(Constantes.CREATE_TABLA_ALTURA);

        Log.e("DATABASE:","CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
