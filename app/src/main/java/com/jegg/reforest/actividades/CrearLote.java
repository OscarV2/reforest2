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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Municipio;
import com.jegg.reforest.R;
import com.jegg.reforest.Utils.Constantes;
import com.jegg.reforest.Utils.LocationLoteUtils;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrearLote extends AppCompatActivity implements OnMapReadyCallback {

    private TextInputEditText nombre, area, edtMunicipio;
    private TextView punto_referencia;
    private StringBuffer delimitacionBuffer;
    private Double areaLote;
    private basededatos datosReforest;
    SimpleDateFormat sdf;

    private GoogleMap mMap;
    private int contPuntosLote = 1;
    List<LatLng> recLote = new ArrayList<>();

    Municipio municipio;
    double lat, lng;
    LocationLoteUtils utils;
    Location location;

    private void init(){

        nombre = (TextInputEditText) findViewById(R.id.nombre_crear_lote);
        TextInputEditText fecha = (TextInputEditText) findViewById(R.id.fecha_crear_lote);
        area = (TextInputEditText) findViewById(R.id.area_crear_lote);
        edtMunicipio = (TextInputEditText)findViewById(R.id.municipio_crear_lote);

        punto_referencia = (TextView ) findViewById(R.id.PuntoReferencialote);
        Button addPunto = (Button) findViewById(R.id.AgregarPuntoLocalizacionLote);

        edtMunicipio.setText("Valledupar, Cesar");

        addPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irMapa();
            }
        });

        sdf = new SimpleDateFormat("dd/MM/yyyy");

        String currentDateandTime = sdf.format(new Date());
        fecha.setText(currentDateandTime);

        fecha.setEnabled(false);
        datosReforest = OpenHelperManager.getHelper(CrearLote.this,
                basededatos.class);

        nombre.requestFocus();

        edtMunicipio.setEnabled(false);
        area.setText("0.0");
        //area.setEnabled(false);
    }

    private void irMapa() {

        if (contPuntosLote < 5){

                location = utils.getLocation();
            if (location != null){

                lat = location.getLatitude();
                lng = location.getLongitude();
                recLote.add(new LatLng(lat,lng));

                utils.dibujarPunto(location);
                utils.dibujarLinea(recLote);
                contPuntosLote++;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_lote);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_crear_lote);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    public void eliminarPunto(View v){
        //borrar ultimas dos lineas del textview

        if (!(recLote.size() == 0)){

            int positionPunto = recLote.size() - 1;
            //borrar ultima coordenada de recLote
            recLote.remove(positionPunto);
            utils.borrarPunto(positionPunto);

            Toast.makeText(this, "puntos borrados", Toast.LENGTH_SHORT).show();

            contPuntosLote--;
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

            areaLote = (SphericalUtil.computeArea(recLote))/10000;
            area.setText(String.format("%.3f", areaLote));
            Toast.makeText(this, "Area en m2: "+String.format("%.3f", SphericalUtil.computeArea(recLote)), Toast.LENGTH_SHORT).show();
        }
    }

    public void guardarLote(View view) throws ParseException {

        String nombreLote = nombre.getText().toString();

        if (!area.getText().toString().equals("")){

            areaLote = Double.parseDouble(area.getText().toString());
        }

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
            String delimitacion = delimitacionBuffer.toString();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        utils = new LocationLoteUtils(CrearLote.this, mMap);

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (!(utils.gpsEnabled())){
            mostrarDialogoGps();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        utils.disConnect();
    }
}
