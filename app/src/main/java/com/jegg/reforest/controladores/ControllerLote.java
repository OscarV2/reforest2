package com.jegg.reforest.controladores;

import android.content.Context;
import android.util.Log;

import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Utils.SyncServiceUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControllerLote extends SyncServiceUtils {

    private Lote lote;
    private List<Arbol> listaArbol = new ArrayList<>();
    private List<DesarrolloActividades> listaDesarrolloAct = new ArrayList<>();
    private List<Altura> listaAltura = new ArrayList<>();
    private List<ArbolEstado> listaArbolEstado = new ArrayList<>();
    private List<ArbolEspecie> listaArbolEspecie = new ArrayList<>();


    public ControllerLote(Context context, Lote lote) {

        super(context);
        this.lote = lote;
        listaArbol = this.lote.getArboles();

    }

    public void eliminar(){

        if (!(listaArbol.size() > 0)){

            eliminarLote();
        }else {

            buscarRegistros();
            try {
                eliminarRegistros();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void eliminarRegistros() throws SQLException {

        daoLotes.deleteById(lote.getId());
        daoAltura.delete(listaAltura);
        daoArboles.delete(listaArbol);
        daoArbolEspecie.delete(listaArbolEspecie);
        daoArbolEstado.delete(listaArbolEstado);
        daoDesarrolloAct.delete(listaDesarrolloAct);

        Log.e("eliminar registros","successfull");
    }

    private void buscarRegistros(){

        for(Arbol arbol : listaArbol){

            listaAltura = arbol.getAlturas();
            listaArbolEspecie = arbol.getArbolEspecie();
            listaArbolEstado = arbol.getArbolEstados();
            listaDesarrolloAct = arbol.getDesarrolloActividades();
        }
    }

    public void eliminarLote(){

        try {
            daoLotes.deleteById(lote.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getLotePersona(int Id){


    }

    @Override
    public void releaseHelper() {
        super.releaseHelper();
    }
}
