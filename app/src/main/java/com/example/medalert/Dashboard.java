package com.example.medalert;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    public TextView username;
    Button addMedicine;

   ArrayList<String> name,type,dose,unit,remaning,time;
   CustomAdapter customAdapter;
    String email = null;
    MyDatabaseHelper myDb  = new MyDatabaseHelper(Dashboard.this);


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addMedicine = findViewById(R.id.addMedicine);

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }

        username = findViewById(R.id.dash_user);
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        username.setText("Hello, "+email);
        RecyclerView recylerview = findViewById(R.id.recyclerView);
        addMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, AddMedicine.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
        name = new ArrayList<>();
        type = new ArrayList<>();
        dose = new ArrayList<>();

        remaning = new ArrayList<>();
        time = new ArrayList<>();
        storeAllData();
        customAdapter = new CustomAdapter(Dashboard.this,name,type,dose,time,remaning);
        recylerview.setAdapter(customAdapter);
        recylerview.setLayoutManager(new LinearLayoutManager(Dashboard.this));





        //Add a new medication


    }
    void storeAllData(){
        Cursor cursor = myDb.fetchUserByEmail(email);
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No Data.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                name.add(cursor.getString(2));
                type.add(cursor.getString(3));
                remaning.add(cursor.getString(6));
                time.add(cursor.getString(7));
                dose.add(cursor.getString(5));

            }

        }

    }
}