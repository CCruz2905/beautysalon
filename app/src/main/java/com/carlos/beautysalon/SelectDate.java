package com.carlos.beautysalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.carlos.beautysalon.backend.ConexionSQLiteHelper;
import com.carlos.beautysalon.backend.models.Cita;
import com.carlos.beautysalon.backend.utils.Utilidades;

import java.util.ArrayList;

import static com.carlos.beautysalon.R.layout.custom_spinner;

public class SelectDate extends AppCompatActivity {

    public TextView textViewTitle, textViewEmpty;
    public Spinner spinnerSelectDate;
    public Button buttonUpdateDate, buttonCancelDate;

    ConexionSQLiteHelper conn;

    ArrayList<String> listaCitas;
    ArrayList<Cita> citasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        spinnerSelectDate = findViewById(R.id.spinnerSelectDate);
        buttonUpdateDate = findViewById(R.id.buttonUpdateDate);
        buttonCancelDate = findViewById(R.id.buttonCancelDate);

        if (checkDates()) {
            fillDates();
        } else {
            textViewEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void fillDates() {
        textViewTitle.setVisibility(View.VISIBLE);
        spinnerSelectDate.setVisibility(View.VISIBLE);
        buttonUpdateDate.setVisibility(View.VISIBLE);
        buttonCancelDate.setVisibility(View.VISIBLE);

        spinnerSelectDate = findViewById(R.id.spinnerSelectDate);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                custom_spinner,
                listaCitas
        );
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        spinnerSelectDate.setAdapter(adapter);
    }

    private boolean checkDates() {
        SQLiteDatabase db = conn.getReadableDatabase();

        Cita cita;
        citasList = new ArrayList<>();

        SharedPreferences sharedPref = this.getSharedPreferences("correo_electronico", Context.MODE_PRIVATE);
        String email = sharedPref.getString(getString(R.string.email), "");

        String[] parametros = { email };
        String[] campos = { Utilidades.CAMPO_TIPO_SESION, Utilidades.CAMPO_FECHA_CITA, Utilidades.CAMPO_HORA_CITA };

        try {
            // Select correo electrónico from usuario where correo electrónico =?
            Cursor cursor = db.query(Utilidades.TABLA_CITAS,
                    campos,
                    Utilidades.CAMPO_ID_EMAIL + " = ? ",
                    parametros,
                    null,
                    null,
                    null);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()){
                    cita = new Cita();
                    cita.setSesion(cursor.getString(0));
                    cita.setFecha(cursor.getString(1));
                    cita.setHora(cursor.getString(2));

                    Log.i("Sesión", cita.getSesion());
                    Log.i("Fecha", cita.getFecha());
                    Log.i("Hora", cita.getHora());

                    citasList.add(cita);
                    
                    getList();

                }
                cursor.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void getList() {
        listaCitas = new ArrayList<>();
        listaCitas.add("<Seleccione una fecha>");

        for(int i=0; i < citasList.size(); i++){
            listaCitas.add(citasList.get(i).getSesion() + " - " + citasList.get(i).getFecha() + "-" + citasList.get(i).getHora());
        }
    }
}