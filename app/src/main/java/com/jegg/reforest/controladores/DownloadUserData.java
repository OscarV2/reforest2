package com.jegg.reforest.controladores;

import android.content.Context;
import android.widget.Toast;

import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Utils.SyncServiceUtils;
import com.jegg.reforest.Utils.UpdateDb;
import com.jegg.reforest.api.ReforestApiAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadUserData extends SyncServiceUtils {

    private int idPersona;
    private Context context;
    private UpdateDb updateDb;

    public DownloadUserData(int idPersona, Context context) {
        super(context);
        this.idPersona = idPersona;
        this.context = context;
        updateDb = new UpdateDb(context);
    }

    public void descargar(){
        descargarLote();
        descargarDesaActividades();
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
                                daoLotes.create(lote);

                                //descargar arboles
                                descargarArboles(lote.getId());

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Lote>> call, Throwable t) {

                Toast.makeText(context, "No se pudo sincronizar sus datos, revise su conexion" +
                        " a Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void descargarArboles(String idLote){

        Call<List<Arbol>> getArboles = ReforestApiAdapter.getApiService()
                .getArboles(idLote);

        getArboles.enqueue(new Callback<List<Arbol>>() {
            @Override
            public void onResponse(Call<List<Arbol>> call, Response<List<Arbol>> response) {
                List<Arbol> lista = response.body();
                if (response.isSuccessful() && lista != null){
                    if (lista.size() > 0){

                        for (Arbol arbol: lista){
                            try {
                                arbol.setUploaded(true);
                                daoArboles.create(arbol);
                                descargarAlturas(arbol.getId());
                                descargarArbolEstado(arbol.getId());
                                descargarArbolEspecie(arbol.getId());

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Arbol>> call, Throwable t) {

            }
        });
    }

    private void descargarAlturas(String idArbol){

        Call<List<Altura>> getAlturas = ReforestApiAdapter.getApiService()
                .getAlturas(idArbol);

        getAlturas.enqueue(new Callback<List<Altura>>() {
            @Override
            public void onResponse(Call<List<Altura>> call, Response<List<Altura>> response) {
                List<Altura> lista = response.body();
                if (response.isSuccessful()){
                    if (lista.size() > 0){

                        for (Altura altura: lista){
                            try {
                                altura.setUploaded(true);
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

            }
        });
    }

    private void descargarArbolEstado(String idArbol){

        Call<List<ArbolEstado>> getEstadosArboles = ReforestApiAdapter.getApiService()
                .getArbolEstado(idArbol);

        getEstadosArboles.enqueue(new Callback<List<ArbolEstado>>() {
            @Override
            public void onResponse(Call<List<ArbolEstado>> call, Response<List<ArbolEstado>> response) {

                List<ArbolEstado> lista = response.body();
                if (response.isSuccessful() && lista != null){
                    if (lista.size() > 0){

                        for (ArbolEstado arbolEstado: lista){
                            try {
                                arbolEstado.setUploaded(true);
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

            }
        });
    }

    private void descargarArbolEspecie(String idArbol){

        Call<List<ArbolEspecie>> getArbolEspecies = ReforestApiAdapter.getApiService()
                .getArbolEspecies(idArbol);

        getArbolEspecies.enqueue(new Callback<List<ArbolEspecie>>() {
            @Override
            public void onResponse(Call<List<ArbolEspecie>> call, Response<List<ArbolEspecie>> response) {

                List<ArbolEspecie> lista = response.body();
                if (response.isSuccessful() && lista != null){
                    if (lista.size() > 0){

                        for (ArbolEspecie arbolEspecie: lista){
                            try {
                                arbolEspecie.setUploaded(true);
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

            }
        });

    }

    private void descargarDesaActividades(){

        Call<List<DesarrolloActividades>> getDesaAct = ReforestApiAdapter.getApiService()
                .getDesaActPersona(idPersona);

        getDesaAct.enqueue(new Callback<List<DesarrolloActividades>>() {
            @Override
            public void onResponse(Call<List<DesarrolloActividades>> call, Response<List<DesarrolloActividades>> response) {

                List<DesarrolloActividades> lista = response.body();
                if (response.isSuccessful()){
                    if (lista.size() > 0){

                        for (DesarrolloActividades desa: lista){
                            try {
                                desa.setUploaded(true);
                                daoDesarrolloAct.create(desa);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DesarrolloActividades>> call, Throwable t) {

                Toast.makeText(context, "No se pudo sincronizar sus datos, revise su conexion" +
                        " a Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
