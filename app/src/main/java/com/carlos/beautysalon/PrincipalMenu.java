package com.carlos.beautysalon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

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

        // Colocar fechas en spinner
//        Calendar calendar = Calendar.getInstance();
//        ArrayList<String> spinnerArray = new ArrayList<String>();
//
//        for (int i = calendar.get(calendar.DAY_OF_MONTH); i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
//            System.out.println(calendar.getTime());
//            calendar.add(calendar.DATE, 1);
//            spinnerArray.add(calendar.get(calendar.DAY_OF_MONTH) + "/" + "enero");
//        }
//
//        spinner = findViewById(R.id.spinner);
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
//        spinner.setAdapter(spinnerArrayAdapter);

    }

    // Métodos públicos
    // Método que dirige a agendar cita
    public void buttonPlanDate(View view) {

    }

    // Método que dirige a cancelar cita
    public void buttonCancelDate(View view) {

    }

    // Método que dirige a configurar usuario
    public void buttonUpdateUser(View view) {

    }

    // Método que dirige a salir de la sesión
    public void buttonExit(View view) {

    }

    // Método que dirige a la información del salón
    public void buttonAboutMe(View view) {

    }
}