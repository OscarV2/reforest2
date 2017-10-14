package com.jegg.reforest.actividades;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.R;
import com.jegg.reforest.Servicios.SincronizacionService;
import com.jegg.reforest.SyncServiceStopped;
import com.jegg.reforest.Utils.CerrarDialogo;
import com.jegg.reforest.Utils.Constantes;
import com.jegg.reforest.controladores.DownloadUserData;

import java.sql.SQLException;

public class Menu extends AppCompatActivity implements CerrarDialogo, SyncServiceStopped{

    SharedPreferences prefs;
    ProgressDialog progressDialog;
    private Persona usuario;
    DownloadUserData descargarDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setToolbar();
        descargarDatos = new DownloadUserData(Menu.this, this);
        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        int idPersona = prefs.getInt("id_persona", 0);
        basededatos datosReforest = OpenHelperManager.getHelper(Menu.this, basededatos.class);

        try {
            Dao<Persona, Integer> daoPersonas = datosReforest.getPersonasDao();
            usuario = daoPersonas.queryForId(idPersona);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (ActivityCompat.checkSelfPermission(Menu.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Menu.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(Menu.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setTitle("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.opcionSincronizar);
        SpannableStringBuilder builder = new SpannableStringBuilder("   Sincronizar");
        // replace "*" with icon
        builder.setSpan(new ImageSpan(this, R.drawable.ic_cached_black_24dp), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        item.setTitle(builder);

        MenuItem item2 = menu.findItem(R.id.opcionIrWeb);
        SpannableStringBuilder builder2 = new SpannableStringBuilder("  Ir a Reforest");
        // replace "*" with icon
        builder2.setSpan(new ImageSpan(this, R.drawable.ic_arrow_forward), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        item2.setTitle(builder2);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)     {
        switch (item.getItemId()){
            case R.id.opcionSincronizar:

                progressDialog = ProgressDialog.show(Menu.this, "Sincronizando...", "Por favor espere..", true);
                SincronizacionService sin = new SincronizacionService(Menu.this, this);
                sin.comenzar("autoSync");
                break;
            case R.id.opcionIrWeb:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constantes.URL_SITIO_WEB));
                startActivity(browserIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void irLotes(View v){
        Intent intent = new Intent(this, Lotes.class);
        startActivity(intent);
        finish();
    }

    public void irMapa(View view){
        Intent intent = new Intent(this, Mapa.class);
        startActivity(intent);
        finish();
    }

    public void irPreferencias(View view){
        Intent intent = new Intent(this, Preferecias.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void cerrardialogo() {

    }

    @Override
    public void onSyncFinished(String msg) {

        Log.e("mensaje", msg);
        switch (msg) {
            case "SyncUsuariosSuccess":
                try {
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "stop":
                try {
                    progressDialog.dismiss();
                    Toast.makeText(Menu.this, "Sincronizacion exitosa.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "SyncFiled":
                try {
                    progressDialog.dismiss();
                    Toast.makeText(Menu.this, "Sincronizacion fallida, Por favor intente mas tarde.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                progressDialog.dismiss();
        }
    }

}