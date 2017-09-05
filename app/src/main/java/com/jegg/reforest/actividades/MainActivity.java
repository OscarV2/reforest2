package com.jegg.reforest.actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.jegg.reforest.R;
import com.jegg.reforest.Servicios.SinconizacionService;
import com.jegg.reforest.Utils.MainActivityAux;

import java.io.File;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    String PATH_BASE_DE_DATOS = "datosReforest";

    ProgressDialog progressDialog;
    boolean inicioSesion, autoSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        inicioSesion = prefs.getBoolean("inicio_sesion", false);
        autoSync = prefs.getBoolean("automatic_sync", false);
        Log.e("Bool InicioSesion", String.valueOf(inicioSesion));

        if (!existeBaseDatos()){
            Log.e("base de datos","no hay");
            //new Handler().postDelayed(new Runnable() {
            //  @Override
            //public void run() {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        MainActivityAux main = new MainActivityAux(getApplicationContext());
                        main.insertarEspecies();
                        main.insertarActividadesEnBd();
                        main.insertarcrearEstadosEnBd();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            //      }
            //        }, 1500);

        }

        (findViewById(R.id.btn_acceso_main)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inicioSesion){

                    irMenu();
                }else{
                    IniciarSesion();
                }
            }
        });
        }

    private void irMenu() {

        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    private void sincronizando(){
  //
        if (autoSync || (!inicioSesion)){

            progressDialog = ProgressDialog.show(MainActivity.this, "Sincronizando...", "Por favor espere..", true);

            progressDialog.setCancelable(false);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            startService(new Intent(MainActivity.this, SinconizacionService.class));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    progressDialog.dismiss();
                                    if (autoSync){

                                        Toast.makeText(MainActivity.this, "Datos sincronizados exitosamente.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }


                    }).start();

                }
            }, 1000);

        }
    }

    @Override
    protected void onStart() {

        sincronizando();
        super.onStart();
    }

    private boolean existeBaseDatos() {

        boolean b;

            File dbFile = getApplicationContext().getDatabasePath(PATH_BASE_DE_DATOS);
            b = dbFile.exists();
        if (b){
            Log.e("Si","existe.");
        }else {
            Log.e("No","existe.");
        }
        return b;
    }

    public void IniciarSesion(){

        Intent intent = new Intent(this, IniciarSesion.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {

        OpenHelperManager.releaseHelper();
        super.onDestroy();
    }
}
