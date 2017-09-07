package com.jegg.reforest.actividades;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
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
import com.jegg.reforest.Servicios.SinconizacionService;
import com.jegg.reforest.Utils.Constantes;
import com.jegg.reforest.controladores.DownloadUserData;

import java.sql.SQLException;

public class Menu extends AppCompatActivity {

    SharedPreferences prefs;
    ProgressDialog progressDialog;
    private Persona usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setToolbar();
        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        int idPersona = prefs.getInt("id_persona", 0);
        basededatos datosReforest = OpenHelperManager.getHelper(Menu.this, basededatos.class);

        try {
            Dao<Persona, Integer> daoPersonas = datosReforest.getPersonasDao();
            usuario = daoPersonas.queryForId(idPersona);
        } catch (SQLException e) {
            e.printStackTrace();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.opcionSincronizar:

                progressDialog = ProgressDialog.show(Menu.this, "Sincronizando...", "Por favor espere..", true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        startService(new Intent(Menu.this, SinconizacionService.class));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                progressDialog.dismiss();
                                Toast.makeText(Menu.this, "Datos sincronizados exitosamente.", Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                }).start();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {


                            }
                        });
                    }
                }, 1000);

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
}
