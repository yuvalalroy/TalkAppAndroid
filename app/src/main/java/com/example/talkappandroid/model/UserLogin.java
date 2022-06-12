package com.example.talkappandroid.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.talkappandroid.R;

@Entity
public class UserLogin {

    @PrimaryKey(autoGenerate=true)
    private String UserName;
    private String Password;

    public UserLogin() {}

    public UserLogin(String name, String password) {
        this.UserName = name;
        this.Password = password;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getPassword() { return Password; }

    public void setPassword(String password) { this.Password = password; }

    @Override
    public String toString() {
        return "UserLogin{" +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }

}
