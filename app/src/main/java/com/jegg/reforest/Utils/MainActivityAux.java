package com.jegg.reforest.Utils;

import android.content.Context;
import android.util.Log;

import com.jegg.reforest.Entidades.Actividad;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.controladores.HandleEspecies;

import java.sql.SQLException;
import java.util.List;

public class MainActivityAux extends SyncServiceUtils{

    private Context context;

    public MainActivityAux(Context context) {

        super(context);
        this.context = context;
    }

    public void insertarEspecies() throws SQLException {

        HandleEspecies handle = new HandleEspecies(context);
        String[] lista = handle.getListaEspecies();
        List<Especie> especieList = daoEspecie.queryForAll();
        if (especieList.size() <= 0){

                for (String especie : lista){
                    daoEspecie.create(new Especie(especie));
                }

            Log.e("Add Especies", "exitoso");
        }

    }

    public void insertarcrearEstadosEnBd() throws SQLException {

        List<Estado> listEstados = daoEstado.queryForAll();

        if (listEstados.size() <= 0){

            listEstados.add(new Estado("Bueno"));
            listEstados.add(new Estado("Enfremo"));
            listEstados.add(new Estado("Resiembra"));
            listEstados.add(new Estado("Erradicado"));

            for (int i = 0; i<listEstados.size(); i++){

                daoEstado.create(listEstados.get(i));
            }
        }
    }

    public void insertarActividadesEnBd() throws SQLException {

        List<Actividad> listActividades = daoActividad.queryForAll();

        if (listActividades.size() <= 0){

            listActividades.add(new Actividad("Preparar terreno y sembrar"));
            listActividades.add(new Actividad("Abonar"));
            listActividades.add(new Actividad("Control de Malezas"));
            listActividades.add(new Actividad("Fertilización"));
            listActividades.add(new Actividad("Sustitución de plantas"));
            listActividades.add(new Actividad("Enfermedades"));
            listActividades.add(new Actividad("Estado del Arbol"));
            for (int i = 0; i<listActividades.size(); i++){
                daoActividad.create(listActividades.get(i));

            }
        }
    }

    @Override
    public void releaseHelper() {
        super.releaseHelper();
    }
}
