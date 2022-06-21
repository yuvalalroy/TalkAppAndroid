package com.example.talkappandroid.api;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.database.UserTokenDB;
import com.example.talkappandroid.utils.FirebaseToken;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserLogin;
import com.example.talkappandroid.model.UserToken;
import com.google.gson.GsonBuilder;

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




    private UserAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(UserTokenDB.SERVER_URL)
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

    public void notifyFirebaseToServer(FirebaseToken firebaseToken){
        Call<Void> notifyCall = userServiceAPI.registerDevice("Bearer " + UserTokenDB.getToken(), firebaseToken);
        notifyCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
            }
        });
    }
}
