package com.example.talkappandroid.api;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.R;
import com.example.talkappandroid.TalkAppApplication;
import com.example.talkappandroid.database.UserDao;
import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserLogin;
import com.example.talkappandroid.model.UserRegister;
import com.example.talkappandroid.model.UserToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private static UserAPI userApi;
    private Retrofit retrofit;
    private UserServiceAPI userServiceAPI;
    private UserTokenDB userTokenDB;
    private static final String BASE_URL = "http://10.0.2.2:7201/api/";



    private UserAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .build();
        userServiceAPI = retrofit.create(UserServiceAPI.class);
        userTokenDB = UserTokenDB.getInstance();
    }

    public static UserAPI getInstance() {
        if (userApi == null) {
            userApi = new UserAPI();
        }
        return userApi;
    }

    public void postRegister(UserItem postUser, MutableLiveData<Boolean> isLoggedIn) {
        Call<UserToken> registerCall = this.userServiceAPI.registerPostUser(postUser);
        registerCall.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(@NonNull Call<UserToken> call, @NonNull Response<UserToken> response) {
                if(response.isSuccessful()){
                    isLoggedIn.postValue(true);
                }
                else
                    isLoggedIn.postValue(false);
            }
            @Override
            public void onFailure(@NonNull Call<UserToken> call, @NonNull Throwable t) {
                isLoggedIn.postValue(false);
            }});
    }

    public void postLogin(UserLogin loginUser, MutableLiveData<Boolean> isLoggedIn) {
        Call<UserToken> loginCall = this.userServiceAPI.loginPostUser(loginUser);
        loginCall.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(@NonNull Call<UserToken> call, @NonNull Response<UserToken> response) {
                if (response.isSuccessful()) {
                    UserToken userToken = response.body();
                    if(UserTokenDB.checkIfExists(userToken.getToken())){
                        userTokenDB.insertToEditor(loginUser, userToken.getToken());
                    }
                    UserTokenDB.setToken(userToken.getToken());
                    isLoggedIn.postValue(true);
                } else {
                    isLoggedIn.postValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserToken> call, @NonNull Throwable t) {
                isLoggedIn.postValue(false);
            }
        });
    }
}
