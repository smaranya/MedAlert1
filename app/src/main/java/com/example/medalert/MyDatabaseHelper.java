package com.example.medalert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "MedAlert.db";
    private static final int DATABASE_VERSION = 2;



    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Medicine.CREATE_MED_TABLE);
        db.execSQL(User.CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+Medicine.TABLE_NAME);
        onCreate(db);
    }

    void addUser(String email, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(User.COLUMN_USER_EMAIL, email);
        cv.put(User.COLUMN_USER_NAME, username);
        cv.put(User.COLUMN_USER_PASSWORD, password);
        long result = db.insert(User.TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added User", Toast.LENGTH_SHORT).show();
        }
    }
    void addMedicine(String email, String medName, String medType, String medUnit, Integer dosage, Integer rem, String medTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Medicine.COLUMN_USER_EMAIL, email);
        cv.put(Medicine.COLUMN_MED_NAME, medName);
        cv.put(Medicine.COLUMN_MED_TYPE, medType);
        cv.put(Medicine.COLUMN_MED_UNIT, medUnit);
        cv.put(Medicine.COLUMN_DOSAGE_LENGTH, dosage);
        cv.put(Medicine.COLUMN_DOSAGE_PRESENT, rem);
        cv.put(Medicine.COLUMN_TIMING, medTime);
        long result = db.insert(Medicine.TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added Medicine", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor fetchUserByEmail(String email) {
        String query = "SELECT * FROM " + Medicine.TABLE_NAME + " WHERE " + Medicine.COLUMN_USER_EMAIL + " = '" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor = null;
       if(db!=null){
          cursor =  db.rawQuery(query,null);
       }
       return cursor;
    }


    void updateData(String id,int dose,int left ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        //content.put(Medicine.COLUMN_MED_ID,id);
        content.put(Medicine.COLUMN_DOSAGE_LENGTH,dose);
        content.put(Medicine.COLUMN_DOSAGE_PRESENT,left);


        long result = db.update(Medicine.TABLE_NAME,content,Medicine.COLUMN_MED_ID +" = "+id,null);
        if(result == -1){
            Toast.makeText(context,"Failed to Update Data",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Data Updated",Toast.LENGTH_SHORT).show();
        }
    }
}
