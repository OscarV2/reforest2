package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.R;
import com.jegg.reforest.Utils.ItemAdapter;
import com.jegg.reforest.Utils.ItemLote;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Lotes extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private ListView listView;
    private SQLiteDatabase dbReforest;
    private TextView tvNoHayLotes;
    basededatos datosReforest;
    Dao lotesDao;


    private void init(){

        listView = (ListView ) findViewById(R.id.lista_lotes);
        tvNoHayLotes = (TextView)findViewById(R.id.tvNoHayLotes);
        datosReforest = OpenHelperManager.getHelper(Lotes.this,
                basededatos.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotes);
        setToolbar();

        init();
        cargarDatosLotes();
     //   onClickListaLotes();
    }

    private void setToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar_lotes);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                backMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            backMenu();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void idCrearLote(View view){
        Intent intent = new Intent(this, CrearLote.class);
        startActivity(intent);
        finish();
    }

    private void backMenu(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    private void onClickListaLotes(){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

    private void cargarDatosLotes(){
        try {
            lotesDao = datosReforest.getLoteDao();
            List<Lote> listaLotes = lotesDao.queryForAll();
          //  datosReforest.close();

            if ( listaLotes.size() > 0){
                cargarListaLotes(listaLotes);
                Log.e("si hay","lotes ");
            }else {


                tvNoHayLotes.setVisibility(View.VISIBLE);


            }
        } catch (SQLException e) {
            Log.e("no hay","lotes excepcion");
        }
    }

    private void cargarListaLotes(final List<Lote> listaLotes) {

        List<ItemLote> itemLotes = new ArrayList<>();
        for (int i= 0; i<listaLotes.size(); i++){
            itemLotes.add(new ItemLote(listaLotes.get(i).getNombre() , listaLotes.get(i).getFecha().toString()) );
        }

        listView.setAdapter(new ItemAdapter(this, itemLotes));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("clik","item");
                Intent irDetalles = new Intent(Lotes.this, Detalles.class);
                irDetalles.putExtra("id_lote", position);
                startActivity(irDetalles);
                finish();
            }
        });

    }

}











