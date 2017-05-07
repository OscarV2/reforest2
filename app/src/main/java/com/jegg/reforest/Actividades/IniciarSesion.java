package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.jegg.reforest.Entidades.Lote;
import com.jegg.reforest.Entidades.Persona;
import com.jegg.reforest.Entidades.User;
import com.jegg.reforest.R;
import com.jegg.reforest.Utils.Constantes;
import com.jegg.reforest.asincronas.GetAsyncrona;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class IniciarSesion extends AppCompatActivity implements View.OnClickListener {

    private EditText cajaCorreo,cajaContraseña;
    private Button entrar;

    private Gson gsonPersona;
    private User usuario;
    private basededatos datosReforest;
    private String password, correo;
    Dao  daoUsers, daoPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        cajaCorreo = (EditText) findViewById(R.id.tvCorreo);
        cajaContraseña = (EditText) findViewById(R.id.tvContraseña);
        entrar = (Button) findViewById(R.id.btn_entrar_iniciar);
        entrar.setOnClickListener(this);
        datosReforest = OpenHelperManager.getHelper(getApplicationContext(), basededatos.class);
        try {
            daoUsers = datosReforest.getUserDao();
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
          //  irMenu();
        }
    }

    private void consultarUsuario() {

        QueryBuilder<User, Integer> queryBuilder = daoUsers.queryBuilder();

        Where where= queryBuilder.where();

        try {

            where.eq("email", correo).and().eq("password", password);

            PreparedQuery<User> preparedQuery = queryBuilder.prepare();
            List<User> userList = daoUsers.query(preparedQuery);

            if (userList.size() == 0){
                Toast.makeText(this, "Correo o contraseña invalidos.", Toast.LENGTH_SHORT).show();
                cajaContraseña.requestFocus();
            }else {
                // usuario valido
                usuario = userList.get(0);
                Log.e("id ususario", String.valueOf(usuario.getId()));
                Log.e("id persona usuario", String.valueOf(usuario.getIdPersona()));

                // hacer get de persona asociada al usuario
                GetAsyncrona getAsyncPersona = new GetAsyncrona();
                String jsonPersona = getAsyncPersona.execute(Constantes.GET_PERSONA +
                        String.valueOf(usuario.getIdPersona())).get();
                Log.e("json persona", jsonPersona);
                irMenu();
                /*
                Persona persona = gsonPersona.fromJson(jsonPersona,Persona.class);
                if (persona != null){
                    daoPersonas.create(persona);

                }
*/


            }

        } catch (SQLException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void irALotes() {

        Intent intent = new Intent(this, Lotes.class);
        startActivity(intent);
        finish();

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
