package com.example.talkappandroid.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.talkappandroid.R;

@Entity
public class UserRegister {

    @PrimaryKey(autoGenerate=true)
    private String UserName;
    private String Password;
    private String DisplayName;
    private int ProfilePic;

    public UserRegister() {}

    public UserRegister(String name, String password, String displayName) {
        this.UserName = name;
        this.Password = password;
        this.DisplayName = displayName;
        this.ProfilePic = R.drawable.ic_avatar;
    }
//
//    public UserItem createUserWithToken(String token) {
//        return new UserItem(this.UserName, this.Password, this.DisplayName);
//    }
}
