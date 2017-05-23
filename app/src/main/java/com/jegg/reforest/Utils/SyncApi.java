package com.jegg.reforest.Utils;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SyncApi {

    private boolean OpExitosa = true;
    private basededatos datosReforest;

    SyncApi(basededatos datosReforest) {
        this.datosReforest = datosReforest;
    }

    void sincronizarLotes(List<Lote> listaLotes) {


        //sinc lotes
        for (int i = (listaLotes.size() - 1); i>=0; i=i-1){

            Lote lote = listaLotes.get(i);

            Call<String> subirLotes = ReforestApiAdapter.getApiService().postLotes(lote);
            subirLotes.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    Log.e("postLotes toString",response.toString());
                    if (response.isSuccessful()){
                        //                      Log.e("resultado postLotes",response.body());
                    }else {
//                        Log.e("postLotes noSuccess",response.body());


                        OpExitosa = false;
                        Log.e("postLotes toString",response.toString());
                        //                    Log.e("resultado postLotes",response.body());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    OpExitosa = false;

                }
            });

            if (!OpExitosa){
                break;
            }
        }

    }

    void sincronizarArboles(List<Arbol> listaArbol) {

        final boolean[] exito = {true};

        //sinc arboles
        for (int i = (listaArbol.size()-1); i>=0; i = i-1){

            Arbol arbol = listaArbol.get(i);
            Call<String> subirArbol = ReforestApiAdapter.getApiService().postArbol(arbol);
            subirArbol.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (!response.isSuccessful()){
                        exito[0] = false;
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });

            if (!exito[0]){
                break;
            }

        }
    }

    void sincronizarEspecie(List<Especie> listaEspecie) {

        if (listaEspecie.size() > 0){

            final boolean[] exito = {true};
            //sinc Especie
            for (int i = listaEspecie.size()-1; i==0; i--) {

                Especie especie = listaEspecie.get(i);
                Call<String> subirEspecie = ReforestApiAdapter.getApiService().postEspecie(especie);
                subirEspecie.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (!response.isSuccessful()){
                            exito[0] = false;
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

                if (!exito[0]){
                    break;
                }

            }
        }else{Log.e("esta vacia", "Especie");}
    }

    void sincronizarArbolEstado(List<ArbolEstado> listaArbolEstado) {

        if (listaArbolEstado.size() > 0){

            final boolean[] exito = {true};
            //sinc Estado
            for (int i = (listaArbolEstado.size() - 1); i>=0; i=i-1){
                //Log.e("sinc Especie arbol","dentro del for");

                ArbolEstado arbolEstado = listaArbolEstado.get(i);
                Call<String> subirEspecie = ReforestApiAdapter.getApiService().postEstadoArbol(arbolEstado);
                subirEspecie.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (!response.isSuccessful()){
                            exito[0] = false;
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

                if (!exito[0]){
                    break;
                }
            }
        }else{Log.e("esta vacia", "arbolEstado");}

    }

    void sincronizarArbolEspecie(List<ArbolEspecie> listaArbolEspecie) {

        if (listaArbolEspecie.size() > 0){

            final boolean[] exito = {true};
            //sinc ArbolEspecie
            for (int i = (listaArbolEspecie.size() - 1); i>=0; i=i-1){

                ArbolEspecie arbolEspecie = listaArbolEspecie.get(i);
                Call<String> subirArbolEspecie = ReforestApiAdapter.getApiService().postEspecieArbol(arbolEspecie);
                subirArbolEspecie.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (!response.isSuccessful()){
                            exito[0] = false;
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

                if (!exito[0]){
                    break;
                }
            }
        }else{Log.e("esta vacia", "ArbolEspecie");}
    }

    void sincronizarAlturas(List<Altura> listaAltura) {

        if (listaAltura.size() > 0){

            final boolean[] exito = {true};
            //sinc Alturas
            for (int i = (listaAltura.size() - 1); i>=0; i=i-1){
                //Log.e("sincronizando alturas","dentro del for");

                Altura altura = listaAltura.get(i);
                Call<String> subirAltura = ReforestApiAdapter.getApiService().postAltura(altura);
                subirAltura.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (!response.isSuccessful()){
                            exito[0] = false;
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });

                if (!exito[0]){
                    break;
                }
            }
        }else{Log.e("esta vacia", "Alturas");}
    }

    void sincronizarDesarrolloAct(List<DesarrolloActividades> listaDesarrolloAct) {


        final boolean[] exito = {true};
        //sinc lotes
        for (int i = (listaDesarrolloAct.size() - 1); i>=0; i=i-1){
            DesarrolloActividades dsa = listaDesarrolloAct.get(i);
            //Log.e("desaAct", dsa.toString());
            /*Log.e("comentario", dsa.getComentario());
            Log.e("fecha", Constantes.sdf.format(dsa.getFecha()));
            Log.e("idActividad", String.valueOf(dsa.getIdActividad().getId()));
            Log.e("arbol_id", String.valueOf(dsa.getArbol().getId()) + Constantes.SERIAL);
            Log.e("personas_id", String.valueOf(dsa.getPersona().getId()));
            */
            Log.e("url", dsa.getUrlFoto());
            //Log.e("tamaño cadena", String.valueOf(dsa.getUrlFoto().length()));

            Call<String> subirDsaAct = ReforestApiAdapter.getApiService().postDesarrolloAct(dsa.getUrlFoto(),
                    dsa.getComentario(), dsa.getFecha(), dsa.getIdActividad().getId(),
                    dsa.getArbol().getId(), dsa.getPersona().getId());
            subirDsaAct.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (!response.isSuccessful()){
                        exito[0] = false;
                        Log.e("response", "no es successfull");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    Log.e("postDesa", "OnFailure");
                }
            });

            if (!exito[0]){
                Log.e("BREAK FOR", "Desarrollo Actividades");
                break;

            }
        }
    }
}
