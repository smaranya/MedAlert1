package com.example.medalert;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AddMedicine extends AppCompatActivity  {
    Button addMed; EditText medName, dosageLength, rem; RadioGroup radioGroup;
    String name, type, timeInput, email;
    int dose, dosage, hour, minute;
    TimePicker timePicker;
    private int notifId = 1;

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

//        timePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(AddMedicine.this, "Set Alarm " + jam + " : " + menit, Toast.LENGTH_SHORT).show();
//                setTimer();
//                notification();
//            }
//        });

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
                if(Objects.equals(type, "Tablet")){
                    unit = "Pill";
                }
                else if(Objects.equals(type, "Syrup")){
                    unit = "ML";
                }

                MyDatabaseHelper myDB = new MyDatabaseHelper(AddMedicine.this);
                myDB.addMedicine(email, name, type, unit, dose, dosage, timeInput);
                Toast.makeText(AddMedicine.this, "Set Alarm " + hour + " : " + minute, Toast.LENGTH_SHORT).show();

                //To set the timer and the notification
                setTimer(hour,minute);
                notification();


                Toast.makeText(getApplicationContext(), "Added Prescription Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddMedicine.this,Dashboard.class);
                //Ignore the notifId
                intent.putExtra("notifID",notifId);

                //Email to re render the Dashboard.class cause it re renders the activity after adding a medication
                //Any other specific details about the medication can be sent to the notification through here
                intent.putExtra("email",email);

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

    private void setTimer(int hour,int minute) {

        AlarmManager alarmManager  = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Date date = new Date();

        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();

        cal_now.setTime(date);
        cal_alarm.setTime(date);

        cal_alarm.set(Calendar.HOUR_OF_DAY, hour);
        cal_alarm.set(Calendar.MINUTE, minute);
        cal_alarm.set(Calendar.SECOND, 0);

        if(cal_alarm.before(cal_now)){
            cal_alarm.add(Calendar.DATE, 1);
        }
        //When a notifciation is received this class is called upon
        Intent i = new Intent(AddMedicine.this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddMedicine.this, 0, i, PendingIntent.FLAG_IMMUTABLE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(),pendingIntent);

    }

    private void notification() {
    //Check for permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Alarm Reminders";
            String description = "Have your medications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel  = new NotificationChannel("Notify", name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}