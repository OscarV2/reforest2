package com.jegg.reforest.Actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.jegg.reforest.R;

public class CrearArbol extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_arbol);
    }

    public void irMapa(View v){
        Toast.makeText(CrearArbol.this, "Mapa", Toast.LENGTH_SHORT).show();

    }
    public void tomarFoto(View v){
        Toast.makeText(CrearArbol.this, "Foto", Toast.LENGTH_SHORT).show();

    }
    public void guardarArbol(View v){
        Toast.makeText(CrearArbol.this, "Guardara", Toast.LENGTH_SHORT).show();

    }
}
