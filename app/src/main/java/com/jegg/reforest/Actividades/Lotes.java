package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jegg.reforest.R;

public class Lotes extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private ListView listView;

    private void init(){

        listView = (ListView ) findViewById(R.id.lista_lotes);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotes);
        setToolbar();
        init();
        cargarDatosLotes();
        onClickListaLotes();
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



    }

}
