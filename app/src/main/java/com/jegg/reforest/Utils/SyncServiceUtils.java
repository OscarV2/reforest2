package com.jegg.reforest.Utils;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.asincronas.PostAsyncrona;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by oscarvc on 10/05/17.
 */

public class SyncServiceUtils {

    private PostAsyncrona postAsync;
    Context context;
    private basededatos datosReforest;
    Dao daoDesarrolloAct, daoActividad, daoEspecie,
            daoArbolEspecie, daoEstado, daoArbolEstado,
            daoArboles, daoAltura, daoLotes,
            daoPersonas;
    List<Persona> listaUsuarios = new ArrayList<>();
    List<Lote> listaLotes = new ArrayList<>();
    List<Arbol> listaArbol = new ArrayList<>();
    List<DesarrolloActividades> listaDesarrolloAct = new ArrayList<>();
    List<Altura> listaAltura = new ArrayList<>();
    List<Estado> listaEstados = new ArrayList<>();
    List<ArbolEstado> listaArbolEstado = new ArrayList<>();
    List<ArbolEspecie> listaArbolEspecie = new ArrayList<>();
    List<Especie> listaEspecie = new ArrayList<>();

    String respuesta;

    public boolean checkTablas() {

        boolean siHayRegistros = false;
        consultarTablas();
        if (!(listaDesarrolloAct.size() == 0 || listaLotes.size() == 0 || listaArbol.size() == 0)) {
            siHayRegistros = true;
        }

        return siHayRegistros;
    }

    private void consultarTablas() {

        try {
            listaArbol = daoArboles.queryForAll();
            listaDesarrolloAct = daoDesarrolloAct.queryForAll();
            listaLotes = daoLotes.queryForAll();
            listaAltura = daoAltura.queryForAll();
            listaArbolEspecie = daoArbolEspecie.queryForAll();
            listaEspecie = daoEspecie.queryForAll();
            listaArbolEstado = daoArbolEstado.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void sincronizar(){

        Log.e("numero lotes", String.valueOf(listaLotes.size()));
        Log.e("numero arboles", String.valueOf(listaArbol.size()));
        Log.e("numDesaAct ", String.valueOf(listaDesarrolloAct.size()));
        Log.e("numAlturas ", String.valueOf(listaAltura.size()));
        Log.e("numEspecies ", String.valueOf(listaEspecie.size()));
        Log.e("numArbolEstados ", String.valueOf(listaArbolEstado.size()));
        Log.e("numArbolEspecies ", String.valueOf(listaArbolEspecie.size()));



        sincronizarLotes();
        sincronizarArboles();
        sincronizarAlturas();
        sincronizarEspecie();
        sincronizarArbolEstado();

        sincronizarArbolEspecie();
        sincronizarDesarrolloAct();

    }




    private void sincronizarLotes() {

        //sinc lotes
        for (int i = (listaLotes.size() - 1); i>=0; i=i-1){


            Lote lote = listaLotes.get(i);
            postAsync = new PostAsyncrona(lote.toString(), context, new PostAsyncrona.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    Log.e("sinc lote ",output);

                }
            });
            try {
                 String respuesta = postAsync.execute(Constantes.POST_LOTE).get();
                if (respuesta.equals("ErrorA")){
                    break;
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    private void sincronizarArboles() {

        //sinc arboles
        for (int i = (listaArbol.size()-1); i>=0; i = i-1){

            Arbol arbol = listaArbol.get(i);
            postAsync = new PostAsyncrona(arbol.toString(), context, new PostAsyncrona.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    Log.e("sinc arbol ",output);
                }
            });
            try {
                String respuesta = postAsync.execute(Constantes.POST_ARBOL).get();
                if (respuesta.equals("ErrorA")){
                    break;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private void sincronizarEspecie() {

        if (listaEspecie.size() > 0){

            //sinc Especie
            for (int i = listaEspecie.size()-1; i==0; i--){

                Especie especie = listaEspecie.get(i);
                //Log.e("especie cadena", especie.toString());
                postAsync = new PostAsyncrona(especie.toString(), context, new PostAsyncrona.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Log.e("sinc especie ",output);
                    }
                });
                try {
                    String respuesta = postAsync.execute(Constantes.POST_ESPECIE).get();
                    if (respuesta.equals("ErrorA")){
                        break;
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }else{Log.e("esta vacia", "Especie");}
    }

    private void sincronizarArbolEstado() {

        if (listaArbolEstado.size() > 0){

            //sinc Estado
            for (ArbolEstado estado: listaArbolEstado){
                //Log.e("sinc Especie arbol","dentro del for");
                postAsync = new PostAsyncrona(estado.toString(), context, new PostAsyncrona.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Log.e("sinc ArbolEstado ",output);
                    }
                });
                try {
                    postAsync.execute(Constantes.POST_ESTADO_ARBOL).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }else{Log.e("esta vacia", "arbolEstado");}

    }

    private void sincronizarArbolEspecie() {

        if (listaArbolEspecie.size() > 0){


            //sinc ArbolEspecie
            for (ArbolEspecie arbolEspecie: listaArbolEspecie){

                //Log.e("Arbol especie", arbolEspecie.toString());
                postAsync = new PostAsyncrona(arbolEspecie.toString(), context, new PostAsyncrona.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Log.e("sinc especieArbol ",output);
                    }
                });
                try {
                    postAsync.execute(Constantes.POST_ESPECIE_ARBOL).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }else{Log.e("esta vacia", "ArbolEspecie");}
    }

    private void sincronizarAlturas() {

        if (listaAltura.size() > 0){

            //sinc Alturas
            for (Altura altura: listaAltura){
                //Log.e("sincronizando alturas","dentro del for");
                postAsync = new PostAsyncrona(altura.toString(), context, new PostAsyncrona.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        Log.e("sinc alturas ",output);
                    }
                });
                try {
                    postAsync.execute(Constantes.POST_ALTURA).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }else{Log.e("esta vacia", "Alturas");}
    }

    private void sincronizarDesarrolloAct() {

        //sinc lotes
        for (DesarrolloActividades DesaAct: listaDesarrolloAct){
          /*  Log.e("desaAct", DesaAct.toString());
            Log.e("comentario", DesaAct.getComentario());
            Log.e("fecha", Constantes.sdf.format(DesaAct.getFecha()));
            Log.e("idActividad", String.valueOf(DesaAct.getIdActividad().getId()));
            Log.e("arbol_id", String.valueOf(DesaAct.getArbol().getId()) + Constantes.SERIAL);
            Log.e("personas_id", String.valueOf(DesaAct.getPersona().getId()));
            Log.e("url", DesaAct.getUrlFoto());
            Log.e("tamaño cadena", String.valueOf(DesaAct.getUrlFoto().length()));
*/
            postAsync = new PostAsyncrona(DesaAct.toString(), context, new PostAsyncrona.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    Log.e("sinc desaAct output ",output);
                }
            });
            try {
                postAsync.execute(Constantes.POST_DESARROLLO_ACTIVIDAD).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public SyncServiceUtils(Context context) {
        this.context = context;

        datosReforest = OpenHelperManager.getHelper(context, basededatos.class);

        try {
            daoLotes = datosReforest.getLoteDao();

            daoArboles = datosReforest.getArbolDao();
            daoActividad = datosReforest.getActividadsDao();
            daoDesarrolloAct = datosReforest.getDesarrolloActividadesDao();

            daoEspecie = datosReforest.getEspeciesDao();
            daoArbolEspecie = datosReforest.getArbolEspeciesDao();

            daoEstado = datosReforest.getEstadoDao();
            daoArbolEstado = datosReforest.getArbolEstadosDao();

            daoAltura = datosReforest.getAlturaDao();
            daoPersonas = datosReforest.getPersonasDao();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkPersonas() {

        try {

            listaUsuarios = daoPersonas.queryForAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return (listaUsuarios.size() == 0);
    }

    public void crearPersona(Persona persona) {

        try {
            daoPersonas.create(persona);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void releaseHelper(){
        OpenHelperManager.releaseHelper();
    }

    public List<Lote> getListaLotes(){

        try {
            listaLotes = daoLotes.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaLotes;

    }

    public void getArbolesSembrados(){

    }
}







