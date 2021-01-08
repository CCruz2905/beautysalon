package com.carlos.beautysalon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

/**
 * Clase para iniciar sesión, si el usuario no tiene cuenta, se puede registrar
 * @author: Carlos Cruz
 * @version: 1
 */

public class MainActivity extends AppCompatActivity {

    // Campos
    public EditText editTextEmail, editTextPassword;

    AwesomeValidation awesomeValidation;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obteniendo los campos
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // Validación correo
        awesomeValidation.addValidation(this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.error_email);

        // Validación contraseña
        awesomeValidation.addValidation(this, R.id.editTextPassword, ".{6,}", R.string.error_password);
    }

    // Métodos públicos
    // Método que valida el formulario
    public void buttonLogin(View view) {
        if (awesomeValidation.validate()) {
//            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, PrincipalMenu.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonSignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
}