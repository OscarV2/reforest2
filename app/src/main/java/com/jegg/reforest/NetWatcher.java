package com.jegg.reforest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by oscarvc on 2/05/17.
 */

public class NetWatcher extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {


        int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

        switch (extraWifiState)
        {
            case WifiManager.WIFI_STATE_DISABLED:
                Log.e("wifi","Apagado");
            case WifiManager.WIFI_STATE_DISABLING:
                Log.e("wifi","DISABLING");
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                while(conMan.getActiveNetworkInfo() == null || conMan.getActiveNetworkInfo().getState() != NetworkInfo.State.CONNECTED)
                {

                }

                break;
            case WifiManager.WIFI_STATE_ENABLING:
                Log.e("wifi","Enabling");
                break;
            case WifiManager.WIFI_STATE_UNKNOWN:
                Log.e("wifi","Desconocido.");
                break;
        }
    }

    }


