package com.jegg.reforest.Actividades;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Actividad;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.R;
import com.jegg.reforest.Utils.LocationUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Detalles extends AppCompatActivity implements AdapterView.OnItemSelectedListener
        {

    EditText edtEspecie, edtFecha, edtComentarios, edtAltura;
    ImageView fotoArbol;
    TextView txtCoordenadas, txtLote;
    Spinner actividades, saludArbol;

    private basededatos datosReforest;

    int idLote, idActividad = 1, idEstado;
    String nombreLote;
    String comentariosActividad;
    String altura;
    String estado;
    String especie;
    File filePath;

    LinearLayout lay_edt_especie, lay_edt_altura, laySpinnerSaludArbol;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    double latitud, longitud;

    SimpleDateFormat sdf;
    String currentDateandTime;
    String fotoPath;

    java.sql.Date fechaActividad;
    Lote lote;
    Actividad actividad;
    Estado estadoEntidad;
    Especie especieEntidad;
    Arbol arbol;

    Location location;
    LocationUtils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        datosReforest = OpenHelperManager.getHelper(Detalles.this, basededatos.class);

        idLote = this.getIntent().getIntExtra("id_lote", 0);
        Log.e("idLote", String.valueOf(idLote));

        try {
            Dao daoLotes = datosReforest.getLoteDao();
            lote = (Lote)daoLotes.queryForId(idLote);
            nombreLote = lote.getNombre();
        } catch (SQLException e) {
            e.printStackTrace();
        }




        lay_edt_altura = (LinearLayout)findViewById(R.id.lay_edt_altura);
        lay_edt_especie = (LinearLayout)findViewById(R.id.lay_edt_especie);
        laySpinnerSaludArbol = (LinearLayout)findViewById(R.id.linear_spiner_salud_arbol);

        edtComentarios = (EditText)findViewById(R.id.comentario_detalles);
        edtEspecie = (EditText)findViewById(R.id.especieArbol);
        edtFecha = (EditText)findViewById(R.id.FechaActividadArbol);
        edtAltura = (EditText)findViewById(R.id.alturaArbol);

        txtCoordenadas = (TextView) findViewById(R.id.CoordenadasArbol);
        fotoArbol = (ImageView) findViewById(R.id.foto_arbol);


        actividades = (Spinner)findViewById(R.id.spinner_detalle_actividad);
        saludArbol = (Spinner)findViewById(R.id.spinner_salud_arbol);

        txtLote = (TextView)findViewById(R.id.nombre_lote);
        txtLote.append(nombreLote);

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        currentDateandTime = sdf.format(new Date());
        edtFecha.setText(currentDateandTime);

        filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lista_tipo_actividades, R.layout.item_spinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actividades.setAdapter(adapter);
        actividades.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterSaludArbol = ArrayAdapter.createFromResource(this,
                R.array.lista_estados, R.layout.item_spinner);

        adapterSaludArbol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        saludArbol.setAdapter(adapterSaludArbol);
        saludArbol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        utils = new LocationUtils(getApplicationContext());

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

        Log.e("requestCode", String.valueOf(requestCode));
        Log.e("resultCode", String.valueOf(resultCode));
        if (requestCode == 2){
            if (resultCode == RESULT_OK){

                latitud = data.getDoubleExtra("latidud_arbol", 0);
                longitud = data.getDoubleExtra("longitud_arbol", 0);
                txtCoordenadas.append("\n" + "Latitud: "+ String.format("%.3f", latitud) +
                        "\n" + "Longitud: "+ String.format("%.3f", longitud));

                Log.e("Latitud:", String.valueOf(latitud));
                Log.e("Longitud:", String.valueOf(latitud));

            }
        }else if(requestCode == REQUEST_IMAGE_CAPTURE){
            Log.e("llego a if","foto");

            Uri selectedImage = data.getData();
            Log.e("Uri", selectedImage.toString());

            Bundle extras = data.getExtras();

            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fotoArbol.setBackground(null);
            }
            fotoArbol.setImageBitmap(imageBitmap);

            Log.e("path", getPath(selectedImage));

        }

    }

    public void irMapa(View v){

        location = utils.getLocation();
        if (location != null){
            latitud = location.getLatitude();
            longitud = location.getLongitude();
            txtCoordenadas.append("\n" + "Latitud: "+ String.format("%.3f", latitud) +
                    "\n" + "Longitud: "+ String.format("%.3f", longitud));

        }else {
            Toast.makeText(Detalles.this, "Locatiom null", Toast.LENGTH_SHORT).show();
        }

    }
    public void tomarFoto(View v) throws IOException {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

    }

    public  void guardarActividad(View v) throws ParseException {

        comentariosActividad = edtComentarios.getText().toString();
        validarComentarios();
        validarDatos();
        volverALotes();
    }

    private void volverALotes() {

        Intent i = new Intent(Detalles.this, Lotes.class);
        startActivity(i);
        finish();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position){
            case 0:
                idActividad = 1;
                esconderVistas(false);
                 break;
            case 1:
                idActividad = 2;
                esconderVistas(true);
                //Abonar
                break;
            case 2:
                idActividad = 3;
                //Fertilizacion
                esconderVistas(true);
                break;
            case 3:
                idActividad = 4;
                //Control de Malezas
                esconderVistas(true);
                break;

            case 4:
                idActividad = 5;
                //Sustitucion de plantas
                esconderVistas(false);
                break;
            case 5:
                idActividad = 6;
                //Preparar terreno
                esconderVistas(true);
                break;
            case 6:
                idActividad = 7;
                //Enfermedades
                lay_edt_altura.setVisibility(View.GONE);
                lay_edt_especie.setVisibility(View.VISIBLE);
                break;
            case 7:
                idActividad = 8;
                //Estado del arbol
                laySpinnerSaludArbol.setVisibility(View.VISIBLE);
                esconderVistas(true);
                break;
        }
    }

    private void esconderVistas(boolean b) {

        if(b){
            lay_edt_altura.setVisibility(View.GONE);
            lay_edt_especie.setVisibility(View.GONE);
        }else{
            lay_edt_altura.setVisibility(View.VISIBLE);
            lay_edt_especie.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void validarDatos() {

        boolean b;
        if (idActividad == 2 || idActividad == 3 || idActividad == 4 || idActividad == 6) {

            //validar foto y coordenadas


        } else if (idActividad == 1 || idActividad == 5) {

            especie = edtEspecie.getText().toString();
            altura = edtAltura.getText().toString();

            if (especie.equals("")) {

                edtEspecie.requestFocus();
            } else if (altura.equals("")) {

                edtAltura.requestFocus();
            }

            if (idActividad == 1){          //Actividad sembrar

                crearArbol();

            }

        }
    }

    private void crearArbol() {

        try {
            Date parsed = sdf.parse(currentDateandTime);
            fechaActividad = new java.sql.Date(parsed.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String coordenadas = String.valueOf(location.getLatitude()) + String.valueOf(location.getLongitude());

        arbol = new Arbol(coordenadas, fechaActividad, lote);
        Dao daoArboles;
        try {
            daoArboles = datosReforest.getArbolDao();
            daoArboles.create(arbol);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void validarComentarios() {
        if (comentariosActividad.equals("")){
            Toast.makeText(Detalles.this, "Llenar campos faltantes.", Toast.LENGTH_SHORT).show();
            edtComentarios.requestFocus();
        }
    }


    private String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null,null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    @Override
    protected void onDestroy() {
        OpenHelperManager.releaseHelper();
        super.onDestroy();
    }
}
