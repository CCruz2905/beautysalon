package com.carlos.beautysalon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.beautysalon.backend.ConexionSQLiteHelper;
import com.carlos.beautysalon.backend.utils.Utilidades;

import java.util.ArrayList;
import java.util.Collections;

import static com.carlos.beautysalon.R.layout.custom_spinner;
import static java.lang.String.format;

public class ScheduleDate extends AppCompatActivity {

    // Constantes
    final int STARTHOUR = 9;
    final String[] SESSION_TYPE =  {"Aplicacion de pestañas", "Colorimetría del cabello", "Manicura", "Maquillaje y peinado"};

    // Campos
    public Spinner spinnerType, spinnerDate, spinnerTime;
    public TextView textViewType, textViewDate, textViewTime;

    // Adaptador para spinner
    ArrayList<String> spinnerArray;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_date);

        textViewType = findViewById(R.id.textViewType);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);

        // Colocar tipo de sesión
        fillTypes();
        // Colocar fechas en spinner
        fillDates();
        // Colocar horas en spinner
        fillTime();
    }

    // Métodos públicos
    // Método para guardar la fecha agendada
    public void buttonSave(View view) {
        if (spinnerValidations()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog);
            builder.setTitle(R.string.confirm_date);
            builder.setMessage(getDialogText());
            builder.setPositiveButton("Guardar", (dialog, which) -> {
                // Hacer cosas aqui al hacer clic en el boton de aceptar
                try {
                    long resultado = insertDate();
                    if (resultado != -1) {
                        Intent intent = new Intent(this, PrincipalMenu.class);
                        startActivity(intent);
                        Toast.makeText(this, "Cita guardada correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Ya existe una cita reservada a esa fecha y hora", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, e + "", Toast.LENGTH_LONG).show();
                }
            });
            builder.setNegativeButton("Atrás", (dialog, which) -> {

            });
            builder.show();
        }
    }

    private long insertDate() {
        SharedPreferences sharedPref = this.getSharedPreferences("correo_electronico", Context.MODE_PRIVATE);
        String email = sharedPref.getString(getString(R.string.email), "");

        String type = spinnerType.getSelectedItem().toString().trim();
        String date = spinnerDate.getSelectedItem().toString().trim() + " " + spinnerTime.getSelectedItem().toString().trim();

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_ID_EMAIL, email);
        values.put(Utilidades.CAMPO_TIPO_SESION, type);
        values.put(Utilidades.CAMPO_FECHA_CITA, date);

        long id = db.insert(Utilidades.TABLA_CITAS, Utilidades.CAMPO_ID_EMAIL, values);

        db.close();

        return id;
    }

    private String getDialogText() {
        String type = spinnerType.getSelectedItem().toString().trim();
        String date = spinnerDate.getSelectedItem().toString().trim();
        String time = spinnerTime.getSelectedItem().toString().trim();

        return "Sesión: " + type + "\n" +
                "Fecha: " + date + "\n" +
                "Hora: " + time;
    }

    private boolean spinnerValidations() {

        if (spinnerType.getSelectedItem().toString().trim().equals("<Selecciona un tipo de sesión>")) {
            textViewType.setVisibility(View.VISIBLE);
        } else {
            textViewType.setVisibility(View.GONE);
        }

        if (spinnerDate.getSelectedItem().toString().trim().equals("<Selecciona una fecha>")) {
            textViewDate.setVisibility(View.VISIBLE);
        } else {
            textViewDate.setVisibility(View.GONE);
        }

        if (spinnerTime.getSelectedItem().toString().trim().equals("<Selecciona una hora>")) {
            textViewTime.setVisibility(View.VISIBLE);
        } else {
            textViewTime.setVisibility(View.GONE);
        }

        return textViewType.getVisibility() != View.VISIBLE && textViewDate.getVisibility() != View.VISIBLE && textViewTime.getVisibility() != View.VISIBLE;
    }

    // Métodos privados
    // Método que llena los tipos de sesión
    private void fillTypes() {
        spinnerArray = new ArrayList<>();

        spinnerArray.add("<Selecciona un tipo de sesión>");

        Collections.addAll(spinnerArray, SESSION_TYPE);

        spinnerType = findViewById(R.id.spinnerType);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                custom_spinner,
                spinnerArray
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerType.setAdapter(adapter);

        /*
        spinnerType = findViewById(R.id.spinnerType);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinnerType.setAdapter(spinnerArrayAdapter);
        */
    }

    // Método que llena las fechas
    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillDates() {
        Calendar calendar = Calendar.getInstance();
        spinnerArray = new ArrayList<>();

        spinnerArray.add("<Selecciona una fecha>");

        int monthNumber = calendar.get(Calendar.MONTH);

        int i = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, 1);

        while (i < calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            String date = twoDigits(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + twoDigits(monthNumber + 1) + "/" + calendar.get(Calendar.YEAR);
            calendar.add(Calendar.DATE, 1);
            spinnerArray.add(date);
            i++;
        }

        spinnerDate = findViewById(R.id.spinnerDate);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                custom_spinner,
                spinnerArray
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerDate.setAdapter(adapter);

        /*
        spinnerDate = findViewById(R.id.spinnerDate);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinnerDate.setAdapter(spinnerArrayAdapter);
        */
    }

    // Método que llena las horas
    @SuppressLint("DefaultLocale")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void fillTime() {
        spinnerArray = new ArrayList<>();

        spinnerArray.add("<Selecciona una hora>");

        for (int i = STARTHOUR; i <= 20; i++) {
            String hour = format("%02d", i) + ":00 hrs";
            spinnerArray.add(hour);
        }

        spinnerTime = findViewById(R.id.spinnerTime);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                custom_spinner,
                spinnerArray
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerTime.setAdapter(adapter);

        /*
        spinnerTime = findViewById(R.id.spinnerTime);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinnerTime.setAdapter(spinnerArrayAdapter);
        */
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}