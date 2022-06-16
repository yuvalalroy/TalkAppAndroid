package com.example.talkappandroid.api;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserLogin;
import com.example.talkappandroid.model.UserRegister;
import com.example.talkappandroid.model.UserToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface UserServiceAPI {
//
//    @GET("Users/{id}")
//    Call<UserItem> getUser();

    @POST("Users/register")
    Call<UserToken> registerPostUser(@Body UserItem user);

    @POST("Users/login")
    Call<UserToken> loginPostUser(@Body UserLogin user);
}
