package com.jegg.reforest.api;

import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.Utils.Constantes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by oscarvc on 18/05/17.
 */

public interface ReforestApiServices {

    @GET(Constantes.GET_PERSONAS)
    Call<List<Persona>> getPersonas();

    @POST(Constantes.POST_ALTURA)
    Call<String> postAltura(@Body Altura altura);

    @POST(Constantes.POST_ARBOL)
    Call<String> postArbol(@Body Arbol arbol);

    /*@FormUrlEncoded
    @POST(Constantes.POST_ARBOL)
    Call<String> postArbol(@Field("id") String id, @Field("coodenadas") String coodenadas,
                           @Field("fecha_sembrado") String fecha, @Field("lote_id") String lote);
*/
    @FormUrlEncoded
    @POST(Constantes.POST_DESARROLLO_ACTIVIDAD)
    Call<String> postDesarrolloAct(@Field("urlimagen") String imagen, @Field("comentario") String comentario,
                                   @Field("fecha") String fecha, @Field("actividades_id") int ActId,
                                   @Field("arbol_id") String ArbolId, @Field("personas_id") int persona);

    @POST(Constantes.POST_ESPECIE)
    Call<String> postEspecie(@Body Especie especie);


    @POST(Constantes.POST_ESPECIE_ARBOL)
    Call<String> postEspecieArbol(@Body ArbolEspecie arbolEspecie);


    @POST(Constantes.POST_ESTADO_ARBOL)
    Call<String> postEstadoArbol(@Body ArbolEstado arbolEstado);


    @POST("lote")
    Call<String> postLotes(@Body Lote lote);

}
