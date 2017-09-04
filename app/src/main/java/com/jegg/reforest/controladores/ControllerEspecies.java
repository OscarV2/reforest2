package com.jegg.reforest.controladores;

import android.content.Context;
import com.jegg.reforest.R;

import java.util.Arrays;

public class ControllerEspecies {

    private String[] listaEspecies;

    public ControllerEspecies(Context context) {

        listaEspecies =  context.getResources().getStringArray(R.array.lista_especies);
    }

    public boolean checkEspecie(String especie){

        return Arrays.asList(listaEspecies).contains(especie);
    }

    public String[] getListaEspecies() {
        return listaEspecies;
    }
}
