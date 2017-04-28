package com.jegg.reforest.Actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.jegg.reforest.R;

public class Detalles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
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
}
