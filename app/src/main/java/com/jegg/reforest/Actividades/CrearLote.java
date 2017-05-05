package com.jegg.reforest.Actividades;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jegg.reforest.Actividades.Menu;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Municipio;
import com.jegg.reforest.R;
import com.jegg.reforest.Utils.LocationUtils;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrearLote extends AppCompatActivity {

    private EditText nombre,fecha,area, edtDepartamento, edtMunicipio;
    private TextView punto_referencia;
    private Button addPunto,removePunto;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private String delimitacion;
    private StringBuffer delimitacionBuffer;
    private Double areaLote;
    private basededatos datosReforest;
    SimpleDateFormat sdf;

    List<LatLng> recLote = new ArrayList<>();
    private Location location;

    Municipio municipio;

    double lat, lng;
    LocationUtils utils;

    private void init(){

        nombre = (EditText) findViewById(R.id.nombre_crear_lote);
        fecha  = (EditText) findViewById(R.id.fecha_crear_lote);
        area = (EditText )findViewById(R.id.area_crear_lote);
        edtDepartamento = (EditText )findViewById(R.id.departamento_crear_lote);
        edtMunicipio = (EditText )findViewById(R.id.municipio_crear_lote);
        punto_referencia = (TextView ) findViewById(R.id.PuntoReferencialote);
        addPunto = (Button) findViewById(R.id.AgregarPuntoLocalizacionLote);
        removePunto = (Button) findViewById(R.id.EliminarPuntoLocalizacionLote);

        edtDepartamento.setText("Cesar");
        edtMunicipio.setText("Valledupar");

        addPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irMapa();
            }
        });

        sdf = new SimpleDateFormat("dd/MM/yyyy");

        String currentDateandTime = sdf.format(new Date());
        fecha.setText(currentDateandTime);

        datosReforest = OpenHelperManager.getHelper(CrearLote.this,
                basededatos.class);

        utils = new LocationUtils(getApplicationContext());

    }

    private void irMapa() {


        location = utils.getLocation();
        if (location != null){

            lat = location.getLatitude();
            lng = location.getLongitude();
            recLote.add(new LatLng(lat,lng));

            punto_referencia.append("Latitud: "+ String.format("%.3f", lat) +
            "\nLongitud: " + String.format("%.3f", lng) + "\n");

            delimitacionBuffer.append(String.valueOf(lat));
            delimitacionBuffer.append(",");
            delimitacionBuffer.append(String.valueOf(lng));

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

    @SuppressLint("DefaultLocale")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                //delimitacion = delimitacionBuffer.toString();
                //areaLote = data.getDoubleExtra("area", 0);


            }
        }
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

    public void calcularArea(View v){
        if (recLote.size() < 4){
            Toast.makeText(CrearLote.this, "Minimo 4 puntos de referencia.", Toast.LENGTH_SHORT).show();

        }else {
            areaLote = (SphericalUtil.computeArea(recLote))/10000;
            area.setText(String.format("%.2f", areaLote));


        }

    }

    public void guardarLote(View view) throws ParseException {

        String nombreLote = nombre.getText().toString();
        areaLote = Double.parseDouble(area.getText().toString());
        delimitacion = delimitacionBuffer.toString();

        if (nombreLote.equals("")){
            Toast.makeText(CrearLote.this, "Por favor llene los campos faltantes.", Toast.LENGTH_SHORT).show();
            nombre.requestFocus();
        }else if (areaLote == 0){

            Toast.makeText(CrearLote.this, "El area no es valida, intentelo nuevamente.", Toast.LENGTH_SHORT).show();

        }else if(delimitacion.equals("")){
            Toast.makeText(CrearLote.this, "Escoger coordenadas.", Toast.LENGTH_SHORT).show();

        }

        else{
            Date parsed = sdf.parse(fecha.getText().toString());
            java.sql.Date fechaLote = new java.sql.Date(parsed.getTime());
            municipio = new Municipio(edtMunicipio.getText().toString());
            Lote lote = new Lote(nombreLote, fechaLote, areaLote, municipio, delimitacion);

            try {
                Dao lotesDao = datosReforest.getLoteDao();
                lotesDao.create(lote);

                startActivity(new Intent(CrearLote.this, Lotes.class));
                finish();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




    }
}
