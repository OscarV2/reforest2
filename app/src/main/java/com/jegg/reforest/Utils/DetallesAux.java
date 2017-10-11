package com.jegg.reforest.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.jegg.reforest.Entidades.Actividad;
import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.Entidades.Tallo;
import com.jegg.reforest.actividades.Lotes;

import java.sql.SQLException;
import java.util.List;

public class DetallesAux extends SyncServiceUtils {

    public Context context;
    public DetallesAux(Context context) {
        super(context);
        this.context = context;

    }

    public Especie getEspecie(String especie){

        Especie especieEntidad = null;
        QueryBuilder<Especie, Integer> qBEspecie = daoEspecie.queryBuilder();
        Where<Especie, Integer> where = qBEspecie.where();
        try {
            where.eq(Constantes.ESPECIE_ESPECIE, especie);
        } catch (SQLException e) {

            e.printStackTrace();

        }

        PreparedQuery<Especie> pqEspecie;
        try {
            pqEspecie = qBEspecie.prepare();
            especieEntidad = daoEspecie.query(pqEspecie).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return especieEntidad;
    }

    public void createEntitiesActivity6(DesarrolloActividades desarrolloActividad, ArbolEspecie arbolEspecie,
                                        Altura alturaEntity, ArbolEstado arbolEstado) throws SQLException {

        daoDesarrolloAct.create(desarrolloActividad);
        daoArbolEspecie.create(arbolEspecie);
        daoAltura.create(alturaEntity);
        daoArbolEstado.create(arbolEstado);
    }

    public void guardarActividad_2_3_4_6_7_8(String fotoPath, String comentariosActividad,
                                              String fechaActividad, Actividad actividad, Arbol arbol,
                                              Persona usuario) {

        try {
            DesarrolloActividades desa = new DesarrolloActividades(fotoPath, comentariosActividad,
                    fechaActividad, actividad, arbol, usuario);

            daoDesarrolloAct.create(desa);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Toast.makeText(this.context, "Actividad exitosa.", Toast.LENGTH_SHORT).show();
        //volverALotes();
        Activity activity = (Activity)context;
        Intent i = new Intent(context, Lotes.class);
        activity.startActivity(i);
        activity.finish();
    }


    public void crearEstado(ArbolEstado arbolEstado) throws SQLException {

        Log.e("id de estado: ",String.valueOf(arbolEstado.getId()));
        List<ArbolEstado> todos = daoArbolEstado.queryForAll();
        for (int i = 0; i < todos.size(); i++){
if (todos.get(i).getId() != null){
    Log.e("id estado lista", String.valueOf(i) + " " + todos.get(i));

}else{Log.e("id estado lista", String.valueOf(i) + "NULL " );}

            }

        daoArbolEstado.create(arbolEstado);
    }

    public Actividad getActividad(int idActividad) throws SQLException {

        return daoActividad.queryForId(idActividad);
    }

    public Persona getPersona(int idPersona) throws SQLException {

        return daoPersonas.queryForId(idPersona);
    }

    public List<Estado> getListEstados() throws SQLException {

        return daoEstado.queryForAll();
    }

    public Arbol getArbol(String idArbol) throws SQLException {

        return daoArboles.queryForId(idArbol);
    }

    public void crearArbolEspecie(ArbolEspecie arbolEspecie) throws SQLException {

        daoArbolEspecie.create(arbolEspecie);
    }

    public void crearAltura(Altura altura) throws SQLException {

        daoAltura.create(altura);
    }

    public void crearArbol(Arbol arbol) throws SQLException {

        daoArboles.create(arbol);
    }

    public Arbol getArbol(int numArbol, String idLote) throws SQLException {

        Arbol arbol;
        QueryBuilder<Arbol, String> qBArbol = daoArboles.queryBuilder();

        Where where = qBArbol.where();
        where.eq("numArbol", numArbol).and().eq(Constantes.LOTE_ID_ARBOL, idLote);

        PreparedQuery<Arbol> pqArbol = qBArbol.prepare();
        arbol = daoArboles.query(pqArbol).get(0);
        return arbol;

    }

    public void crearTallo(String medida, Arbol arbol){

        try {
            daoTallo.create(new Tallo(arbol, medida));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseHelper() {
        Log.e("release","detallesAux");
        super.releaseHelper();
    }
}
