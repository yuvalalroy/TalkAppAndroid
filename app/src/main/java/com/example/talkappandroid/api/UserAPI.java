package com.example.talkappandroid.api;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.R;
import com.example.talkappandroid.TalkAppApplication;
import com.example.talkappandroid.database.UserDao;
import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserLogin;
import com.example.talkappandroid.model.UserRegister;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private static UserDao dao;
    private static UserAPI userApi;
    private Retrofit retrofit;
    private UserServiceAPI userServiceAPI;
    private UserTokenDB userTokenDB;
    private static final String BASE_URL = "http://10.0.2.2:7201/api/";



    private UserAPI(UserDao dao) {
        UserAPI.dao = dao;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .build();
        userServiceAPI = retrofit.create(UserServiceAPI.class);
        userTokenDB = UserTokenDB.getInstance();
    }

    public static UserAPI getInstance(UserDao dao) {
        if (userApi == null) {
            userApi = new UserAPI(dao);
        }
        return userApi;
    }

    public void postRegister(UserItem postUser, MutableLiveData<Boolean> isLoggedIn) {
        Call<String> registerCall = this.userServiceAPI.registerPostUser(postUser);
        registerCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()){
                    String token = response.body();
                    dao.insert(postUser);
                    userTokenDB.insertToEditor(postUser, token);
                    isLoggedIn.postValue(true);
                }
                else
                    isLoggedIn.postValue(false);
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                isLoggedIn.postValue(false);
            }});
    }

    public void postLogin(UserLogin loginUser, MutableLiveData<Boolean> isLoggedIn) {
        Call<String> loginCall = userServiceAPI.loginPostUser(loginUser);
        loginCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    if (Objects.equals(token, "Wrong password") || Objects.equals(token, "User does not exists"))
                        isLoggedIn.postValue(false);
                    else {
                        UserTokenDB.setToken(token);
                        //currentUser.postValue(userTokenDB.getFromEditor(token));
                        isLoggedIn.postValue(true);
                    }
                }
                else {
                    isLoggedIn.postValue(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                isLoggedIn.postValue(false);
            }
        });
    }
}
