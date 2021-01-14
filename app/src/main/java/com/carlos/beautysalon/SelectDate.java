package com.carlos.beautysalon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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

    public TextView textViewTitle, textViewEmpty, editTextSessionType, editTextDateTime, editTextDateScheduled, textView, textView2, textView3;
    public Spinner spinnerSelectDate;
    public Button buttonCancelDate;

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
        buttonCancelDate = findViewById(R.id.buttonCancelDate);
        editTextSessionType = findViewById(R.id.editTextSessionType);
        editTextDateTime = findViewById(R.id.editTextDateTime);
        editTextDateScheduled = findViewById(R.id.editTextDateScheduled);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);

        buttonCancelDate.setEnabled(false);

        if (checkDates()) {
            fillDates();

            ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                    this,
                    custom_spinner,
                    listaCitas
            );

            adaptador.setDropDownViewResource(R.layout.custom_spinner_dropdown);
            spinnerSelectDate.setAdapter(adaptador);

            spinnerSelectDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long idl) {

                    if (position != 0) {
                        buttonCancelDate.setEnabled(true);

                        textView.setVisibility(View.VISIBLE);
                        textView2.setVisibility(View.VISIBLE);
                        textView3.setVisibility(View.VISIBLE);
                        buttonCancelDate.setVisibility(View.VISIBLE);
                        editTextSessionType.setVisibility(View.VISIBLE);
                        editTextDateTime.setVisibility(View.VISIBLE);
                        editTextDateScheduled.setVisibility(View.VISIBLE);
                        buttonCancelDate.setVisibility(View.VISIBLE);

                        editTextSessionType.setText(citasList.get(position - 1).getSesion());
                        editTextDateTime.setText(citasList.get(position - 1).getFecha());
                        editTextDateScheduled.setText(citasList.get(position - 1).getFecha_agenda() + " hrs");
                    } else {
                        buttonCancelDate.setEnabled(false);

                        textView.setVisibility(View.INVISIBLE);
                        textView2.setVisibility(View.INVISIBLE);
                        textView3.setVisibility(View.INVISIBLE);
                        buttonCancelDate.setVisibility(View.INVISIBLE);
                        editTextSessionType.setVisibility(View.INVISIBLE);
                        editTextDateTime.setVisibility(View.INVISIBLE);
                        editTextDateScheduled.setVisibility(View.INVISIBLE);
                        buttonCancelDate.setVisibility(View.INVISIBLE);

                        editTextSessionType.setText("");
                        editTextDateTime.setText("");
                        editTextDateScheduled.setText("");
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } else {
            textViewEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void fillDates() {
        textViewTitle.setVisibility(View.VISIBLE);
        spinnerSelectDate.setVisibility(View.VISIBLE);

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
        String[] campos = { Utilidades.CAMPO_TIPO_SESION, Utilidades.CAMPO_FECHA_CITA, Utilidades.CAMPO_FECHA_AGENDA };

        try {
            // Select correo electrónico from usuario where correo electrónico =?
            Cursor cursor = db.query(Utilidades.TABLA_CITAS,
                    campos,
                    Utilidades.CAMPO_ID_EMAIL + " = ? ",
                    parametros,
                    null,
                    null,
                    Utilidades.CAMPO_FECHA_CITA);

            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()){
                    cita = new Cita();
                    cita.setSesion(cursor.getString(0));
                    cita.setFecha(cursor.getString(1));
                    cita.setFecha_agenda(cursor.getString(2));

                    Log.i("Sesión", cita.getSesion());
                    Log.i("Fecha", cita.getFecha());

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
            listaCitas.add(citasList.get(i).getSesion() + " - " + citasList.get(i).getFecha());
        }
    }

    public void eliminarUsuario(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog);
        builder.setTitle(R.string.are_sure);
        builder.setMessage(R.string.delete);
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {

        });
        builder.setPositiveButton(R.string.confirm, (dialog, which) -> {
            // Hacer cosas aqui al hacer clic en el boton de aceptar
            try {
                SQLiteDatabase db = conn.getWritableDatabase();
                String[] parametros = { editTextDateTime.getText().toString() };

                db.delete(Utilidades.TABLA_CITAS, Utilidades.CAMPO_FECHA_CITA + "=?", parametros);
                Toast.makeText(getApplicationContext(),"La cita fue cancelada", Toast.LENGTH_LONG).show();

                spinnerSelectDate.setSelection(0);

                Intent intent = getIntent();
                finish();
                startActivity(intent);

                db.close();
            } catch (Exception e) {
                Toast.makeText(this, e + "", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}