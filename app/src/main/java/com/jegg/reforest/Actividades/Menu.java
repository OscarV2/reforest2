package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jegg.reforest.R;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);


    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.opcionSincronizar:
                break;
            case R.id.opcionCerrarApp:
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
