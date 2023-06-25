package com.example.medalert;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText dose, remaining;
    Button update,delete;
    String  id,dosage,left,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        dose = findViewById(R.id.dose2);
        remaining = findViewById(R.id.remaining);
        update = findViewById(R.id.update);
        delete =  findViewById(R.id.delete);
        getandSetIntentData();


        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper db = new MyDatabaseHelper(UpdateActivity.this);
                dosage = dose.getText().toString().trim();
                left = remaining.getText().toString().trim();
                db.updateData(id,Integer.parseInt(dosage),Integer.parseInt(left));
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }



   public void getandSetIntentData(){
        if(getIntent().hasExtra("_id")&&getIntent().hasExtra("dose")&& getIntent().hasExtra("remaining")){
            //Get Intent Data
            id = getIntent().getStringExtra("_id");
            dosage = getIntent().getStringExtra("dose");
            left  = getIntent().getStringExtra("remaining");
            name  = getIntent().getStringExtra("name");
            //Set Intent Data
            dose.setText(dosage);
            remaining.setText(left);
        }else{
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Are you sure you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}