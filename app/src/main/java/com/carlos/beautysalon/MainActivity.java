package com.carlos.beautysalon;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.carlos.beautysalon.backend.ConexionSQLiteHelper;
import com.carlos.beautysalon.backend.utils.Utilidades;

/**
 * Clase para iniciar sesión, si el usuario no tiene cuenta, se puede registrar
 * @author: Carlos Cruz
 * @version: 1
 */

public class MainActivity extends AppCompatActivity {

    // Campos
    public EditText editTextEmail, editTextPassword;

    AwesomeValidation awesomeValidation;
    ConexionSQLiteHelper conn;
    SQLiteDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obteniendo los campos
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        awesomeValidation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        awesomeValidation.setContext(this);
        awesomeValidation.setUnderlabelColor(ContextCompat.getColor(this, android.R.color.holo_red_light));

        // Validación correo
        awesomeValidation.addValidation(this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.error_email);

        // Validación contraseña
        awesomeValidation.addValidation(this, R.id.editTextPassword, ".{6,}", R.string.error_password);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);
    }

    // Métodos públicos
    // Método que valida el formulario
    public void buttonLogin(View view) {
        String email, password;
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if (awesomeValidation.validate()) if (checkEmail(email)) {
            if (checkEmailPassword(email, password)) {
                SharedPreferences sharedPref = this.getSharedPreferences("correo_electronico", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.email), email);
                editor.apply();

                Intent intent = new Intent(this, PrincipalMenu.class);
                startActivity(intent);
            }
        }
    }

    public void buttonSignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public Boolean checkEmail(String email) {
        db = conn.getReadableDatabase();
        String[] parametros = { email };
        String[] campos = { Utilidades.CAMPO_NOMBRE };

        try {
            // Select correo electrónico from usuario where correo electrónico =?
            Cursor cursor = db.query(Utilidades.TABLA_USUARIO,
                    campos,
                    Utilidades.CAMPO_ID_EMAIL + " = ? ",
                    parametros,
                    null,
                    null,
                    null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                SharedPreferences sharedPref = this.getSharedPreferences("nombre", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.first_name), cursor.getString(0));
                editor.apply();

                cursor.close();
                return true;
            } else {

                editTextPassword.setText("");

                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog);
                builder.setTitle(R.string.email_not_found);
                builder.setMessage(email + " no coincide con ninguna cuenta existente. Puedes crear una cuenta para acceder.");
                builder.setNegativeButton(R.string.try_again, (dialog, which) -> {

                });
                builder.setPositiveButton(R.string.sign_up, (dialog, which) -> {
                    // Hacer cosas aqui al hacer clic en el boton de aceptar
                    Intent intent = new Intent(this, SignUp.class);
                    startActivity(intent);
                });
                builder.show();
            }
        } catch (Exception e) {

            Toast.makeText(this, e + "", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = conn.getReadableDatabase();

        try {
            // Select correo electrónico from usuario where correo electrónico =?
            Cursor cursor = db.rawQuery("Select " + Utilidades.CAMPO_ID_EMAIL + ", " + Utilidades.CAMPO_PASSWORD +
                    " from " + Utilidades.TABLA_USUARIO
                    + " where " + Utilidades.CAMPO_ID_EMAIL + " = ?  and " + Utilidades.CAMPO_PASSWORD + "= ?",
                    new String[] { email, password });

            if (cursor.getCount() > 0) {
                return true;

            } else {

                editTextPassword.setText("");
                cursor.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialog);
                builder.setTitle(R.string.email_not_match);
                builder.setMessage("La contraseña no coincide con la cuenta registrada.");
                builder.setNegativeButton(R.string.try_again, (dialog, which) -> {

                });
                builder.show();
            }
        } catch (Exception e) {

            Toast.makeText(this, e + "", Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}