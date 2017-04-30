package com.jegg.reforest.Actividades;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Spinner;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;
import com.jegg.reforest.R;

import java.util.ArrayList;
import java.util.List;

public class Mapa extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private int numeroClicksMarker = 0;

    LatLng[] rectLote = new LatLng[4];

    List<LatLng> latLngs = new ArrayList<>();
    private Marker marcadorMiPosicion;
//    private LocationListener locationListener;
//    private LocationManager locationManager;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private Spinner spinner;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        setToolbar();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        spinner = (Spinner) findViewById(R.id.spinner_mapa);


        if (client == null) {
            Log.e("cliente", "nuevo");
            client = new GoogleApiClient.Builder(getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            client.connect();

        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Location locationClick = new Location(LOCATION_SERVICE);
                locationClick.setLatitude(latLng.latitude);
                locationClick.setLongitude(latLng.longitude);
                actualizarPosicion(locationClick);
                //getLocalizacion(locationClick);
            }
        });
        mMap.setOnMarkerClickListener(this);
    }


    private void actualizarPosicion(Location locationClick) {
        if (!(marcadorMiPosicion == null)){
            marcadorMiPosicion.setPosition(new LatLng(locationClick.getLatitude(), locationClick.getLongitude()));
        }else{
            marcadorMiPosicion =  mMap.addMarker(new MarkerOptions().position(new LatLng(locationClick.getLatitude(), locationClick.getLongitude())).icon(BitmapDescriptorFactory.
                    defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationClick.getLatitude(), locationClick.getLongitude()), 15));


        }
    }



    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_mapa);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back));
        }
    }
/*
    private void getLocalizacion(Location location) {
        //mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).icon(BitmapDescriptorFactory.
                defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));
    }
*/
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.e("apiLocate", "Conectada");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(client);

        actualizarPosicion(location);

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(15000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(client, mLocationRequest, new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.e("location ","changed");
                actualizarPosicion(location);
            }
        });



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("no pudo","Conectarse");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        Log.e("marker","click");
        if (numeroClicksMarker < 4){
            rectLote[numeroClicksMarker] = marker.getPosition();
            latLngs.add(marker.getPosition());

            dibujarPuntoRef(marker);
            numeroClicksMarker++;
        }else {
            PolygonOptions options = new PolygonOptions()
                            .add(rectLote[0])
                            .add(rectLote[1])
                            .add(rectLote[2])
                            .add(rectLote[3]);
            Polygon lotePoligono = mMap.addPolygon(options);
            Log.e("Area ", String.valueOf(SphericalUtil.computeArea(latLngs)));

        }

        return false;
    }

    private void dibujarPuntoRef(Marker marker) {

        mMap.addMarker(new MarkerOptions().position(marker.getPosition()).icon(BitmapDescriptorFactory.
                defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

    }
}
