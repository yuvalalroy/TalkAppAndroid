package com.example.talkappandroid.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserItem {

    @PrimaryKey
    @NonNull
    private String userName;
    private String password;
    private String displayName;
    private String profilePic;

    public UserItem() {}

    public UserItem(String name, String password, String displayName) {
        this.userName = name;
        this.password = password;
        this.displayName = displayName;
        this.profilePic = null;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDisplayName() { return displayName; }

    public String getPassword() { return password; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public void setPassword(String password) { this.password = password; }


    @Override
    public String toString() {
        return "UserItem{" +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", displayName='" + displayName + '\'' +
                ", profilePic='" + profilePic + '\'' +
                '}';
    }

}
