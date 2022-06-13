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


    public int getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(int profilePic) {
        this.ProfilePic = profilePic;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getDisplayName() { return DisplayName; }

    public String getPassword() { return Password; }

    public void setDisplayName(String displayName) { this.DisplayName = displayName; }

    public void setPassword(String password) { this.Password = password; }

    @Override
    public String toString() {
        return "ContactItem{" +
                ", _name='" + UserName + '\'' +
                ", _password='" + Password + '\'' +
                ", _diaplayName='" + DisplayName + '\'' +
                '}';
    }

    public UserItem createUserWithToken(String token) {
        UserItem user = new UserItem(this.UserName, this.Password, this.DisplayName);
        user.setToken(token);
        return user;
    }
}
