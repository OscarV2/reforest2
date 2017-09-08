package com.jegg.reforest.actividades;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.R;
import com.jegg.reforest.Utils.LastLocationReady;
import com.jegg.reforest.Utils.LocationUtils;
import com.jegg.reforest.Utils.SyncServiceUtils;

import java.util.ArrayList;
import java.util.List;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback,
         GoogleMap.OnMarkerClickListener, LastLocationReady {

    private GoogleMap mMap;

    List<LatLng> recLote = new ArrayList<>();
    List<Arbol> listaArboles = new ArrayList<>();
    //private double area = 0;
    //private Marker marcadorMiPosicion;

    private Spinner spinner;
    List<Lote> listaLotes =  new ArrayList<>();
    LocationUtils utils;

    private SyncServiceUtils sync;
    Location miLocation;
    PolygonOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        setToolbar();

        utils = new LocationUtils(Mapa.this);

        if (utils.permisosGranted()){

            if (!(utils.gpsEnabled())){
                mostrarDialogoGps();
            }else{

                utils.setLastLocation(this);
                sync = new SyncServiceUtils(Mapa.this);

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);

                spinner = (Spinner) findViewById(R.id.spinner_mapa);
                options = new PolygonOptions();
                cargarLotes();
            }
        }else {

            ActivityCompat.requestPermissions(Mapa.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    private void cargarLotes() {

            listaLotes = sync.getListaLotes();
            if ( listaLotes.size() > 0){

                cargarSpinner(listaLotes);
            }else {
                Toast.makeText(this, "no hay lotes", Toast.LENGTH_SHORT).show();
                //mostrarDialogo();
            }
    }

    private void cargarSpinner(final List<Lote> listaLotes) {

        String[] nombresLotes = new String[listaLotes.size()];
        for (int i = 0; i<listaLotes.size(); i++){

            nombresLotes[i] = listaLotes.get(i).getNombre();

        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, R.layout.item_spinner_mapa, nombresLotes);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_mapa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               dibujarLote(position);
                Log.e("Position", String.valueOf(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void dibujarLote(int position) {

        mMap.clear();
        Lote lote = listaLotes.get(position);
        recLote = lote.getPuntos();

        listaArboles = lote.getArboles();

        if (listaArboles.size() == 0){
            Toast.makeText(this, "No se han sembrado árboles en este lote.", Toast.LENGTH_SHORT).show();
        }else {

            for (Arbol arbol : listaArboles){

                dibujaArbol(arbol.getPosicion() , listaArboles.indexOf(arbol));
            }
        }

        for (int i = 0; i < recLote.size(); i++){

            options.add(recLote.get(i));
        }

        PolylineOptions lineOptions = new PolylineOptions();
        lineOptions.color(Color.GREEN);
        lineOptions.add(recLote.get(0));
        lineOptions.add(recLote.get(1));
        lineOptions.add(recLote.get(2));
        lineOptions.add(recLote.get(3));
        lineOptions.add(recLote.get(0));
        mMap.addPolyline(lineOptions);
    }

    private void dibujaArbol(LatLng posicion, int positionList) {

        mMap.addMarker(new MarkerOptions().position(posicion)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTag(positionList);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(posicion.latitude, posicion.longitude), 19f));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        UiSettings mUiSettings;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        miLocation = utils.getLocation();

        if (miLocation != null){

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(miLocation.getLatitude(), miLocation.getLongitude()), 17f));
        }

    }

    private void setToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_mapa);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        getMenuInflater().inflate(R.menu.menu_mapa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMarkerClick(Marker marker){

        Arbol arbolMarker = listaArboles.get((int)marker.getTag());

        String fecha = arbolMarker.getFecha_sembrado();
        int sizeListaEspecies = arbolMarker.getArbolEspecie().size();

        Especie especie = arbolMarker.getArbolEspecie().get(sizeListaEspecies-1).getEspecie();
        Estado estado = arbolMarker.getLastEstado();
        marker.setTitle(especie.getNombre());
        if (estado == null){

            marker.setSnippet("Sembrado en "+ fecha);
        }else {
            marker.setSnippet("Estado: " + estado.getNombre());
        }

        marker.showInfoWindow();
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("KEYCODE ", String.valueOf(keyCode));
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Log.e("KEY ","BACK");
            onClickBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onClickBack(){

        startActivity(new Intent(this, Menu.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(Mapa.this, Menu.class));
                finish();
                return true;
            case R.id.map_hibrido:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.map_satelite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.map_tierra:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarDialogoGps() {

        AlertDialog.Builder volver = new AlertDialog.Builder(Mapa.this);
        volver.setTitle("Verificar Servicio de Localizacion.")
                .setMessage("Por favor habilite la ubicacion de su dispositivo.")
                .setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Mapa.this, Menu.class);
                        startActivity(i);
                        finish();

                    }
                })
                .create();

        volver.show();
    }

    @Override
    public void locationReady(Location location) {

    }
}