package com.jegg.reforest.Utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by oscarvc on 4/05/17.
 */

public class LocationUtils implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private Context context;
    private GoogleApiClient client;
    private Location location;

    public LocationUtils(Context context) {
        this.context = context;

        if (client == null) {

            Log.e("cliente", "nuevo");
            client = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            client.connect();
        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.e("apiLocate", "Conectada");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        while (location == null) {
            Log.e("location todavia es", "null");
            location = LocationServices.FusedLocationApi.getLastLocation(client);
            Log.e("latitud", String.valueOf(location.getLatitude()));
            Log.e("longitud", String.valueOf(location.getLongitude()));
        }
        Log.e("saliendo del", "while");
    }

    public Location getLocation() {

        Log.e("latitud", String.valueOf(location.getLatitude()));
        Log.e("longitud", String.valueOf(location.getLongitude()));
        return location;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
