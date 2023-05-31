package com.example.medalert;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dashboard extends AppCompatActivity {

    public TextView username;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        username = findViewById(R.id.dash_user);
        Bundle bundle = getIntent().getExtras();
        String user_name = bundle.getString("username");
        String email = bundle.getString("email");
        if(user_name == null)
        {
            username.setText("Hello, user of email: "+email);
        }
        else{
            username.setText("Hello, "+ user_name);
        }


    }
}