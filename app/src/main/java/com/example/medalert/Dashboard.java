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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

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
        RecyclerView recylerview = findViewById(R.id.recyclerView);
        name = new ArrayList<>();
        type = new ArrayList<>();
        dose = new ArrayList<>();
        unit = new ArrayList<>();
        remaning = new ArrayList<>();
        time = new ArrayList<>();

        storeAllData();
        recylerview.setAdapter(customAdapter);
        recylerview.setLayoutManager(new LinearLayoutManager(Dashboard.this));
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        username.setText("Hello, "+email);



        //Add a new medication
        addMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, AddMedicine.class);
                intent.putExtra("email", email);

                startActivity(intent);
            }
        });

    }
    void storeAllData(){
        Cursor cursor = myDb.fetchUserByEmail(email);
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No Data.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                name.add(cursor.getString(0));
                type.add(cursor.getString(0));
                unit.add(cursor.getString(0));
                remaning.add(cursor.getString(0));
                time.add(cursor.getString(0));
                dose.add(cursor.getString(0));

            }

        }

    }
}