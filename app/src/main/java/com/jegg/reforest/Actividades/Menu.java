package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jegg.reforest.R;
import com.jegg.reforest.Utils.Constantes;

import java.lang.reflect.Field;

public class Menu extends AppCompatActivity {

    SharedPreferences prefs;
    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        //setSupportActionBar(toolbar);
        setToolbar();
        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setTitle("");
            //actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.opcionSincronizar);
        SpannableStringBuilder builder = new SpannableStringBuilder("   Sincronizar");
        // replace "*" with icon
        builder.setSpan(new ImageSpan(this, R.drawable.ic_add_foto), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        item.setTitle(builder);

        MenuItem item2 = menu.findItem(R.id.opcionIrWeb);
        SpannableStringBuilder builder2 = new SpannableStringBuilder("  Ir a Reforest");
        // replace "*" with icon
        builder2.setSpan(new ImageSpan(this, R.drawable.ic_add_foto), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        item2.setTitle(builder2);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.opcionSincronizar:
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
