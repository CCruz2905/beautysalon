package com.carlos.beautysalon;

import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.carlos.beautysalon.dialog.DatePickerFragment;

public class SignUp extends AppCompatActivity {

    public EditText editTextFirstName, editTextLastName, editTextDOB, editTextEmail, editTextPassword1, editTextPassword2, editTextPhone;

    AwesomeValidation awesomeValidation;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmailAddress);
        editTextDOB = findViewById(R.id.editTextDOB);
        editTextPassword1 = findViewById(R.id.editTextPassword1);
        editTextPassword2 = findViewById(R.id.editTextPassword2);
        editTextPhone = findViewById(R.id.editTextPhone);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // Validación nombre y apellido
        awesomeValidation.addValidation(this, R.id.editTextFirstName, RegexTemplate.NOT_EMPTY, R.string.error_name);
        awesomeValidation.addValidation(this, R.id.editTextLastName, RegexTemplate.NOT_EMPTY, R.string.error_name);
        // Validación correo
        awesomeValidation.addValidation(this, R.id.editTextEmailAddress, Patterns.EMAIL_ADDRESS, R.string.error_email);
        // Validación fecha de nacimiento
        awesomeValidation.addValidation(this, R.id.editTextDOB, RegexTemplate.NOT_EMPTY, R.string.error_year);
        // Validación contraseña
        awesomeValidation.addValidation(this, R.id.editTextPassword1, ".{6,}", R.string.error_password);
        awesomeValidation.addValidation(this, R.id.editTextPassword2, R.id.editTextPassword1, R.string.wrong_password);
        // Validación teléfono
        awesomeValidation.addValidation(this, R.id.editTextPhone, RegexTemplate.TELEPHONE, R.string.error_phone);
    }

    public void buttonSignUp(View view) {
        if (awesomeValidation.validate()) {
            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para capturar fecha de nacimiento
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            // +1 because January is zero
            final String selectedDate = twoDigits(day) + "/" + twoDigits(month+1) + "/" + year;
            editTextDOB.setText(selectedDate);
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // Método para ver los días y meses en 2 dígitos
    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}