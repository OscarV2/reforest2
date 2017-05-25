package com.jegg.reforest.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.api.ReforestApiAdapter;

import java.sql.SQLException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SyncApi {


    private UpdateDb updateDb;

    SyncApi(Context context, basededatos datosReforest) {
        basededatos datosReforest1 = datosReforest;
        this.updateDb = new UpdateDb(context, datosReforest);
    }

    void sincronizarLotes(List<Lote> listaLotes) {

        //sinc lotes
        for (final Lote lote : listaLotes){

            Call<ResponseBody> subirLotes = ReforestApiAdapter.getApiService().postLotes(lote);
            subirLotes.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    Log.e("postLotes toString",response.toString());
                    if (response.isSuccessful()){

                        Log.e("subirLote","Sucessful");
                        try {
                            updateDb.updateLote(lote);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        //                      Log.e("resultado postLotes",response.body());
                    }else {
//                        Log.e("postLotes noSuccess",response.body());

                        Log.e("postLotes toString",response.toString());
                        //                    Log.e("resultado postLotes",response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {


                    Log.e("FalloLote", call.toString());
                    Log.e("subirLote","Fallo");
                }
            });
        }

    }

    void sincronizarArboles(List<Arbol> listaArbol) {

        //sinc arboles
        for (final Arbol arbol : listaArbol){

            Call<ResponseBody> subirArbol = ReforestApiAdapter.getApiService().postArbol(arbol);
            subirArbol.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()){

                        Log.e("subirArbol","Sucessful");
                        try {
                            updateDb.updateAbol(arbol);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    Log.e("subirArbol","Fallo");
                }
            });
        }
    }

    void sincronizarEspecie(List<Especie> listaEspecie) {

        if (listaEspecie.size() > 0){

            //sinc Especie
            for (int i = listaEspecie.size()-1; i==0; i--) {

                Especie especie = listaEspecie.get(i);
                Call<ResponseBody> subirEspecie = ReforestApiAdapter.getApiService().postEspecie(especie);
                subirEspecie.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()){
                            Log.e("subirEspecie","Sucessful");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Log.e("subirArbolEspecie","Fallo");
                    }
                });
            }
        }else{Log.e("esta vacia", "Especie");}
    }

    void sincronizarArbolEstado(List<ArbolEstado> listaArbolEstado) {

        if (listaArbolEstado.size() > 0){

            //sinc Estado
            for (final ArbolEstado arbolEstado : listaArbolEstado){
                //Log.e("sinc Especie arbol","dentro del for");

                Call<ResponseBody> subirEspecie = ReforestApiAdapter.getApiService().postEstadoArbol(arbolEstado);
                subirEspecie.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()){
                            Log.e("subirArbolEstado","Sucessful");
                            try {
                                updateDb.updateArbolEstado(arbolEstado);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Log.e("subirArbolEstado","Fallo");
                    }
                });
            }
        }else{Log.e("esta vacia", "arbolEstado");}

    }

    void sincronizarArbolEspecie(List<ArbolEspecie> listaArbolEspecie) {

            //sinc ArbolEspecie
            for (final ArbolEspecie arbolEspecie : listaArbolEspecie){

                Call<ResponseBody> subirArbolEspecie = ReforestApiAdapter.getApiService().postEspecieArbol(arbolEspecie);
                subirArbolEspecie.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()){
                            Log.e("subirArbolEspecie","Sucessful");
                            try {
                                updateDb.update(arbolEspecie);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Log.e("subirArbolEspecie","Fallo");
                    }
                });
            }
    }

    void sincronizarAlturas(List<Altura> listaAltura) {

            //sinc Alturas
            for (final Altura altura : listaAltura){
                //Log.e("sincronizando alturas","dentro del for");

                Call<ResponseBody> subirAltura = ReforestApiAdapter.getApiService().postAltura(altura);
                subirAltura.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if (response.isSuccessful()){
                            Log.e("subirAltura","Sucessful");
                            try {
                                updateDb.updateAltura(altura);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Log.e("subirAltura","Fallo");
                    }
                });


            }
    }

    void sincronizarDesarrolloAct(List<DesarrolloActividades> listaDesarrolloAct) {

        //sinc lotes
        for (final DesarrolloActividades dsa : listaDesarrolloAct){

            Call<ResponseBody> subirDsaAct = ReforestApiAdapter.getApiService().postDesarrolloAct(dsa.getUrlFoto(),
                    dsa.getComentario(), dsa.getFecha(), dsa.getIdActividad().getId(),
                    dsa.getArbol().getId(), dsa.getPersona().getId());
            subirDsaAct.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    if (response.isSuccessful()){
                        try {
                            updateDb.updateDesaAct(dsa);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        Log.e("subirDesarrollo", "es successfull");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    Log.e("DesaResponse", call.toString());
                    Log.e("subirDesa","Fallo");
                }
            });

        }
    }
}
