package com.example.talkappandroid.api;

import android.service.autofill.UserData;

import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.R;
import com.example.talkappandroid.TalkAppApplication;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.database.UserDao;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserRegister;
import com.example.talkappandroid.session.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private UserDao dao;
    Retrofit retrofit;
    webServiceAPI webServiceAPI;
    MutableLiveData<UserItem> userData;
    SessionManager sessionManager;

    public UserAPI(MutableLiveData<UserItem> userData, UserDao dao) {
        this.userData = userData;
        this.dao = dao;
        retrofit = new Retrofit.Builder()
            .baseUrl(TalkAppApplication.context.getString(R.string.BaseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        webServiceAPI = retrofit.create(webServiceAPI.class);
    }

    public UserAPI() {

    }

    public void get(MutableLiveData<UserItem> userItem) {
        Call<UserItem> call = webServiceAPI.getUser();
        call.enqueue(new Callback<UserItem>() {
            @Override
            public void onResponse(Call<UserItem> call, Response<UserItem> response) {

                userItem.setValue(response.body());

                new Thread(() -> {
                    dao.delete();
                    dao.insert(response.body());
                }).start();
            }

            @Override
            public void onFailure(Call<UserItem> call, Throwable t) {}
            });
    }

    public void postRegister(UserRegister postUser, MutableLiveData<Boolean> isLoggedIn) {

        Call<String> registerCall = webServiceAPI.registerPostUser(postUser);
        registerCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.isSuccessful()){
                    String token = response.body();
                    UserItem body = postUser.createUserWithToken(token);
                    sessionManager.saveSession(body);
                    isLoggedIn.postValue(true);
                } else {
                    sessionManager.removeSession();
                    isLoggedIn.postValue(false);
                }
                call.setValue(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                sessionManager.removeSession();
                isLoggedIn.postValue(false);
            }
        });
    }
}
