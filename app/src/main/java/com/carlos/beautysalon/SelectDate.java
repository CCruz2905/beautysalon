package com.carlos.beautysalon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SelectDate extends AppCompatActivity {

    public TextView textViewTitle, textViewEmpty;
    public Spinner spinnerSelectDate;
    public Button buttonUpdateDate, buttonCancelDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewEmpty = findViewById(R.id.textViewEmpty);
        spinnerSelectDate = findViewById(R.id.spinnerSelectDate);
        buttonUpdateDate = findViewById(R.id.buttonUpdateDate);
        buttonCancelDate = findViewById(R.id.buttonCancelDate);
    }
}