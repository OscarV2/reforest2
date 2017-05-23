package com.jegg.reforest.api;

import com.jegg.reforest.Utils.Constantes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oscarvc on 18/05/17.
 */

public class ReforestApiAdapter {

    private static ReforestApiServices API_SERVICE;

    public static ReforestApiServices getApiService() {

        if (API_SERVICE == null){

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constantes.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API_SERVICE = retrofit.create(ReforestApiServices.class);
        }
        return API_SERVICE;
    }
}
