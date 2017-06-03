package com.jegg.reforest.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class LocationLoteUtils extends LocationUtils {

    private List<Marker> puntos;
    private Marker miPositionMarker;
    private GoogleMap mapa;
    private PolygonOptions options;
    private boolean FlagMarkerRemoved;
    private Polygon lotePolygon;

    public LocationLoteUtils(Context context, GoogleMap map) {

        super(context);
        this.mapa = map;
        options = new PolygonOptions();
        puntos = new ArrayList<>();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location1 = LocationServices.FusedLocationApi.getLastLocation(client);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(15000);
        mLocationRequest.setFastestInterval(10000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(client, mLocationRequest, new com.google.android.gms.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                location1 = location;
                if (!FlagMarkerRemoved){

                    if(miPositionMarker == null && location1 != null){

                        miPositionMarker = mapa.addMarker(new MarkerOptions().position(new LatLng(location1.getLatitude(), location1.getLongitude()))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location1.getLatitude(), location1.getLongitude()), 18f));

                        miPositionMarker.setTitle("¡Aqui estoy!");
                        miPositionMarker.showInfoWindow();
                    }else {
                        assert location1 != null;
                        miPositionMarker.setPosition(new LatLng(location1.getLatitude(), location1.getLongitude()));

                    }
                }
            }
        });
        super.onConnected(bundle);
    }

    public void dibujarLote(List<LatLng> recLote){

        for (int i = 0; i < recLote.size(); i++){

            options.add(recLote.get(i));
        }

        options.fillColor(0x7F0000FF).strokeColor(Color.GREEN);
        lotePolygon =  mapa.addPolygon(options);
        miPositionMarker.remove();
        FlagMarkerRemoved = true;
    }

    public void borrarPunto(int i){

        puntos.get(i).remove();
        FlagMarkerRemoved = false;
        lotePolygon.remove();
    }

    public void dibujarPunto(Location location){

        puntos.add(mapa.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));

        Toast.makeText(context, "Punto agregado exitosamente.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean gpsEnabled() {
        return super.gpsEnabled();
    }

    @Override
    public void disConnect() {
        super.disConnect();
    }
}








