package com.jegg.reforest.Utils;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.stmt.UpdateBuilder;
import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Tallo;

import java.sql.SQLException;

public class UpdateDb extends SyncServiceUtils{

    public UpdateDb(Context context) {
        super(context);
    }

    public void updateAbol(Arbol arbol) throws SQLException {

        UpdateBuilder<Arbol, String> updateBuilder = this.daoArboles.updateBuilder();
        updateBuilder.where().eq(Constantes.ID, arbol.getId());
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);
        updateBuilder.update();
    }

    public void updateLote(Lote lote) throws SQLException {

        lote.setUploaded(true);
        daoLotes.update(lote);
    }

    public void updateAltura(Altura altura) throws SQLException {

        altura.setUploaded(true);
        daoAltura.update(altura);
    }

    public void update(ArbolEspecie arbolEspecie) throws SQLException {

 //       Log.e("borrando","arbolespecie");

        arbolEspecie.setUploaded(true);
        daoArbolEspecie.update(arbolEspecie);

    }

    public void updateArbolEstado(ArbolEstado arbolEstado) throws SQLException {

  //      Log.e("borrando","arbolestado");

        arbolEstado.setUploaded(true);
        daoArbolEstado.update(arbolEstado);

    }

    public void updateTallo(Tallo tallo) throws SQLException {

        tallo.setUploaded(true);
        daoTallo.update(tallo);
    }


    public void updateDesaAct(DesarrolloActividades desarrolloActividades) throws SQLException {

//        Log.e("borrando","desaAct");
        desarrolloActividades.setUploaded(true);
        daoDesarrolloAct.update(desarrolloActividades);
    }
}