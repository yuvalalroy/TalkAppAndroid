package com.example.talkappandroid.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.talkappandroid.R;

@Entity
public class UserLogin {

    @PrimaryKey(autoGenerate=true)
    private String userName;
    private String password;

    public UserLogin() {}

    public UserLogin(String name, String password) {
        this.userName = name;
        this.password = password;
    }

}
