package com.carlos.beautysalon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Clase para iniciar sesión, si el usuario no tiene cuenta, se puede registrar
 * @author: Carlos Cruz
 * @version: 1
 */

public class MainActivity extends AppCompatActivity {

    // Constantes
    String RED = "red", WHITE = "white";

    // Campos
    public EditText editTextEmail, editTextPassword;
    public TextView textViewError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obteniendo los campos
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewError = findViewById(R.id.textViewError);

    }

    // Métodos públicos
    // Método que valida el formulario
    public void buttonLogin(View view) {
        if (validateForm()) {
            Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonSignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    // Métodos privados
    // Método que valida el formulario
    private boolean validateForm() {
        // Error si los campos de correo y constraseña están vacíos
        if (editTextEmail.getText().toString().isEmpty() && editTextPassword.getText().toString().isEmpty()) {
            textViewError.setText(getString(R.string.error_email_password));
            changeEditTextColor(editTextEmail, RED);
            changeEditTextColor(editTextPassword, RED);
            return false;

        // Error si el campo correo está vacío
        } else if (editTextEmail.getText().toString().isEmpty()) {
            textViewError.setText(getString(R.string.error_email));
            changeEditTextColor(editTextEmail, RED);
            changeEditTextColor(editTextPassword, WHITE);
            return false;

        // Error si el correo está mal escrito y la contraseña vacía
        } else if (validateEmail(editTextEmail.getText().toString()) && editTextPassword.getText().toString().isEmpty()) {
            textViewError.setText(getString(R.string.wrong_email_password));
            changeEditTextColor(editTextEmail, RED);
            changeEditTextColor(editTextPassword, RED);
            return false;

        // Error si el correo está mal escrito
        } else if (validateEmail(editTextEmail.getText().toString())) {
            textViewError.setText(getString(R.string.wrong_email));
            changeEditTextColor(editTextEmail, RED);
            changeEditTextColor(editTextPassword, WHITE);
            return false;

        // Error si la contraseña está vacía
        } else if (editTextPassword.getText().toString().isEmpty()) {
            textViewError.setText(getString(R.string.error_password));
            changeEditTextColor(editTextEmail, WHITE);
            changeEditTextColor(editTextPassword, RED);
            return false;

        // Si los campos están correctos
        } else {
            textViewError.setText("");
            changeEditTextColor(editTextEmail, WHITE);
            changeEditTextColor(editTextPassword, WHITE);
        }
        return true;
    }

    // Método para validar el correo del usuario
    private boolean validateEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return !pattern.matcher(email).matches();
    }

    // Método para cambiar el color a los campos
    private void changeEditTextColor(EditText field, String color) {
        if (color.equals("red")) {
            field.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.red));
        } else if (color.equals("white")) {
            field.setBackgroundTintList(AppCompatResources.getColorStateList(this, R.color.white));
        }
    }

}