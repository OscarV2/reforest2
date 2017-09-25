package com.jegg.reforest;

/**
 * Created by oscar on 25/09/17.
 * Interfaz para cuando se terminen
 * de sincronizar los datos
 */

public interface SyncServiceStopped {

    void onSyncFinished(String msg);
}
