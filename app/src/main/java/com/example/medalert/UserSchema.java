package com.example.medalert;

public class UserSchema {
    private String username;
    private String email;

    public UserSchema(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}


