package com.carlos.beautysalon;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.carlos.beautysalon.backend.ConexionSQLiteHelper;
import com.carlos.beautysalon.backend.utils.Utilidades;
import com.carlos.beautysalon.dialog.DatePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Clase para registrar usuario
 * @author: Carlos Cruz
 * @version: 1
 */
public class SignUp extends AppCompatActivity {

    // Campos
    public EditText editTextFirstName, editTextLastName, editTextDOB, editTextEmail, editTextPassword1, editTextPassword2, editTextPhone;

    // Objeto para realizar validaciones
    AwesomeValidation awesomeValidation;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Obteniendo los campos
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmailAddress);
        editTextDOB = findViewById(R.id.editTextDOB);
        editTextPassword1 = findViewById(R.id.editTextPassword1);
        editTextPassword2 = findViewById(R.id.editTextPassword2);
        editTextPhone = findViewById(R.id.editTextPhone);

        addValidations();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addValidations() {
        // Variable que aplicará las validaciones
        awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        awesomeValidation.setContext(this);
        awesomeValidation.setUnderlabelColor(ContextCompat.getColor(this, android.R.color.holo_red_light));

        // Validación nombre y apellido
        awesomeValidation.addValidation(this, R.id.editTextFirstName, "[A-zÀ-ú]+", R.string.error_name);
        awesomeValidation.addValidation(this, R.id.editTextLastName, "[A-zÀ-ú]+" , R.string.error_name);
        // Validación correo
        awesomeValidation.addValidation(this, R.id.editTextEmailAddress, Patterns.EMAIL_ADDRESS, R.string.error_email);
        // Validación fecha de nacimiento
        awesomeValidation.addValidation(this, R.id.editTextDOB, input -> {
            // check if the age is >= 16
            try {
                Calendar calendarBirthday = Calendar.getInstance();
                Calendar calendarToday = Calendar.getInstance();
                calendarBirthday.setTime(new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(input));
                int yearOfToday = calendarToday.get(Calendar.YEAR);
                int yearOfBirthday = calendarBirthday.get(Calendar.YEAR);
                if (yearOfToday - yearOfBirthday > 16) {
                    return true;
                } else if (yearOfToday - yearOfBirthday == 16) {
                    int monthOfToday = calendarToday.get(Calendar.MONTH);
                    int monthOfBirthday = calendarBirthday.get(Calendar.MONTH);
                    if (monthOfToday > monthOfBirthday) {
                        return true;
                    } else if (monthOfToday == monthOfBirthday) {
                        if (calendarToday.get(Calendar.DAY_OF_MONTH) >= calendarBirthday.get(Calendar.DAY_OF_MONTH)) {
                            return true;
                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }, R.string.error_year);
        // Validación contraseña
        awesomeValidation.addValidation(this, R.id.editTextPassword1, ".{6,}", R.string.error_password);
        awesomeValidation.addValidation(this, R.id.editTextPassword2, R.id.editTextPassword1, R.string.wrong_password);
        // Validación teléfono
        awesomeValidation.addValidation(this, R.id.editTextPhone, "\\d{10}", R.string.error_phone);
    }

    // Métodos públicos
    public void buttonSignUp(View view) {
        if (awesomeValidation.validate()) {
//            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
            try {
                long resultado = insertUser();
                if (resultado != -1) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "Usuario registrado!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, e + "", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Revisa los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private long insertUser() {
        // insert into usuario (email, password, nombre, apellido, fec_nac, telefono) values ('carloscruzg295@gmail.com', 'contraseña', 'Carlos','Cruz', '29/05/1998', '8442347661')

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ID_EMAIL, editTextEmail.getText().toString());
        values.put(Utilidades.CAMPO_PASSWORD, editTextPassword1.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE, editTextFirstName.getText().toString());
        values.put(Utilidades.CAMPO_APELLIDO, editTextLastName.getText().toString());
        values.put(Utilidades.CAMPO_FEC_NAC, editTextDOB.getText().toString());
        values.put(Utilidades.CAMPO_TELEFONO, editTextPhone.getText().toString());

        long id = db.insert(Utilidades.TABLA_USUARIO, Utilidades.CAMPO_ID_EMAIL, values);

        db.close();

        return id;
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            // +1 because January is zero
            final String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
            editTextDOB.setText(selectedDate);
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}