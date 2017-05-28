package com.jegg.reforest.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jegg.reforest.R;
import com.jegg.reforest.Servicios.SinconizacionService;
import com.jegg.reforest.Utils.Constantes;

public class Menu extends AppCompatActivity {

    SharedPreferences prefs;
    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
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
        builder.setSpan(new ImageSpan(this, R.drawable.ic_cached_black_24dp), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        item.setTitle(builder);

        MenuItem item2 = menu.findItem(R.id.opcionIrWeb);
        SpannableStringBuilder builder2 = new SpannableStringBuilder("  Ir a Reforest");
        // replace "*" with icon
        builder2.setSpan(new ImageSpan(this, android.R.drawable.ic_media_play), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        item2.setTitle(builder2);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.opcionSincronizar:
                startService(new Intent(Menu.this, SinconizacionService.class));
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
