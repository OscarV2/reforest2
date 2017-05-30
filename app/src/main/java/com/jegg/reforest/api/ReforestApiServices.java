package com.jegg.reforest.api;

import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.Utils.Constantes;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by oscarvc on 18/05/17.
 * Interfaz con peticiones http a appreforest.com
 */

public interface ReforestApiServices {

    @GET(Constantes.GET_PERSONAS)
    Call<List<Persona>> getPersonas();

    @POST(Constantes.POST_ALTURA)
    Call<ResponseBody> postAltura(@Body Altura altura);

    @POST(Constantes.POST_ARBOL)
    Call<ResponseBody> postArbol(@Body Arbol arbol);

    /*@FormUrlEncoded
    @POST(Constantes.POST_ARBOL)
    Call<String> postArbol(@Field("id") String id, @Field("coodenadas") String coodenadas,
                           @Field("fecha_sembrado") String fecha, @Field("lote_id") String lote);
*/

    @POST(Constantes.POST_DESARROLLO_ACTIVIDAD)
    Call<ResponseBody> postDesarrolloAct(@Body DesarrolloActividades dsa);

    @POST(Constantes.POST_ESPECIE_ARBOL)
    Call<ResponseBody> postEspecieArbol(@Body ArbolEspecie arbolEspecie);


    @POST(Constantes.POST_ESTADO_ARBOL)
    Call<ResponseBody> postEstadoArbol(@Body ArbolEstado arbolEstado);

    @POST("lote")
    Call<ResponseBody> postLotes(@Body Lote lote);

}
