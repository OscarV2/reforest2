package com.jegg.reforest.Utils;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.stmt.UpdateBuilder;
import com.jegg.reforest.Entidades.Actividad;
import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.controladores.ControllerEspecies;

import java.sql.SQLException;
import java.util.List;

public class MainActivityAux extends SyncServiceUtils{

    private Context context;

    public MainActivityAux(Context context) {

        super(context);
        this.context = context;
    }

    public void insertarEspecies() throws SQLException {

        ControllerEspecies handle = new ControllerEspecies(context);
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
    /*
    public void restaurarDatos() throws SQLException {

        Log.e("updating","function");
        List<Lote> lotes = daoLotes.queryForAll();
        List<Altura> alturas= daoAltura.queryForAll();
        List<Arbol> arboles = daoArboles.queryForAll();
        List<DesarrolloActividades> listDesa = daoDesarrolloAct.queryForAll();
        List<ArbolEspecie> listArbolEspecie = daoArbolEspecie.queryForAll();
        List<ArbolEstado> listArbolEstado = daoArbolEstado.queryForAll();
        if (lotes.size() > 0){
            for (int i = 0; i < lotes.size(); i++) {

                UpdateBuilder<Lote, String> updateBuilder = this.daoLotes.updateBuilder();
                updateBuilder.where().eq("id", lotes.get(i).getId());
                updateBuilder.updateColumnValue(Constantes.UPLOADED, false);
                updateBuilder.update();
                Log.e("updating","Lote");
            }
        }
        for (int i = 0; i < alturas.size(); i++) {

            UpdateBuilder<Altura, Integer> updateBuilder = this.daoAltura.updateBuilder();
            updateBuilder.where().eq("id", alturas.get(i).getId());
            updateBuilder.updateColumnValue(Constantes.UPLOADED, false);
            updateBuilder.update();
            Log.e("updating","Altura");

        }
        for (int i = 0; i < listArbolEspecie.size(); i++) {

            UpdateBuilder<ArbolEspecie, Integer> updateBuilder = this.daoArbolEspecie.updateBuilder();
            updateBuilder.where().eq("idArbolEspecie", listArbolEspecie.get(i).getId());
            updateBuilder.updateColumnValue(Constantes.UPLOADED, false);
            updateBuilder.update();
            Log.e("updating","ArbolEspecie");

        }
        for (int i = 0; i < arboles.size(); i++) {

            UpdateBuilder<Arbol, String> updateBuilder = this.daoArboles.updateBuilder();
            updateBuilder.where().eq(Constantes.ID_ARBOL, arboles.get(i).getId());
            updateBuilder.updateColumnValue(Constantes.UPLOADED, false);
            updateBuilder.update();
            Log.e("updating","Arbol");

        }
        for (int i = 0; i < listArbolEstado.size(); i++) {

            UpdateBuilder<ArbolEstado, Integer> updateBuilder = this.daoArbolEstado.updateBuilder();
            updateBuilder.where().eq("id", listArbolEstado.get(i).getId());
            updateBuilder.updateColumnValue(Constantes.UPLOADED, false);
            updateBuilder.update();
            Log.e("updating","arbolEstado");

        }
        for (int i = 0; i < listDesa.size(); i++) {

            UpdateBuilder<DesarrolloActividades, Integer> updateBuilder = this.daoDesarrolloAct.updateBuilder();
            updateBuilder.updateColumnValue(Constantes.UPLOADED, false);
            updateBuilder.where().eq("id", listDesa.get(i).getId());
            updateBuilder.update();
            Log.e("updating","desa");

        }
    }
    */
    @Override
    public void releaseHelper() {
        super.releaseHelper();
    }
}
