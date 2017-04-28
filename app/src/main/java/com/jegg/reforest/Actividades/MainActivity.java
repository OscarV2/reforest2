package com.jegg.reforest.Actividades;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jegg.reforest.Actividades.IniciarSesion;
import com.jegg.reforest.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.btn_acceso_main)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IniciarSesion();
            }
        });

    }


    public void IniciarSesion(){
        Intent intent = new Intent(this, IniciarSesion.class);
        startActivity(intent);
        finish();
    }
}
