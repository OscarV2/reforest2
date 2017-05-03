package com.jegg.reforest.Servicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jegg.reforest.NetWatcher;

public class SinconizacionService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        NetWatcher watcher = new NetWatcher();

        this.registerReceiver(watcher, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));

        return START_STICKY;
    }
}
