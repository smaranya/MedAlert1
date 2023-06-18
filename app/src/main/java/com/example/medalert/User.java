package com.example.medalert;

public class User {
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_NAME = "username";

    public static final String CREATE_USER_TABLE = "CREATE TABLE "+User.TABLE_NAME+
            " ("+User.COLUMN_USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            User.COLUMN_USER_EMAIL+" TEXT "+
            User.COLUMN_USER_NAME+" TEXT, "+
            User.COLUMN_USER_PASSWORD+" INTEGER);";
}
