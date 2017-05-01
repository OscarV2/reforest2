package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jegg.reforest.MapArbol;
import com.jegg.reforest.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CrearArbol extends AppCompatActivity {

    EditText edtEspecie, edtFecha, edtComentarios;
    ImageView fotoArbol;
    TextView txtCoordenadas;

    double latitud, longitud;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_arbol);

        edtComentarios = (EditText)findViewById(R.id.edt_comentario_arbol);
        edtEspecie = (EditText)findViewById(R.id.edt_especie_arbol);
        edtFecha = (EditText)findViewById(R.id.fecha_crear_arbol);

        txtCoordenadas = (TextView) findViewById(R.id.txt_coordenadas_arbol);
        fotoArbol = (ImageView) findViewById(R.id.foto_arbol);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());
        edtFecha.setText(currentDateandTime);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1){
            if (resultCode == RESULT_OK){

                latitud = data.getDoubleExtra("latitud_arbol", 0);
                longitud = data.getDoubleExtra("longitud_arbol", 0);
                txtCoordenadas.append("\n" + "Latitud: "+ String.format("%.3f", latitud) +
                        "\n" + "Longitud: "+ String.format("%.3f", longitud));

            }
        }
    }

    public void irMapa(View v){
        startActivityForResult(new Intent(CrearArbol.this, MapArbol.class) , 1);
        Toast.makeText(CrearArbol.this, "Mapa", Toast.LENGTH_SHORT).show();

    }
    public void tomarFoto(View v){
        Toast.makeText(CrearArbol.this, "Foto", Toast.LENGTH_SHORT).show();

    }
    public void guardarArbol(View v){
        Toast.makeText(CrearArbol.this, "Guardara", Toast.LENGTH_SHORT).show();

    }
}
