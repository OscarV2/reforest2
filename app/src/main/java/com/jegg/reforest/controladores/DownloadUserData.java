package com.jegg.reforest.controladores;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jegg.reforest.Entidades.Actividad;
import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.Utils.CerrarDialogo;
import com.jegg.reforest.Utils.SyncServiceUtils;
import com.jegg.reforest.api.ReforestApiAdapter;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadUserData extends SyncServiceUtils {

    private int idPersona;
    private Persona persona;
    private Context context;
    private CerrarDialogo cerrarDialogo;

    public DownloadUserData(Context context,
                            CerrarDialogo cerrarDialogo1) {
        super(context);
        this.context = context;
        this.cerrarDialogo = cerrarDialogo1;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
        this.idPersona = persona.getId();
    }

    public void descargar(){
        descargarLote();
    }

    private void descargarLote(){

        Call<List<Lote>> getLotes = ReforestApiAdapter.getApiService()
                .getLotes(idPersona);

        getLotes.enqueue(new Callback<List<Lote>>() {
            @Override
            public void onResponse(Call<List<Lote>> call, Response<List<Lote>> response) {

                List<Lote> lotes = response.body();
                if (response.isSuccessful() && lotes != null){
                    if (lotes.size() > 0){
                        for (Lote lote: lotes){
                            try {
                                lote.setUploaded(true);
                                lote.setPersona(persona);
                                daoLotes.create(lote);

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }finally {
                                //descargar arboles
                                descargarArboles(lote);
                            }

                        }

                    }else{
                        Log.e("tamaño de","lista de lotes es cero");

                        cerrarDialogo.cerrardialogo();}
                }else{
                    Log.e("lotes response","was not successfull");

                    Toast.makeText(context, "No se pudo sincronizar sus datos, revise su conexion" +
                            " a Internet", Toast.LENGTH_SHORT).show();
                    cerrarDialogo.cerrardialogo();
                    }
            }

            @Override
            public void onFailure(Call<List<Lote>> call, Throwable t) {

                Log.e("fallo","descarga de lotes");
                cerrarDialogo.cerrardialogo();
                Toast.makeText(context, "No se pudo sincronizar sus datos, revise su conexion" +
                        " a Internet", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void descargarArboles(final Lote idLote){

        Call<List<Arbol>> getArboles = ReforestApiAdapter.getApiService()
                .getArboles(idLote.getId());

        getArboles.enqueue(new Callback<List<Arbol>>() {
            @Override
            public void onResponse(Call<List<Arbol>> call, Response<List<Arbol>> response) {
                List<Arbol> lista = response.body();
                if (response.isSuccessful() && lista != null){
                    Log.e("Arbol response es", "successfull y lista no es nula");
                    if (lista.size() > 0){
                        for (Arbol arbol: lista){
                            try {
                                arbol.setUploaded(true);
                                arbol.setLote(idLote);
                                daoArboles.create(arbol);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }finally {
                                Log.e("arbol finally","descargando tablas");
                                descargarAlturas(arbol);
                                descargarArbolEstado(arbol);
                                descargarArbolEspecie(arbol);
                                descargarDesaActividades(arbol);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Arbol>> call, Throwable t) {

                Log.e("peticion failed"," ARBOL PERSONAS");
            }
        });
    }

    private void descargarAlturas(final Arbol idArbol){
        Log.e("descargando","alturas");
        Call<List<Altura>> getAlturas = ReforestApiAdapter.getApiService()
                .getAlturas(idArbol.getId());

        getAlturas.enqueue(new Callback<List<Altura>>() {
            @Override
            public void onResponse(Call<List<Altura>> call, Response<List<Altura>> response) {
                List<Altura> lista = response.body();
                if (response.isSuccessful() && lista != null){
                    if (lista.size() > 0){
                        for (Altura altura: lista){
                            try {
                                altura.setUploaded(true);
                                altura.setArbol(idArbol);
                                daoAltura.create(altura);

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Altura>> call, Throwable t) {
                Log.e("peticion failed","Alturas  PERSONAS");
            }
        });
    }

    private void descargarArbolEstado(final Arbol idArbol){

        Call<List<ArbolEstado>> getEstadosArboles = ReforestApiAdapter.getApiService()
                .getArbolEstado(idArbol.getId());

        getEstadosArboles.enqueue(new Callback<List<ArbolEstado>>() {
            @Override
            public void onResponse(Call<List<ArbolEstado>> call, Response<List<ArbolEstado>> response) {

                List<ArbolEstado> lista = response.body();
                if (response.isSuccessful() && lista != null){

                    if (lista.size() > 0){
                        for (ArbolEstado arbolEstado: lista){
                            try {
                                arbolEstado.setUploaded(true);
                                arbolEstado.setArbol(idArbol);
                                Estado estado = daoEstado.queryForId(arbolEstado.getEstado_id());
                                arbolEstado.setEstado(estado);
                                daoArbolEstado.create(arbolEstado);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ArbolEstado>> call, Throwable t) {
                Log.e("peticion failed","ARBOL ESTADO  PERSONAS");
            }
        });
    }

    private void descargarArbolEspecie(final Arbol idArbol){

        Call<List<ArbolEspecie>> getArbolEspecies = ReforestApiAdapter.getApiService()
                .getArbolEspecies(idArbol.getId());

        getArbolEspecies.enqueue(new Callback<List<ArbolEspecie>>() {
            @Override
            public void onResponse(Call<List<ArbolEspecie>> call, Response<List<ArbolEspecie>> response) {

                List<ArbolEspecie> lista = response.body();
                if (response.isSuccessful() && lista != null){
                    if (lista.size() > 0){
                        for (ArbolEspecie arbolEspecie: lista){
                            try {
                                arbolEspecie.setUploaded(true);
                                arbolEspecie.setArbol(idArbol);
                                Especie especie = daoEspecie.queryForId(arbolEspecie.getEspecie_id());
                                arbolEspecie.setEspecie(especie);
                                daoArbolEspecie.create(arbolEspecie);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<List<ArbolEspecie>> call, Throwable t) {
                Log.e("peticion failed","ARBOL ESPECIE  PERSONAS");
            }
        });

    }

    private void descargarDesaActividades(final Arbol arbol){

        Call<List<DesarrolloActividades>> getDesaAct = ReforestApiAdapter.getApiService()
                .getDesaActPersona(idPersona);

        getDesaAct.enqueue(new Callback<List<DesarrolloActividades>>() {
            @Override
            public void onResponse(Call<List<DesarrolloActividades>> call, Response<List<DesarrolloActividades>> response) {


                List<DesarrolloActividades> lista = response.body();
                if (response.isSuccessful()){
                    Log.e("descargando","desarrolloactividades response successfull");
                    if (lista.size() > 0){
                        Log.e("descargando","desarrolloactividades lista size mayor a cero");
                        for (DesarrolloActividades desa: lista){
                            try {
                                desa.setUploaded(true);
                                desa.setPersona(persona);
                                desa.setArbol(arbol);
                                Actividad actividad = daoActividad.queryForId(desa.getActividades_id());
                                desa.setIdActividad(actividad);
                                daoDesarrolloAct.create(desa);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        cerrarDialogo.cerrardialogo();
                    }else {Log.e("descargando","desarrolloactividades lista esta vacia");}
                }else {Log.e("descargando","desarrolloactividades response WASNT SUCCESSFULL");}
            }

            @Override
            public void onFailure(Call<List<DesarrolloActividades>> call, Throwable t) {

                cerrarDialogo.cerrardialogo();
                Log.e("peticion failed"," DESARROLLO ACTIVIDADES PERSONAS");
                Toast.makeText(context, "No se pudo sincronizar sus datos, revise su conexion" +
                        " a Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
