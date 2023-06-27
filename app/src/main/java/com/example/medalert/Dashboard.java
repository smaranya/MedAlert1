package com.example.medalert;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class Dashboard extends AppCompatActivity {

    public TextView username;
    Button addMedicine;

   ArrayList<String> id,name,time,dose,remaining, type;
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

        id = new ArrayList<>();
        name = new ArrayList<>();
        time = new ArrayList<>();
        type = new ArrayList<>();
        dose = new ArrayList<>();
        remaining = new ArrayList<>();

        storeAllData();
        customAdapter = new CustomAdapter(Dashboard.this,this,id,name,time,dose,remaining, type);
        recylerview.setAdapter(customAdapter);
        recylerview.setLayoutManager(new LinearLayoutManager(Dashboard.this));


    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            recreate();
        }
    }
    void storeAllData(){
        Cursor cursor = myDb.fetchUserByEmail(email);
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No Data.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                name.add(cursor.getString(2));
                dose.add(cursor.getString(5));
                type.add(cursor.getString(3));
                remaining.add(cursor.getString(6));
                time.add(cursor.getString(7));


            }

        }

    }

}