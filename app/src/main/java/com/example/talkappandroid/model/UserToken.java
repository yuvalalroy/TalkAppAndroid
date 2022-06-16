package com.example.talkappandroid.model;

public class UserToken {

    private String token;
    private UserItem user;

    public UserToken(UserItem user, String token) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        token = token;
    }

    public UserItem getUser() {
        return user;
    }

    public void setUser(UserItem user) {
        user = user;
    }
}
