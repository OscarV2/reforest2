package com.jegg.reforest.Actividades;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.R;
import com.jegg.reforest.Utils.LocationUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback,
         GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private int numeroClicksMarker = 0;

    List<LatLng> recLote = new ArrayList<>();
    List<Arbol> listaArboles = new ArrayList<>();
    private double area = 0;
    private Marker marcadorMiPosicion;

    private Toolbar toolbar;
    private ActionBar actionBar;
    private Spinner spinner;
    List<Lote> listaLotes =  new ArrayList<>();
    private String[] nombresLotes ;
    LocationUtils utils;

    Location miLocation;
    basededatos datosReforest;
    Dao lotesDao;
    PolygonOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        setToolbar();

        utils = new LocationUtils(Mapa.this);
        datosReforest = OpenHelperManager.getHelper(this, basededatos.class);
        try {
            lotesDao = datosReforest.getLoteDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        spinner = (Spinner) findViewById(R.id.spinner_mapa);

        cargarLotes();
        options = new PolygonOptions();

    }

    private void cargarLotes() {
        try {
            Log.e("cargando","lotes ");
            //lotesDao = datosReforest.getLoteDao();
            listaLotes = lotesDao.queryForAll();

            if ( listaLotes.size() > 0){
                Log.e("si hay","lotes ");
                cargarSpinner(listaLotes);

            }else {
                Toast.makeText(this, "no hay lotes", Toast.LENGTH_SHORT).show();
                mostrarDialogo();

            }
        } catch (SQLException e) {
            Log.e("no hay","lotes excepcion");
        }

    }

    private void cargarSpinner(List<Lote> listaLotes) {

        nombresLotes = new String[listaLotes.size()];
        for (int i = 0; i<listaLotes.size(); i++){

            nombresLotes[i] = listaLotes.get(i).getNombre();

        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, R.layout.item_spinner_mapa, nombresLotes);
        spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_mapa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               dibujarLote(position);
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

        ForeignCollection<Arbol> arboles = lote.getArboles();

        if (arboles.size() == 0){
            Toast.makeText(this, "No se han sembrado árboles en este lote.", Toast.LENGTH_SHORT).show();
        }else {

            CloseableWrappedIterable<Arbol> iterator = arboles.getWrappedIterable();
            for (Arbol arbol : iterator){

                listaArboles.add(arbol);
                dibujaArbol(arbol.getPosicion() , arbol.getId());
            }

        }

        PolygonOptions options = new PolygonOptions();

        for (int i = 0; i<recLote.size(); i++){
            options.add(recLote.get(i));
        }

        Polygon lotePoligono = mMap.addPolygon(options);


    }

    private void dibujaArbol(LatLng posicion, int id) {

        mMap.addMarker(new MarkerOptions().position(posicion)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTag(id);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(posicion.latitude, posicion.longitude), 18f));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);
        utils = new LocationUtils(Mapa.this);
        miLocation = utils.getLocation();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                mMap.addMarker(new MarkerOptions().position(latLng));

            }
        });

    }

    private void setToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_mapa);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Log.e("marker","click");
        Log.e("numClicks ", String.valueOf(numeroClicksMarker));
        if (numeroClicksMarker < 4){
            options.add(marker.getPosition());

            dibujarPuntoRef(marker);
            numeroClicksMarker++;
        }else {

            options.fillColor(0x7F00FF00).strokeColor(Color.GREEN);
            Polygon lotePoligono = mMap.addPolygon(options);

        }

/*
        Log.e("id marker ", String.valueOf(marker.getTag()));
        Arbol arbolMarker = listaArboles.get((Integer)marker.getTag() - 1);

        marker.setTitle("Mango");
        marker.setSnippet("Altura ");

        marker.showInfoWindow();
        Log.e("marker","click");
*/
        return false;
    }

    private void mostrarDialogo() {

        AlertDialog.Builder volverCrearLoteDialog = new AlertDialog.Builder(Mapa.this);
        volverCrearLoteDialog.setTitle("No hay lotes")
                             .setMessage("Por favor agrege un nuevo lote para visualizarlo en el mapa. ")
                             .setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     Intent i = new Intent(Mapa.this, Menu.class);
                                     startActivity(i);
                                     finish();

                                 }
                             })
                             .create();

        volverCrearLoteDialog.show();
    }

    private void dibujarPuntoRef(Marker marker) {

        mMap.addMarker(new MarkerOptions().position(marker.getPosition()).icon(BitmapDescriptorFactory.
                defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

    }
/*
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
*/




}
