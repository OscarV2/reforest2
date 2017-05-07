package com.jegg.reforest.Actividades;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Actividad;
import com.jegg.reforest.Entidades.Altura;
import com.jegg.reforest.Entidades.Arbol;
import com.jegg.reforest.Entidades.ArbolEspecie;
import com.jegg.reforest.Entidades.ArbolEstado;
import com.jegg.reforest.Entidades.DesarrolloActividades;
import com.jegg.reforest.Entidades.Especie;
import com.jegg.reforest.Entidades.Estado;
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.R;
import com.jegg.reforest.Utils.LocationUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Detalles extends AppCompatActivity implements OnMapReadyCallback,
        AdapterView.OnItemSelectedListener, GoogleMap.OnMarkerClickListener {

    EditText edtEspecie, edtFecha, edtComentarios, edtAltura;
    ImageView fotoArbol;
    TextView txtCoordenadas, txtLote;
    Spinner actividades, saludArbol;

    private basededatos datosReforest;

    int idLote, idActividad = 1, idEstado, idArbol=0;
    String nombreLote;
    String comentariosActividad;
    String altura;
    String especie;
    File filePath;
    private GoogleMap mMap;

    LinearLayout lay_edt_especie, lay_edt_altura,
            laySpinnerSaludArbol, layMapa;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    double latitud, longitud;
    private Toolbar toolbar;
    private ActionBar actionBar;
    SimpleDateFormat sdf;
    String currentDateandTime;
    String fotoPath;
    boolean arbolesCargados = false;

    java.sql.Date fechaActividad;
    Lote lote;
    Arbol arbol;

    Altura alturaEntity;

    Actividad actividad;
    DesarrolloActividades desarrolloActividad;

    Estado estadoEntidad;
    ArbolEstado arbolEstado;

    Especie especieEntidad;
    ArbolEspecie arbolEspecie;

    Location location;
    LocationUtils utils;

    ForeignCollection<Arbol> listaArboles;

    Dao daoDesarrolloAct, daoActividad, daoEspecie,
            daoArbolEspecie, daoEstado, daoArbolEstado,
            daoArboles, daoAltura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        setToolbar();

        datosReforest = OpenHelperManager.getHelper(Detalles.this, basededatos.class);

        idLote = this.getIntent().getIntExtra("id_lote", 0);
        Log.e("idLote", String.valueOf(idLote));

        try {
            Dao daoLotes = datosReforest.getLoteDao();
            lote = (Lote) daoLotes.queryForId(idLote);
            nombreLote = lote.getNombre();

            listaArboles = lote.getArboles();

            daoArboles = datosReforest.getArbolDao();
            daoActividad = datosReforest.getActividadsDao();
            daoDesarrolloAct = datosReforest.getDesarrolloActividadesDao();
            daoEspecie = datosReforest.getEspeciesDao();
            daoArbolEspecie = datosReforest.getArbolEspeciesDao();
            daoEstado = datosReforest.getEstadoDao();
            daoArbolEstado = datosReforest.getArbolEstadosDao();

            daoAltura = datosReforest.getAlturaDao();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        init();
    }

    private void setToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar_detalles);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
        }

    }

    private void init() {

        lay_edt_altura = (LinearLayout) findViewById(R.id.lay_edt_altura);
        lay_edt_especie = (LinearLayout) findViewById(R.id.lay_edt_especie);
        laySpinnerSaludArbol = (LinearLayout) findViewById(R.id.linear_spiner_salud_arbol);
        layMapa = (LinearLayout) findViewById(R.id.lay_mapa);

        edtComentarios = (EditText) findViewById(R.id.comentario_detalles);
        edtEspecie = (EditText) findViewById(R.id.especieArbol);
        edtFecha = (EditText) findViewById(R.id.FechaActividadArbol);
        edtAltura = (EditText) findViewById(R.id.alturaArbol);

        txtCoordenadas = (TextView) findViewById(R.id.CoordenadasArbol);
        fotoArbol = (ImageView) findViewById(R.id.foto_arbol);


        actividades = (Spinner) findViewById(R.id.spinner_detalle_actividad);
        saludArbol = (Spinner) findViewById(R.id.spinner_salud_arbol);

        txtLote = (TextView) findViewById(R.id.nombre_lote);
        txtLote.append(nombreLote);

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        currentDateandTime = sdf.format(new Date());
        edtFecha.setText(currentDateandTime);

        try {
            Date parsed = sdf.parse(currentDateandTime);
            fechaActividad = new java.sql.Date(parsed.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lista_tipo_actividades, R.layout.item_spinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        actividades.setAdapter(adapter);
        actividades.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterSaludArbol = ArrayAdapter.createFromResource(this,
                R.array.lista_estados, R.layout.item_spinner);

        adapterSaludArbol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        saludArbol.setAdapter(adapterSaludArbol);
        saludArbol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        idEstado = 1;
                        setEstadoEntidad();
                        break;
                    case 1:
                        idEstado = 2;
                        setEstadoEntidad();
                        break;
                    case 2:
                        idEstado = 3;
                        setEstadoEntidad();
                        break;
                    case 3:
                        idEstado = 4;
                        setEstadoEntidad();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        utils = new LocationUtils(getApplicationContext());
    }

    private void setEstadoEntidad() {

        try {
            estadoEntidad = (Estado) daoArbolEstado.queryForId(idEstado);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//esto es para la navegacion hacia atras
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                startActivity(new Intent(Detalles.this, Lotes.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {

                latitud = data.getDoubleExtra("latidud_arbol", 0);
                longitud = data.getDoubleExtra("longitud_arbol", 0);
                txtCoordenadas.append("\n" + "Latitud: " + String.format("%.3f", latitud) +
                        "\n" + "Longitud: " + String.format("%.3f", longitud));

            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

           // Uri selectedImage = data.getData();

            Bundle extras = data.getExtras();

            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            byte[] b = baos.toByteArray();

            fotoPath = Base64.encodeToString(b, Base64.DEFAULT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                fotoArbol.setBackground(null);
            }
            fotoArbol.setImageBitmap(imageBitmap);
            //fotoPath = getPath(selectedImage);

        }

    }


    public void irMapa(View v) {

        location = utils.getLocation();
        if (location != null) {
            latitud = location.getLatitude();
            longitud = location.getLongitude();
            txtCoordenadas.append("\n" + "Latitud: " + String.format("%.3f", latitud) +
                    "\n" + "Longitud: " + String.format("%.3f", longitud));

        } else {
            Toast.makeText(Detalles.this, "Locatiom null", Toast.LENGTH_SHORT).show();
        }

    }

    public void tomarFoto(View v) throws IOException {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    public void guardarActividad(View v) throws ParseException, SQLException {

        comentariosActividad = edtComentarios.getText().toString();

        if (fotoPath.equals("")){
            Toast.makeText(Detalles.this, R.string.tomar_foto, Toast.LENGTH_SHORT).show();
        }else if(comentariosActividad.equals("")) {
            Toast.makeText(Detalles.this, "Llenar campos faltantes.", Toast.LENGTH_SHORT).show();
            edtComentarios.requestFocus();
        }else{
            validarDatos();
            volverALotes();
        }
    }

    private void volverALotes() {

        Intent i = new Intent(Detalles.this, Lotes.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                idActividad = 1;
                setActividad();
                layMapa.setVisibility(View.GONE);
                esconderVistas(false);
                break;
            case 1:
                idActividad = 2;
                setActividad();
                esconderVistas(true);
                cargarArboles();
                //Abonar
                break;
            case 2:
                idActividad = 3;
                setActividad();
                //Fertilizacion
                esconderVistas(true);
                cargarArboles();
                break;
            case 3:
                idActividad = 4;
                setActividad();
                //Control de Malezas
                esconderVistas(true);
                cargarArboles();
                break;

            case 4:
                idActividad = 5;
                setActividad();
                //Sustitucion de plantas
                esconderVistas(false);
                laySpinnerSaludArbol.setVisibility(View.GONE);
                layMapa.setVisibility(View.VISIBLE);
                cargarArboles();
                break;
            case 5:
                idActividad = 6;
                setActividad();
                //Preparar terreno
                esconderVistas(true);
                cargarArboles();
                break;
            case 6:
                idActividad = 7;
                setActividad();
                //Enfermedades
                lay_edt_altura.setVisibility(View.GONE);
                lay_edt_especie.setVisibility(View.VISIBLE);
                layMapa.setVisibility(View.VISIBLE);
                cargarArboles();
                break;
            case 7:
                idActividad = 8;
                setActividad();
                //Estado del arbol
                laySpinnerSaludArbol.setVisibility(View.VISIBLE);
                esconderVistas(true);
                cargarArboles();
                break;
        }
    }

    private void setActividad() {

        try {
            actividad = (Actividad) daoActividad.queryForId(idActividad);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void esconderVistas(boolean b) {

        if (b) {
            lay_edt_altura.setVisibility(View.GONE);
            lay_edt_especie.setVisibility(View.GONE);
            layMapa.setVisibility(View.VISIBLE);
            location = utils.getLocation();
            if (location != null){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));

            }

        } else {
            lay_edt_altura.setVisibility(View.VISIBLE);
            lay_edt_especie.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void validarDatos() throws SQLException {

        boolean b;

        if (idActividad != 1 && idArbol==0){
            Toast.makeText(Detalles.this, R.string.select_arbol, Toast.LENGTH_SHORT).show();
            if (idActividad == 5){
                guardarActividad5();
            }else if(idActividad == 7){ especie = edtEspecie.getText().toString();

                if (especie.equals("")) { edtEspecie.requestFocus();

                }else{  // guardar actividad 7 Enfermedades

                    guardarAct7();
                }
            }
            else if(idActividad == 8){

                arbolEstado = new ArbolEstado(arbol, estadoEntidad);
                daoArbolEstado.create(arbolEstado);
                guardarActividad_2_3_4_6_7_8();

            }
            guardarActividad_2_3_4_6_7_8();
        }else if (idActividad == 1) {

            especie = edtEspecie.getText().toString();
            altura = edtAltura.getText().toString();

            if (especie.equals("")) {

                edtEspecie.requestFocus();
            } else if (altura.equals("")) {

                edtAltura.requestFocus();
            }else{  //Actividad sembrar

                crearArbol();

            }
        }
    }

    private void guardarAct7() {

        especieEntidad = new Especie(especie);
        arbolEspecie = new ArbolEspecie(arbol, especieEntidad);

        try {
            daoEspecie.create(especieEntidad);
            daoArbolEspecie.create(arbolEspecie);
            guardarActividad_2_3_4_6_7_8();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarActividad5() {

        if (idArbol == 0){

            Toast.makeText(Detalles.this, R.string.select_arbol, Toast.LENGTH_SHORT).show();

        }else{

            setEntidadesAct1y5();
            guardarDaosAct1y5();
        }


    }

    private void guardarActividad_2_3_4_6_7_8() {



            try {
                daoDesarrolloAct.create(new DesarrolloActividades(fotoPath, comentariosActividad,
                        fechaActividad, actividad, arbol, null));
            } catch (SQLException e) {
                e.printStackTrace();
            }


    }

    private void crearArbol() throws SQLException {

        String coordenadas = String.valueOf(location.getLatitude()) + "," +
                String.valueOf(location.getLongitude());

        arbol = new Arbol(coordenadas, fechaActividad, lote);

        setEntidadesAct1y5();

        daoArboles.create(arbol);

        guardarDaosAct1y5();

    }

    private void setEntidadesAct1y5(){

        alturaEntity = new Altura(arbol, altura);
        especieEntidad = new Especie(especie);
        arbolEspecie = new ArbolEspecie(arbol, especieEntidad);
        desarrolloActividad = new DesarrolloActividades(fotoPath, comentariosActividad
                , fechaActividad, actividad, arbol, null);

    }

    private void guardarDaosAct1y5(){
        try {

            daoEspecie.create(especieEntidad);
            daoDesarrolloAct.create(desarrolloActividad);
            daoArbolEspecie.create(arbolEspecie);
            daoAltura.create(alturaEntity);

        }catch (SQLException r){r.printStackTrace();}
    }

    private void cargarArboles(){

        if (listaArboles.size() > 0){

            if (!arbolesCargados){
                CloseableWrappedIterable<Arbol> iterator = listaArboles.getWrappedIterable();

                for (Arbol arbol1 : iterator){

                    Marker marcadorArbol = mMap.addMarker(new MarkerOptions().position(arbol1.getPosicion()));
                    marcadorArbol.setTag(arbol1.getId());
                    // Location arbolLocation = getLocationString(arbol1);
                }
                arbolesCargados = true;
            }

        }else {

            Toast.makeText(Detalles.this, "No hay arboles sembrados", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.e("mapa", "listo");
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            onClickBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        idArbol = (Integer)marker.getTag();
        try {
            arbol = (Arbol)daoArboles.queryForId(idArbol);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Toast.makeText(Detalles.this, "Id Marker: " + String.valueOf(marker.getTag()), Toast.LENGTH_SHORT).show();

        return false;
    }

    private void onClickBack(){
        startActivity(new Intent(this,Lotes.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        OpenHelperManager.releaseHelper();
        utils.disConnect();
        super.onDestroy();
    }

}
