package com.carlos.beautysalon;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.carlos.beautysalon.dialog.DatePickerFragment;

public class SignUp extends AppCompatActivity {

    public EditText editTextFirstName, editTextLastName, editTextDOB, editTextEmail, editTextPassword1, editTextPassword2, editTextPhone;

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