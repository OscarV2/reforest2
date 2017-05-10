package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.jegg.reforest.DBdatos.basededatos;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.R;

import java.sql.SQLException;
import java.util.List;

public class IniciarSesion extends AppCompatActivity implements View.OnClickListener {

    private EditText cajaCorreo,cajaContraseña;
    private Button entrar;

    private Persona usuario;
    private basededatos datosReforest;
    private String password, correo;
    Dao  daoPersonas;

    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        cajaCorreo = (EditText) findViewById(R.id.tvCorreo);
        cajaContraseña = (EditText) findViewById(R.id.tvContraseña);
        entrar = (Button) findViewById(R.id.btn_entrar_iniciar);
        entrar.setOnClickListener(this);

        prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);

        datosReforest = OpenHelperManager.getHelper(getApplicationContext(), basededatos.class);
        try {
            daoPersonas = datosReforest.getPersonasDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
                SharedPreferences.Editor editor = prefs.edit();
                usuario = userList.get(0);
                editor.putBoolean("inicio_sesion", true);
                editor.putInt("id_persona", usuario.getId());
                editor.apply();
                // usuario valido
                irMenu();
            }

        } catch (SQLException  e) {
            e.printStackTrace();
        }
    }

    public void irMenu (){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
        finish();
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
