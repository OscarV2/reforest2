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

public class UpdateDb extends SyncServiceUtils{

    public UpdateDb(Context context) {
        super(context);

    }

    public void updateAbol(Arbol arbol) throws SQLException {

        UpdateBuilder<Arbol, String> updateBuilder = this.daoArboles.updateBuilder();
        updateBuilder.where().eq(Constantes.ID_ARBOL, arbol.getId());
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);
        updateBuilder.update();

    }

    public void updateLote(Lote lote) throws SQLException {

        UpdateBuilder<Lote, String> updateBuilder = this.daoLotes.updateBuilder();
        updateBuilder.where().eq("id", lote.getId());
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);
        updateBuilder.update();

    }

    public void updateAltura(Altura altura) throws SQLException {

        altura.setUploaded(true);
        daoAltura.update(altura);
      /*  UpdateBuilder<Altura, Integer> updateBuilder = this.daoAltura.updateBuilder();
        updateBuilder.where().eq("id", altura.getId());
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);
        updateBuilder.update();
*/
    }

    public void update(ArbolEspecie arbolEspecie) throws SQLException {

        arbolEspecie.setUploaded(true);
        daoArbolEspecie.update(arbolEspecie);

      /*  UpdateBuilder<ArbolEspecie, Integer> updateBuilder = this.daoArbolEspecie.updateBuilder();
        updateBuilder.where().eq("idArbolEspecie", arbolEspecie.getId());
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);
        updateBuilder.update();
*/
    }

    public void updateArbolEstado(ArbolEstado arbolEstado) throws SQLException {

        arbolEstado.setUploaded(true);
        daoArbolEstado.update(arbolEstado);
    /*    UpdateBuilder<ArbolEstado, Integer> updateBuilder = this.daoArbolEstado.updateBuilder();
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);
        updateBuilder.where().eq("id", arbolEstado.getId());
        updateBuilder.update();
*/
    }

    public void updateDesaAct(DesarrolloActividades desarrolloActividades) throws SQLException {

        desarrolloActividades.setUploaded(true);
        daoDesarrolloAct.update(desarrolloActividades);
        /*UpdateBuilder<DesarrolloActividades, Integer> updateBuilder = this.daoDesarrolloAct.updateBuilder();
        updateBuilder.updateColumnValue(Constantes.UPLOADED, true);
        updateBuilder.where().eq("uploaded", false);
        updateBuilder.update();
*/
    }
}
