package com.jegg.reforest.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.R;
import com.jegg.reforest.controladores.ControllerLote;
import com.jegg.reforest.adapter.ItemAdapter;
import com.jegg.reforest.adapter.ItemLote;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Lotes extends AppCompatActivity {

    private ListView listView;
    private basededatos datosReforest;
    private Persona persona;
    List<Lote> listaLotes;
    ItemAdapter itemAdapter;
    List<ItemLote> itemLotes = new ArrayList<>();

    private void init(){

        listaLotes = new ArrayList<>();
        listView = (ListView ) findViewById(R.id.lista_lotes);
        registerForContextMenu(listView);
        datosReforest = OpenHelperManager.getHelper(Lotes.this,
                basededatos.class);

        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        int idPersona = prefs.getInt("id_persona", 0);
        try {
            Dao<Persona, Integer> daoPersonas = datosReforest.getPersonasDao();
            persona = daoPersonas.queryForId(idPersona);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotes);
        setToolbar();

        init();
        cargarDatosLotes();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        Log.e("create context", "menu");
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);

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

    private void cargarDatosLotes(){

        ForeignCollection<Lote> foreignLotes = persona.getLotes();
        CloseableWrappedIterable<Lote> iterator = foreignLotes.getWrappedIterable();
        for (Lote lote: iterator){

            listaLotes.add(lote);
        }
        if ( listaLotes.size() > 0){
            cargarListaLotes(listaLotes);
        }else {

            Toast.makeText(this, "No hay lotes creados.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int position = info.position;
        switch (item.getItemId()){

            case R.id.opt_ir_crear_lote:

                Intent irDetalles = new Intent(Lotes.this, Detalles.class);
                irDetalles.putExtra("id_lote", listaLotes.get(position).getId());
                irDetalles.putExtra("nombre_lote", listaLotes.get(position).getNombre());
                irDetalles.putExtra("num_arboles", getTotalArbolesLote(listaLotes.get(position)));
                startActivity(irDetalles);
                finish();
                break;

            case R.id.opt_eliminar_lote:

                final Lote lote = listaLotes.get(position);
                if (!lote.isUploaded()){

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            ControllerLote borrarLote = new ControllerLote(Lotes.this, lote);
                            borrarLote.eliminar();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(Lotes.this, "Se elimino el lote con exito!.", Toast.LENGTH_LONG).show();
                                    itemLotes.remove(position);
                                    itemAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }).start();



                }else {

                    Toast.makeText(this, "No se puede eliminar el lote.", Toast.LENGTH_LONG).show();
                }
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void cargarListaLotes(final List<Lote> listaLotes) {

        for (int i= 0; i<listaLotes.size(); i++){

            int numeroArboles = getTotalArbolesLote(listaLotes.get(i));
            itemLotes.add(new ItemLote(listaLotes.get(i).getNombre() , listaLotes.get(i).getFecha_creacion(),
                    numeroArboles, listaLotes.get(i).getArea()) );
        }

        itemAdapter = new ItemAdapter(this, itemLotes);
        listView.setAdapter(itemAdapter);


        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent irDetalles = new Intent(Lotes.this, Detalles.class);
                irDetalles.putExtra("id_lote", listaLotes.get(position).getId());
                irDetalles.putExtra("nombre_lote", listaLotes.get(position).getNombre());
                irDetalles.putExtra("num_arboles", getTotalArbolesLote(listaLotes.get(position)));
                startActivity(irDetalles);
                finish();
            }
        });*/
    }

    private int getTotalArbolesLote(Lote itemLote) {

        int num = 0;

        List<Arbol> arboles = itemLote.getArboles();
        if (arboles.size() > 0){

            for(Arbol arbol : arboles){
                Log.e("idARBOL", arbol.getId());
            }
            num = arboles.size();
        }
        return num;
    }

    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_lotes);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        OpenHelperManager.releaseHelper();
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
}