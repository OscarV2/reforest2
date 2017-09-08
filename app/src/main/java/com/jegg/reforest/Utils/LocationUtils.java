package com.jegg.reforest.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static android.content.Context.LOCATION_SERVICE;

public class LocationUtils implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    Context context;
    GoogleApiClient client;
    Location location1;
    LocationRequest mLocationRequest;
    private LastLocationReady lastLocation;

    public LocationUtils(Context context) {
        this.context = context;

        if (client == null) {

            client = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            if (gpsEnabled()){
                client.connect();
            }

        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.e("onConncted","");
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

            while (location1 == null){

                location1 = LocationServices.FusedLocationApi.getLastLocation(client);
                if (location1 != null){

                    lastLocation.locationReady(location1);
                    break;
                }
            }

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(15000);
            mLocationRequest.setFastestInterval(10000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationServices.FusedLocationApi.requestLocationUpdates(client, mLocationRequest, new com.google.android.gms.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    location1 = location;
                    if (lastLocation != null){

                        lastLocation.locationReady(location1);
                    }


                }
            });
        }
      //  while (location1 == null) {
            //Log.e("location todavia es", "null");
/*
            LocationServices.FusedLocationApi.requestLocationUpdates(client, mLocationRequest, new com.google.android.gms.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    location1 = location;

                }
            });
*/
        //}

    }

    public void setLastLocation(LastLocationReady lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Location getLocation() {

        return location1;
    }

    public void disConnect(){

        client.disconnect();
    }

    public boolean permisosGranted(){
        boolean b;

        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.e("permisos","not GRANTED");
            b = false;
        }else {
            Log.e("Permisos","granted");
            b = true;
        }


        return b;
    }

    public boolean isConnected(){

        return client.isConnected();
    }

    public boolean gpsEnabled(){

        boolean isEnabled;
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            isEnabled = true;
        }else{
            isEnabled = false;
        }
        return isEnabled;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}






