package com.carlos.beautysalon;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

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

        // Variable que aplicará las validaciones
        awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        awesomeValidation.setContext(this);
        awesomeValidation.setUnderlabelColor(ContextCompat.getColor(this, android.R.color.holo_red_light));

        // Validación nombre y apellido
        awesomeValidation.addValidation(this, R.id.editTextFirstName, RegexTemplate.NOT_EMPTY, R.string.error_name);
        awesomeValidation.addValidation(this, R.id.editTextLastName, RegexTemplate.NOT_EMPTY, R.string.error_name);
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
        awesomeValidation.addValidation(this, R.id.editTextPhone, ".{10,}", R.string.error_phone);
    }

    // Métodos públicos
    public void buttonSignUp(View view) {
        if (awesomeValidation.validate()) {
            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }
    }
}