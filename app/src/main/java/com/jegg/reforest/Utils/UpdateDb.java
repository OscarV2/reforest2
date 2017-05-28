package com.jegg.reforest.Utils;

import android.content.Context;

import com.j256.ormlite.stmt.UpdateBuilder;
import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Lote;

import java.sql.SQLException;

class UpdateDb extends SyncServiceUtils{

    UpdateDb(Context context) {
        super(context);

    }

    void updateAbol(Arbol arbol) throws SQLException {

        UpdateBuilder<Arbol, String> updateBuilder = this.daoArboles.updateBuilder();
        updateBuilder.where().eq(Constantes.ID_ARBOL, arbol.getId());
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);
        updateBuilder.update();

    }

    void updateLote(Lote lote) throws SQLException {

        UpdateBuilder<Lote, String> updateBuilder = this.daoLotes.updateBuilder();
        updateBuilder.where().eq("id", lote.getId());
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);
        updateBuilder.update();

    }

    void updateAltura(Altura altura) throws SQLException {

        UpdateBuilder<Altura, Integer> updateBuilder = this.daoAltura.updateBuilder();
        updateBuilder.where().eq("id", altura.getId());
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);
        updateBuilder.update();

    }

    void update(ArbolEspecie arbolEspecie) throws SQLException {

        UpdateBuilder<ArbolEspecie, Integer> updateBuilder = this.daoArbolEspecie.updateBuilder();
        updateBuilder.where().eq("idArbolEspecie", arbolEspecie.getId());
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);

        updateBuilder.update();

    }

    void updateArbolEstado(ArbolEstado arbolEstado) throws SQLException {

        UpdateBuilder<ArbolEstado, Integer> updateBuilder = this.daoArbolEstado.updateBuilder();
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);
        updateBuilder.where().eq("id", arbolEstado.getId());
        updateBuilder.update();

    }

    void updateDesaAct(DesarrolloActividades desarrolloActividades) throws SQLException {

        UpdateBuilder<DesarrolloActividades, Integer> updateBuilder = this.daoDesarrolloAct.updateBuilder();
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);
        updateBuilder.where().eq("id", desarrolloActividades.getId());
        updateBuilder.update();

    }
}
