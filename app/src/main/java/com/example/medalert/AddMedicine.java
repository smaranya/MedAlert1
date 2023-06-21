package com.example.medalert;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMedicine extends AppCompatActivity {
    Button addMed; EditText medName, dosageLength, rem; RadioGroup radioGroup;
    String name, type, timeInput, email;
    int dose, dosage, hour, minute;
    TimePicker timePicker;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        email = getIntent().getStringExtra("email");
        addMed = findViewById(R.id.submitMed);
        medName = findViewById(R.id.medName);

        dosageLength = findViewById(R.id.dose);

        rem = findViewById(R.id.dosage);

        timePicker = findViewById(R.id.time);
        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                type = radioButton.getText().toString();
                Toast.makeText(AddMedicine.this, "Selected option: " + type, Toast.LENGTH_SHORT).show();
            }
        });

        addMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unit = "";
                name = medName.getText().toString();
                dose = Integer.parseInt(dosageLength.getText().toString());
                dosage = Integer.parseInt(rem.getText().toString());
                hour = timePicker.getHour();
                minute = timePicker.getMinute();
                timeInput = formatTime(hour, minute);
                if(type == "Tablet"){
                    unit = "Pill";
                }
                else if(type == "Syrup"){
                    unit = "ML";
                }

                MyDatabaseHelper myDB = new MyDatabaseHelper(AddMedicine.this);
                myDB.addMedicine(email, name, type, unit, dose, dosage, timeInput);
                Toast.makeText(getApplicationContext(), "Added Prescription Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddMedicine.this,Dashboard.class);
                startActivity(intent);
            }
        });

    }
    private String formatTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
}