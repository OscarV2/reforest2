package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jegg.reforest.MapArbol;
import com.jegg.reforest.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Detalles extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText edtEspecie, edtFecha, edtComentarios, edtAltura;
    ImageView fotoArbol;
    TextView txtCoordenadas, txtLote;
    Spinner actividades;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    double latitud, longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        edtComentarios = (EditText)findViewById(R.id.comentario_detalles);
        edtEspecie = (EditText)findViewById(R.id.especieArbol);
        edtFecha = (EditText)findViewById(R.id.FechaActividadArbol);
        edtAltura = (EditText)findViewById(R.id.alturaArbol);

        txtCoordenadas = (TextView) findViewById(R.id.CoordenadasArbol);
        fotoArbol = (ImageView) findViewById(R.id.foto_arbol);

        actividades = (Spinner)findViewById(R.id.spinner_detalle_actividad);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());
        edtFecha.setText(currentDateandTime);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lista_tipo_actividades, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actividades.setAdapter(adapter);
        actividades.setOnItemSelectedListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//esto es para la navegacion hacia atras
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(Detalles.this, Lotes.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2){
            if (resultCode == RESULT_OK){

                latitud = data.getDoubleExtra("latitud_arbol", 0);
                longitud = data.getDoubleExtra("longitud_arbol", 0);
                txtCoordenadas.append("\n" + "Latitud: "+ String.format("%.3f", latitud) +
                        "\n" + "Longitud: "+ String.format("%.3f", longitud));

            }
        }else if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fotoArbol.setBackground(null);
            }
            fotoArbol.setImageBitmap(imageBitmap);
        }

    }


    public void irMapa(View v){
        startActivityForResult(new Intent(Detalles.this, MapArbol.class) , 2);
        Toast.makeText(Detalles.this, "Mapa", Toast.LENGTH_SHORT).show();

    }
    public void tomarFoto(View v){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }
    public void guardarArbol(View v){

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){

            case 0:
                edtAltura.setVisibility(View.VISIBLE);
                edtEspecie.setVisibility(View.VISIBLE);
                Toast.makeText(Detalles.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            case 1:
                //Abonar
                edtAltura.setVisibility(View.GONE);
                edtEspecie.setVisibility(View.GONE);
                Toast.makeText(Detalles.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            case 2:
                //Fertilizacion
                edtAltura.setVisibility(View.GONE);
                edtEspecie.setVisibility(View.GONE);

                Toast.makeText(Detalles.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            case 3:
                //Control de Malezas
                edtAltura.setVisibility(View.GONE);
                edtEspecie.setVisibility(View.GONE);
                Toast.makeText(Detalles.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

            case 4:
                //Sustitucion de plantas
                edtAltura.setVisibility(View.VISIBLE);
                edtEspecie.setVisibility(View.VISIBLE);
                Toast.makeText(Detalles.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            case 5:
                //Preparar terreno
                edtAltura.setVisibility(View.GONE);
                edtEspecie.setVisibility(View.GONE);
                Toast.makeText(Detalles.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            case 6:
                //Enfermedades
                edtAltura.setVisibility(View.GONE);
                edtEspecie.setVisibility(View.GONE);
                Toast.makeText(Detalles.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            case 7:
                //Estado del arbol
                Toast.makeText(Detalles.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;

        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
