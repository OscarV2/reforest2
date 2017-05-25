package com.jegg.reforest.actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Municipio;
import com.jegg.reforest.R;
import com.jegg.reforest.Utils.Constantes;
import com.jegg.reforest.Utils.LocationUtils;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrearLote extends AppCompatActivity {

    private TextInputEditText nombre, fecha, area, edtDepartamento, edtMunicipio;
    private TextView punto_referencia;
    private Button addPunto;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private String delimitacion;
    private StringBuffer delimitacionBuffer;
    private Double areaLote;
    private basededatos datosReforest;
    SimpleDateFormat sdf;

    private int contPuntosLote = 1;
    List<LatLng> recLote = new ArrayList<>();
    private Location location;

    Municipio municipio;

    double lat, lng;
    LocationUtils utils;

    private void init(){

        nombre = (TextInputEditText) findViewById(R.id.nombre_crear_lote);
        fecha  = (TextInputEditText) findViewById(R.id.fecha_crear_lote);
        area = (TextInputEditText) findViewById(R.id.area_crear_lote);
        edtDepartamento = (TextInputEditText)findViewById(R.id.departamento_crear_lote);
        edtMunicipio = (TextInputEditText)findViewById(R.id.municipio_crear_lote);

        punto_referencia = (TextView ) findViewById(R.id.PuntoReferencialote);
        addPunto = (Button) findViewById(R.id.AgregarPuntoLocalizacionLote);

        edtDepartamento.setText("Cesar");
        edtMunicipio.setText("Valledupar");

        addPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irMapa();
            }
        });

        sdf = new SimpleDateFormat("dd/MM/yyyy");

        String currentDateandTime = Constantes.sdf.format(new Date());
        fecha.setText(currentDateandTime);

        datosReforest = OpenHelperManager.getHelper(CrearLote.this,
                basededatos.class);

        utils = new LocationUtils(getApplicationContext());
        nombre.requestFocus();

        if (!(utils.gpsEnabled())){
            mostrarDialogoGps();
        }

    }

    private void irMapa() {

        if (contPuntosLote < 9){

            location = utils.getLocation();
            if (location != null){

                lat = location.getLatitude();
                lng = location.getLongitude();
                recLote.add(new LatLng(lat,lng));

                punto_referencia.append("Punto " +
                        String.valueOf(contPuntosLote) +   ": " +
                        "\n" +
                        "Latitud: " + String.valueOf(lat) +
                        "\n   Longitud: " + String.valueOf(lng) + "\n");
                contPuntosLote++;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_lote);
        setToolbar();
        init();
        delimitacionBuffer = new StringBuffer();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onClickBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            onClickBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onClickBack(){
        startActivity(new Intent(this,Lotes.class));
        finish();
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_crear_lote);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back));
        }
    }

    public void eliminarPunto(View v){

        Log.e("rec size antes", String.valueOf(recLote.size()));
        //borrar ultimas dos lineas del textview

        if (!(recLote.size() == 0)){

            String contenidoTxtPuntos = punto_referencia.getText().toString();

            int lastNewLineAt = contenidoTxtPuntos.lastIndexOf("P");
            punto_referencia.setText(contenidoTxtPuntos.substring(0, lastNewLineAt));

            //borrar ultima coordenada de recLote
            recLote.remove(recLote.size() - 1);

            Toast.makeText(this, "puntos borrados", Toast.LENGTH_SHORT).show();

            contPuntosLote--;
            Log.e("rec size despues", String.valueOf(recLote.size()));

        }else {
            Toast.makeText(CrearLote.this, "Escoger coordenadas.", Toast.LENGTH_SHORT).show();
        }

    }

    private void setDelimitacionBuffer(){
        for (int i = 0; i < recLote.size(); i++){

            lat = recLote.get(i).latitude;
            lng = recLote.get(i).longitude;
            delimitacionBuffer.append(String.valueOf(lat));
            delimitacionBuffer.append(" ");
            delimitacionBuffer.append(String.valueOf(lng));
            delimitacionBuffer.append(",");
        }
    }

    public void calcularArea(View v){
        if (recLote.size() < 4){
            Toast.makeText(CrearLote.this, "Minimo 4 puntos de referencia.", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "en m2 "+String.valueOf(areaLote), Toast.LENGTH_SHORT).show();
            areaLote = (SphericalUtil.computeArea(recLote))/10000;
            area.setText(String.valueOf(areaLote));
        }

    }

    public void guardarLote(View view) throws ParseException {

        String nombreLote = nombre.getText().toString();
        areaLote = Double.parseDouble(area.getText().toString());

        if (nombreLote.equals("")){
            Toast.makeText(CrearLote.this, "Por favor llene los campos faltantes.", Toast.LENGTH_SHORT).show();
            nombre.requestFocus();
        }else if (areaLote == 0){

            Toast.makeText(CrearLote.this, "El area no es valida, intentelo nuevamente.", Toast.LENGTH_SHORT).show();

        }else if(recLote.size() < 4){

            Toast.makeText(CrearLote.this, "Escoger coordenadas.", Toast.LENGTH_SHORT).show();
        }

        else{
            setDelimitacionBuffer();
            delimitacion = delimitacionBuffer.toString();
            delimitacion = delimitacion.substring(0, delimitacion.length()-1);
            Log.e("delimi", delimitacion);
            String fechaLote = Constantes.sdf.format(new Date());
            municipio = new Municipio(edtMunicipio.getText().toString());
            Lote lote = new Lote(nombreLote, fechaLote, areaLote, municipio, delimitacion);

            try {
                Dao<Lote, String> lotesDao = datosReforest.getLoteDao();
                lotesDao.create(lote);

                startActivity(new Intent(CrearLote.this, Lotes.class));
                finish();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    private void mostrarDialogoGps() {

        AlertDialog.Builder volver = new AlertDialog.Builder(CrearLote.this);
        volver.setTitle("Verificar Servicio de Localizacion.")
                .setMessage("Por favor habilite la ubicacion de su dispositivo.")
                .setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(CrearLote.this, Lotes.class);
                        startActivity(i);
                        finish();

                    }
                })
                .create();

        volver.show();
    }
}