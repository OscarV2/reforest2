package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import com.jegg.reforest.Actividades.Menu;
import com.jegg.reforest.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CrearLote extends AppCompatActivity {

    private EditText nombre,fecha,area;
    private TextView punto_referencia;
    private Button addPunto,removePunto;
    private Toolbar toolbar;
    private ActionBar actionBar;

    private void init(){

        nombre = (EditText) findViewById(R.id.nombre_crear_lote);
        fecha  = (EditText) findViewById(R.id.fecha_crear_lote);
        area = (EditText )findViewById(R.id.area_crear_lote);
        punto_referencia = (TextView ) findViewById(R.id.PuntoReferencialote);
        addPunto = (Button) findViewById(R.id.AgregarPuntoLocalizacionLote);
        removePunto = (Button) findViewById(R.id.EliminarPuntoLocalizacionLote);

        addPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irMapa();
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateandTime = sdf.format(new Date());
        fecha.setText(currentDateandTime);

    }

    private void irMapa() {
        Intent i = new Intent(CrearLote.this, Mapa.class);
        startActivityForResult(i, 4);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_lote);
        setToolbar();
        init();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onClickBack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            onClickBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void onClickBack(){
        startActivity(new Intent(this,Lotes.class));
        finish();
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_crear_lote);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back));
        }
    }

    public void guardarLote(View view){

        String fechaLote = fecha.getText().toString();
        double areaLote  = Double.parseDouble(area.getText().toString());

    }
}
