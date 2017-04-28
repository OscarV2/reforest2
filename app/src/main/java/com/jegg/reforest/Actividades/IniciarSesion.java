package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jegg.reforest.R;

public class IniciarSesion extends AppCompatActivity implements View.OnClickListener {
    private EditText cajaCorreo,cajaContraseña;
    private Button entrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
        cajaCorreo = (EditText) findViewById(R.id.tvCorreo);
        cajaContraseña = (EditText) findViewById(R.id.tvContraseña);
        entrar = (Button) findViewById(R.id.btn_entrar_iniciar);
        entrar.setOnClickListener(this);

    }

    public void iniciarSesion (){
        String correo = cajaCorreo.getText().toString();
        String contraseña = cajaContraseña.getText().toString();

        if (correo.equals("") || contraseña.equals("")){ // el usuario no ha digitado nada
            Toast.makeText(IniciarSesion.this, "LLENAR CAMPOS", Toast.LENGTH_SHORT).show(); // mostrar un mensaje
        }else {
            irMenu();
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
