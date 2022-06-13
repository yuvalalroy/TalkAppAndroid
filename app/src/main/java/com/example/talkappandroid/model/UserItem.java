package com.example.talkappandroid.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.talkappandroid.R;

import java.util.List;

@Entity
public class UserItem {

    @PrimaryKey(autoGenerate=true)
    private String UserName;
    private String Password;
    private String DisplayName;
    private List<ContactItem> Contacts;
    private String Token;
    private int ProfilePic;

    public UserItem() {}

    public UserItem(String name, String password, String displayName) {
        this.UserName = name;
        this.Password = password;
        this.DisplayName = displayName;
        this.ProfilePic = R.drawable.ic_avatar;
        this.Contacts = null;
        this.Token = null;
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

    public String getToken() { return this.Token; }

    public void setToken(String token) { this.Token = token; }

    @Override
    public String toString() {
        return "UserItem{" +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", DisplayName='" + DisplayName + '\'' +
                '}';
    }

}
