package com.jegg.reforest.Utils;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.jegg.reforest.DBdatos.basededatos;
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
        basededatos datosReforest = OpenHelperManager.getHelper(context, basededatos.class);

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

    public void makeDataUnsyncronized() throws SQLException {

        List<Lote> lotes = daoLotes.queryForAll();
        List<Arbol> arboles = daoArboles.queryForAll();
        List<ArbolEstado> arbolEstados = daoArbolEstado.queryForAll();
        List<ArbolEspecie> arbolEspecies = daoArbolEspecie.queryForAll();
        List<Altura> alturas = daoAltura.queryForAll();
        List<DesarrolloActividades> actividades = daoDesarrolloAct.queryForAll();

        if (lotes.size() > 0){
            for (int i = 0; i < lotes.size(); i++) {
                lotes.get(i).setUploaded(false);
                daoLotes.update(lotes.get(i));
            }
        }

        if (arboles.size() > 0){
            for (int i = 0; i < arboles.size(); i++) {
                arboles.get(i).setUploaded(false);
                daoArboles.update(arboles.get(i));
            }
        }
        if (alturas.size() > 0){
            for (int i = 0; i < alturas.size(); i++) {
                alturas.get(i).setUploaded(false);
                daoAltura.update(alturas.get(i));
            }
        }
        if (arbolEstados.size() > 0){
            for (int i = 0; i < arbolEstados.size(); i++) {
                arbolEstados.get(i).setUploaded(false);
                daoArbolEstado.update(arbolEstados.get(i));
            }
        }
        if (arbolEspecies.size() > 0){
            for (int i = 0; i < arbolEspecies.size(); i++) {
                arbolEspecies.get(i).setUploaded(false);
                daoArbolEspecie.update(arbolEspecies.get(i));
            }
        }
        if (actividades.size() > 0){
            for (int i = 0; i < actividades.size(); i++) {
                actividades.get(i).setUploaded(false);
                daoDesarrolloAct.update(actividades.get(i));
            }
        }






    }

    @Override
    public void releaseHelper() {
        super.releaseHelper();
    }
}
