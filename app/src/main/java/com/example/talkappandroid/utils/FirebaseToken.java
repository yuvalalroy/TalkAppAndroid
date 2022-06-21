package com.example.talkappandroid.utils;

import com.google.gson.annotations.SerializedName;

public class FirebaseToken {
    @SerializedName("deviceId")
    public String token;

    public FirebaseToken(String token) {
        this.token = token;
    }
}
