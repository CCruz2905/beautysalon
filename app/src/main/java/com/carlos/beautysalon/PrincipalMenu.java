package com.carlos.beautysalon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

/**
 * Clase que muestra el menú principal para usuarios registrados
 * @author: Carlos Cruz
 * @version: 1
 */
public class PrincipalMenu extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_menu);

    }

    // Métodos públicos
    // Método que dirige a agendar cita
    public void buttonPlanDate(View view) {
        Intent intent = new Intent(this, ScheduleDate.class);
        startActivity(intent);
    }

    // Método que dirige a cancelar cita
    public void buttonCancelDate(View view) {
        Intent intent = new Intent(this, SelectDate.class);
        startActivity(intent);
    }

    // Método que dirige a salir de la sesión
    public void buttonExit(View view) {

    }

    // Método que dirige a la información del salón
    public void buttonAboutMe(View view) {

    }
}