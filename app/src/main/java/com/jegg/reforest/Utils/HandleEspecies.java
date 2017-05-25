package com.jegg.reforest.Utils;

import android.content.Context;
import com.jegg.reforest.R;

import java.util.Arrays;

/**
 * Created by oscarvc on 23/05/17.
 */
public class HandleEspecies {

    private String[] listaEspecies;

    public HandleEspecies(Context context) {

        listaEspecies =  context.getResources().getStringArray(R.array.lista_especies);
    }

    public boolean checkEspecie(String especie){

        return Arrays.asList(listaEspecies).contains(especie);
    }

    public String[] getListaEspecies() {
        return listaEspecies;
    }
}
