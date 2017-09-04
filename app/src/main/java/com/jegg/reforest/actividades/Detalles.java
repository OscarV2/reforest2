package com.jegg.reforest.actividades;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
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
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.R;
import com.jegg.reforest.Utils.Constantes;
import com.jegg.reforest.Utils.DetallesAux;
import com.jegg.reforest.controladores.ControllerEspecies;
import com.jegg.reforest.Utils.LastLocationReady;
import com.jegg.reforest.Utils.LocationUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Detalles extends AppCompatActivity implements OnMapReadyCallback,
        AdapterView.OnItemSelectedListener, GoogleMap.OnMarkerClickListener,
        LastLocationReady {

    EditText edtFecha, edtComentarios, edtAltura, edtBuscarArbol;
    AutoCompleteTextView edtEspecie;
    ImageView fotoArbol;
    TextView txtCoordenadas, txtLote;
    Spinner actividades, saludArbol;

    int idActividad, idEstado;
    String idLote;
    String nombreLote, idArbol = "";
    String comentariosActividad;
    String altura;
    String especie;
    File filePath;
    private GoogleMap mMap;


    RelativeLayout layCordenadas, layBuscarArbol;
    LinearLayout lay_edt_especie, lay_edt_altura,
            laySpinnerSaludArbol, layMapa;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    double latitud, longitud;
    SimpleDateFormat sdf;
    String fotoPath;
    boolean arbolesCargados = false;

    int idPersona, numArboles;
    String fechaActividad;
    Lote lote;
    Arbol arbol;

    PolylineOptions options;

    Altura alturaEntity;

    Actividad actividad;
    DesarrolloActividades desarrolloActividad;

    List<Estado> estados;
    ArbolEstado arbolEstado;

    Especie especieEntidad;
    ArbolEspecie arbolEspecie;

    Location location;
    LocationUtils utils;
    SharedPreferences prefs;
    Persona usuario;
    List<Arbol> listaArboles;

    private DetallesAux detallesAux;
    private boolean ButonLocationPressed;
    ControllerEspecies controllerEspecies;
    Marker miPosicionMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        setToolbar();
        utils = new LocationUtils(Detalles.this);
        utils.setLastLocation(this);
        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        basededatos datosReforest = OpenHelperManager.getHelper(Detalles.this, basededatos.class);

        idLote = this.getIntent().getStringExtra("id_lote");
        idPersona = prefs.getInt("id_persona", 0);
        numArboles = this.getIntent().getIntExtra("num_arboles", 0);

        detallesAux = new DetallesAux(Detalles.this);
        try {
            Dao<Lote, String> daoLotes = datosReforest.getLoteDao();
            lote = daoLotes.queryForId(idLote);
            nombreLote = lote.getNombre();

            listaArboles = lote.getArboles();
            usuario = detallesAux.getPersona(idPersona);
            Log.e("idPersona", String.valueOf(usuario.getId()));
            Log.e("persona clave", usuario.getClave());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<LatLng> recLote = lote.getPuntos();
        options = new PolylineOptions();
        options.color(Color.GREEN);
        options.add(recLote.get(0));
        options.add(recLote.get(1));
        options.add(recLote.get(2));
        options.add(recLote.get(3));
        options.add(recLote.get(0));
        init();
    }

    private void init() {

        lay_edt_altura = (LinearLayout) findViewById(R.id.lay_edt_altura);
        lay_edt_especie = (LinearLayout) findViewById(R.id.lay_edt_especie);
        laySpinnerSaludArbol = (LinearLayout) findViewById(R.id.linear_spiner_salud_arbol);
        layMapa = (LinearLayout) findViewById(R.id.lay_mapa);
        layCordenadas = (RelativeLayout) findViewById(R.id.lay_cordenadas);
        layBuscarArbol = (RelativeLayout) findViewById(R.id.lay_buscar_arbol);

        edtComentarios = (EditText) findViewById(R.id.comentario_detalles);
        edtEspecie = (AutoCompleteTextView) findViewById(R.id.especieArbol);
        edtFecha = (EditText) findViewById(R.id.FechaActividadArbol);
        edtAltura = (EditText) findViewById(R.id.alturaArbol);
        edtBuscarArbol = (EditText) findViewById(R.id.edt_numero_arbol);

        txtCoordenadas = (TextView) findViewById(R.id.CoordenadasArbol);
        fotoArbol = (ImageView) findViewById(R.id.foto_arbol);

        actividades = (Spinner) findViewById(R.id.spinner_detalle_actividad);
        saludArbol = (Spinner) findViewById(R.id.spinner_salud_arbol);

        txtLote = (TextView) findViewById(R.id.nombre_lote);
        txtLote.append(nombreLote);

        controllerEspecies = new ControllerEspecies(this);
        ArrayAdapter<String> especieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                controllerEspecies.getListaEspecies());

        edtEspecie.setAdapter(especieAdapter);

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaActividad = Constantes.sdf.format(new Date());
        edtFecha.setText(sdf.format(new Date()));

        filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.lista_tipo_actividades, R.layout.item_spinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        actividades.setAdapter(adapter);
        actividades.setOnItemSelectedListener(this);

        try {
            estados = detallesAux.getListEstados();
            Log.e("estados size", String.valueOf(estados.size()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                        break;
                    case 1:
                        idEstado = 2;
                        break;
                    case 2:
                        idEstado = 3;
                        break;
                    case 3:
                        idEstado = 4;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                idEstado = 1;
            }
        });

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

            try {

                Bundle extras = data.getExtras();

                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                assert imageBitmap != null;
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                byte[] b = baos.toByteArray();

                fotoPath = Base64.encodeToString(b, Base64.DEFAULT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

                    fotoArbol.setBackground(null);
                }
                fotoArbol.setImageBitmap(imageBitmap);

            } catch (Exception e) {

                Toast.makeText(this, "No se pudo tomar la foto. Por favor intentelo de nuevo.", Toast.LENGTH_SHORT).show();
            }
            edtComentarios.requestFocus();
        }
    }

    public void irMapa(View v) {

        location = utils.getLocation();
        if (!ButonLocationPressed) {

            if (location != null) {
                latitud = location.getLatitude();
                longitud = location.getLongitude();
                txtCoordenadas.append("\n" + "Latitud: " + String.format("%.3f", latitud) +
                        "\n" + "Longitud: " + String.format("%.3f", longitud));

                miPosicionMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                miPosicionMarker.setTitle("Nuevo Arbol!");
                miPosicionMarker.showInfoWindow();
                ButonLocationPressed = true;

            } else {
                Toast.makeText(Detalles.this, "Locatiom null", Toast.LENGTH_SHORT).show();
            }
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

        if (fotoPath.equals("")) {
            Toast.makeText(Detalles.this, R.string.tomar_foto, Toast.LENGTH_SHORT).show();
        } else if (comentariosActividad.equals("")) {
            Toast.makeText(Detalles.this, "Llenar campos faltantes.", Toast.LENGTH_SHORT).show();
            edtComentarios.requestFocus();
        } else {
            validarDatos();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                //Preparar terreno y sembrar
                idActividad = 1;
                setActividad();
                esconderVistas(false);
                if (location != null) {

                    initMap();
                }

                Log.e("position 0", "position 0");
                laySpinnerSaludArbol.setVisibility(View.GONE);
                layCordenadas.setVisibility(View.VISIBLE);
                layBuscarArbol.setVisibility(View.GONE);
                break;
            case 1:
                idActividad = 2;
                setActividad();
                //Abonar
                esconderVistas(true);
                laySpinnerSaludArbol.setVisibility(View.GONE);
                cargarArboles();
                break;
            case 2:
                idActividad = 3;
                setActividad();
                //Control de Malezas
                esconderVistas(true);
                laySpinnerSaludArbol.setVisibility(View.GONE);
                cargarArboles();
                break;

            case 3:
                idActividad = 4;
                setActividad();
                //Fertilizacion
                esconderVistas(true);
                laySpinnerSaludArbol.setVisibility(View.GONE);
                cargarArboles();
                break;
            case 4:
                idActividad = 5;
                setActividad();
                //Sustitucion de plantas
                esconderVistas(false);
                layMapa.setVisibility(View.VISIBLE);
                laySpinnerSaludArbol.setVisibility(View.GONE);
                layBuscarArbol.setVisibility(View.VISIBLE);
                cargarArboles();
                break;
            case 5:
                idActividad = 6;
                setActividad();
                //Enfermedades
                lay_edt_altura.setVisibility(View.GONE);
                lay_edt_especie.setVisibility(View.VISIBLE);
                layMapa.setVisibility(View.VISIBLE);
                laySpinnerSaludArbol.setVisibility(View.GONE);
                layCordenadas.setVisibility(View.GONE);
                layBuscarArbol.setVisibility(View.VISIBLE);
                cargarArboles();
                break;
            case 6:
                idActividad = 7;
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
            actividad = detallesAux.getActividad(idActividad);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (idActividad != 1) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            if (miPosicionMarker.isVisible()){

                miPosicionMarker.remove();
            }

        }
    }

    private void esconderVistas(boolean b) {

        layCordenadas.setVisibility(View.GONE);
        if (b) {
            lay_edt_altura.setVisibility(View.GONE);
            lay_edt_especie.setVisibility(View.GONE);
            layBuscarArbol.setVisibility(View.VISIBLE);
            location = utils.getLocation();
            if (location != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));

            }

        } else {
            lay_edt_altura.setVisibility(View.VISIBLE);
            lay_edt_especie.setVisibility(View.VISIBLE);
            location = utils.getLocation();
            if (location != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));

            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void validarDatos() throws SQLException {

        if (idActividad == 5 || idActividad == 1) {  // sustitucion de plantas

            Log.e("actividad", String.valueOf(idActividad));
            especie = edtEspecie.getText().toString();
            altura = edtAltura.getText().toString();

            if (especie.equals("")) {

                edtEspecie.requestFocus();
            } else if (altura.equals("")) {
                edtAltura.requestFocus();
            } else if (!controllerEspecies.checkEspecie(especie)) { //no coincide la especie
                Toast.makeText(this, "La especie no existe.", Toast.LENGTH_SHORT).show();
            } else {
                if (idActividad == 5) {
                    Log.e("guardando", "actividad 6 o 2");
                    guardarActividad5();
                } else {   // idActividad = 1
                    if (location == null) {
                        Toast.makeText(this, "No hay coordenadas para preparar terreno.", Toast.LENGTH_SHORT).show();
                    } else {
                        prepararTerreno();
                    }
                }
            }
        } else if (idActividad == 6) {
            especie = edtEspecie.getText().toString();  // Enfermedad Arbol

            if (especie.equals("")) {
                edtEspecie.requestFocus();

            } else {  // guardar actividad 6 Enfermedades

                guardarAct6();
            }
        } else if (idActividad == 7) {     // Estado Arbol

            Log.e("actividad es", "EStado");
            arbolEstado = new ArbolEstado(arbol, estados.get(idEstado - 1));
            detallesAux.crearEstado(arbolEstado);
            detallesAux.guardarActividad_2_3_4_6_7_8(fotoPath, comentariosActividad,
                    fechaActividad, actividad, arbol, usuario);

        } else {         // actividades 2,3, 4

            detallesAux.guardarActividad_2_3_4_6_7_8(fotoPath, comentariosActividad,
                    fechaActividad, actividad, arbol, usuario);
        }
    }//else {Toast.makeText(Detalles.this, R.string.select_arbol, Toast.LENGTH_SHORT).show();}  //y no se ha dado click en ningun terreno

    private void guardarAct6() throws SQLException {   // Enfermedades

        especieEntidad = detallesAux.getEspecie(especie);
        if (especieEntidad == null) {

            Toast.makeText(this, "La especie no existe.", Toast.LENGTH_SHORT).show();
        }

        arbolEspecie = new ArbolEspecie(arbol, especieEntidad);

        try {

            detallesAux.crearArbolEspecie(arbolEspecie);
            detallesAux.guardarActividad_2_3_4_6_7_8(fotoPath, comentariosActividad,
                    fechaActividad, actividad, arbol, usuario);
            volverALotes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarActividad5() {     //Resiembra

        if (idArbol.equals("") || (arbol == null)) {

            Toast.makeText(Detalles.this, R.string.select_arbol, Toast.LENGTH_SHORT).show();
        } else {

            especieEntidad = detallesAux.getEspecie(especie);

            if (especieEntidad == null) {
                Toast.makeText(this, "La especie no existe.", Toast.LENGTH_SHORT).show();
            } else {

                alturaEntity = new Altura(arbol, altura);
                arbolEspecie = new ArbolEspecie(arbol, especieEntidad);
                Log.e("guardando", "actividad 2 o 6");
                Log.e("idActividad", String.valueOf(actividad.getId()));

                arbolEstado = new ArbolEstado(arbol, estados.get(3));

                desarrolloActividad = new DesarrolloActividades(fotoPath, comentariosActividad
                        , fechaActividad, actividad, arbol, usuario);
                try {
                    detallesAux.createEntitiesActivity6(desarrolloActividad, arbolEspecie,
                            alturaEntity, arbolEstado);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                volverALotes();
            }
        }
    }

    private void prepararTerreno() throws SQLException {

        String coordenadas = String.valueOf(latitud) + " " +
                String.valueOf(longitud);

        arbol = new Arbol(coordenadas, fechaActividad, lote);
        arbol.setNumArbol(numArboles + 1);
        arbolEstado = new ArbolEstado(arbol, estados.get(0));
        alturaEntity = new Altura(arbol, altura);
        especieEntidad = detallesAux.getEspecie(especie);
        arbolEspecie = new ArbolEspecie(arbol, especieEntidad);
        if (especieEntidad == null) {

            Toast.makeText(this, "La especie no existe.", Toast.LENGTH_SHORT).show();
        }else{

            detallesAux.crearArbol(arbol);
            detallesAux.crearEstado(arbolEstado);
            detallesAux.crearArbolEspecie(arbolEspecie);
            detallesAux.crearAltura(alturaEntity);
            detallesAux.guardarActividad_2_3_4_6_7_8(fotoPath, comentariosActividad,
                    fechaActividad, actividad, arbol, usuario);
            volverALotes();
        }
    }

    private void cargarArboles() {

        if (listaArboles.size() > 0) {

            if (!arbolesCargados) {

                for (Arbol arbol1 : listaArboles) {

                    Marker marcadorArbol = mMap.addMarker(new MarkerOptions().position(arbol1.getPosicion()));
                    marcadorArbol.setTag(arbol1.getId());
                }

                arbolesCargados = true;
                if (location != null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18f));
                }
            }
        } else {

            Toast.makeText(Detalles.this, "No se ha preparado el terreno", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.e("mapa", "listo");
        mMap = googleMap;
        UiSettings mUiSettings;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (!(utils.gpsEnabled())) {
            mostrarDialogoGps();
        }
        // dibujar lote
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onClickBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initMap() {

        mMap.clear();
        mMap.addPolyline(options);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(false);
            miPosicionMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
            miPosicionMarker.setTag("MiPosicion");
            miPosicionMarker.setTitle("¡Aquí estoy!");
            miPosicionMarker.showInfoWindow();
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 19f));

        } else {

            Toast.makeText(this, "Por favor habilite el permiso de ubicacion para seguir usando la aplicación.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        idArbol = (String) marker.getTag();
        assert idArbol != null;
        if (!idArbol.equals("MiPosicion")) {


            try {
                arbol = detallesAux.getArbol(idArbol);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            Toast.makeText(Detalles.this, "Arbol Seleccionado", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void buscarArbol(View v) {

        // verificar que el dato sea numerico y que no se pase del numero de arboles del lote
        String numeroArbolCadena = edtBuscarArbol.getText().toString();
        int numArbolBuscar = Integer.parseInt(numeroArbolCadena);

        if (numArbolBuscar > numArboles) {

            Toast.makeText(this, "Arbol " + String.valueOf(numArbolBuscar) + " no existe.", Toast.LENGTH_SHORT).show();
        } else {

            try {
                arbol = detallesAux.getArbol(numArbolBuscar, idLote);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            dibujarArbolBusqueda(arbol, numArbolBuscar);
        }
    }

    private void dibujarArbolBusqueda(Arbol arbol, int numArbolBuscar) {

        mMap.clear();
        mMap.addPolyline(options);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        Marker marker =  mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .position(arbol.getPosicion()));

        int sizeListaEspecies = arbol.getArbolEspecie().size();
        Especie especieA = arbol.getArbolEspecie().get(sizeListaEspecies-1).getEspecie();
        Estado estadoA = arbol.getLastEstado();

        marker.setTag(arbol.getId());
        marker.setTitle(especieA.getNombre() + " " + String.valueOf(numArbolBuscar));
        marker.setSnippet("Estado: " + estadoA.getNombre());
        marker.showInfoWindow();

    }

    private void onClickBack(){
        startActivity(new Intent(this,Lotes.class));
        finish();
    }

    private void mostrarDialogoGps() {

        AlertDialog.Builder volver = new AlertDialog.Builder(Detalles.this);
        volver.setTitle("Verificar Servicio de Localizacion.")
                .setMessage("Por favor habilite la ubicacion de su dispositivo.")
                .setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(Detalles.this, CrearLote.class);
                        startActivity(i);
                        finish();

                    }
                })
                .create();

        volver.show();
    }

    @Override
    protected void onDestroy() {
        detallesAux.releaseHelper();
        utils.disConnect();
        super.onDestroy();
    }

    private void volverALotes() {

        Intent i = new Intent(Detalles.this, Lotes.class);
        startActivity(i);
        finish();

    }

    private void setToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detalles);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar !=null){
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back);
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
    public void locationReady(Location location2) {

        if (idActividad == 1 && (!ButonLocationPressed)){

            location = location2;
            initMap();
        }
    }
}