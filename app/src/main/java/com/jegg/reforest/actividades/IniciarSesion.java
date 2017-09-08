package com.jegg.reforest.actividades;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.R;
import com.jegg.reforest.Utils.CerrarDialogo;
import com.jegg.reforest.Utils.MainActivityAux;
import com.jegg.reforest.controladores.DownloadUserData;

import java.sql.SQLException;
import java.util.List;

public class IniciarSesion extends AppCompatActivity implements View.OnClickListener, CerrarDialogo {

    private EditText cajaCorreo, cajaContraseña;

    private String password, correo;
    Dao<Persona, Integer>  daoPersonas;
    SharedPreferences prefs;
    DownloadUserData downloadData;
    Persona persona;
    public ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        cajaCorreo = (EditText) findViewById(R.id.tvCorreo);
        cajaContraseña = (EditText) findViewById(R.id.tvContraseña);
        Button entrar = (Button) findViewById(R.id.btn_entrar_iniciar);
        entrar.setOnClickListener(this);

        downloadData = new DownloadUserData(IniciarSesion.this, this);

        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        basededatos datosReforest = OpenHelperManager.getHelper(getApplicationContext(), basededatos.class);
        try {
            daoPersonas = datosReforest.getPersonasDao();
            List<Persona> lista= daoPersonas.queryForAll();
            if (lista.size() == 0){
                mostrarDialogo();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void iniciarSesion (){
        correo = cajaCorreo.getText().toString();
        password = cajaContraseña.getText().toString();

        if (correo.equals("") || password.equals("")){ // el usuario no ha digitado nada
            Toast.makeText(IniciarSesion.this, "LLENAR CAMPOS", Toast.LENGTH_SHORT).show(); // mostrar un mensaje
        }else {
            // hacer query para usuario
            consultarUsuario();
        }
    }

    private void consultarUsuario() {

        QueryBuilder<Persona, Integer> queryBuilder = daoPersonas.queryBuilder();
        Where where= queryBuilder.where();

        try {

            where.eq("correo", correo).and().eq("clave", password);

            PreparedQuery<Persona> preparedQuery = queryBuilder.prepare();
            List<Persona> userList = daoPersonas.query(preparedQuery);

            if (userList.size() == 0){
                Toast.makeText(this, "Correo invalido.", Toast.LENGTH_SHORT).show();
                cajaCorreo.requestFocus();
            }

            else {

                persona = userList.get(0);
                descargarDatos(persona);

            }

        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cerrardialogo() {

        dialog.dismiss();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("inicio_sesion", true);
        editor.putInt("id_persona", persona.getId());
        Log.e("id p sesion", String.valueOf(persona.getId()));
        editor.apply();
        editor.commit();
        // descargar lotes de esta persona
        irMenu();
    }

    private class TareaDescarga extends AsyncTask<Persona, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Persona... personas) {

                downloadData.descargar();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    private void descargarDatos(Persona persona) {

        dialog = ProgressDialog.show(IniciarSesion.this, "Sincronizando",
                "Por favor espere...", true, false);

        downloadData.setPersona(persona);

        TareaDescarga descargar = new TareaDescarga();
        descargar.execute(persona);
    }

    public void irMenu (){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
    }

    private void mostrarDialogo() {

        AlertDialog.Builder volver = new AlertDialog.Builder(IniciarSesion.this);
        volver.setTitle("Verificar Conexion")
                .setMessage("El inicio de sesion debe hacerse conectado a una red de internet. ")
                .setPositiveButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(IniciarSesion.this, MainActivity.class);
                        startActivity(i);
                        OpenHelperManager.releaseHelper();
                        finish();

                    }
                })
                .create();

        volver.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_entrar_iniciar:
                iniciarSesion();
                break;
        }
    }
}
