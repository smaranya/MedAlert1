package com.example.medalert;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Medicine {

    private SQLiteDatabase database;
    public static final String TABLE_NAME = "medicines";
    public static final String COLUMN_MED_ID = "_id";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_MED_NAME = "name";
    public static final String COLUMN_MED_TYPE = "type";
    public static final String COLUMN_MED_UNIT = "unit";
    public static final String COLUMN_DOSAGE_LENGTH = "dose";
    public static final String COLUMN_DOSAGE_PRESENT = "remaining";
    public static final String COLUMN_TIMING = "time";

    public static final String CREATE_MED_TABLE = "CREATE TABLE "+Medicine.TABLE_NAME+
            " ("+Medicine.COLUMN_MED_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            Medicine.COLUMN_USER_EMAIL+" TEXT ,"+
            Medicine.COLUMN_MED_NAME+" TEXT, "+
            Medicine.COLUMN_MED_TYPE+" TEXT, "+
            Medicine.COLUMN_MED_UNIT+" TEXT, "+
            Medicine.COLUMN_DOSAGE_LENGTH+" INTEGER, "+
            Medicine.COLUMN_DOSAGE_PRESENT+" INTEGER, "+
            Medicine.COLUMN_TIMING+" TEXT);";


}
