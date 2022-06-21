package com.example.talkappandroid.api;

import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserLogin;
import com.example.talkappandroid.model.UserToken;
import com.example.talkappandroid.utils.FirebaseToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;


public interface UserServiceAPI {

    @POST("Users/register")
    Call<UserToken> registerPostUser(@Body UserItem user);

    @POST("Users/login")
    Call<UserToken> loginPostUser(@Body UserLogin user);

    @POST("contacts/registerDevice")
    Call<Void> registerDevice(@Header("Authorization") String token, @Body FirebaseToken firebaseToken);
}
