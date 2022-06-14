package com.example.talkappandroid.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.api.UserAPI;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.UserDao;
import com.example.talkappandroid.model.UserItem;
import com.example.talkappandroid.model.UserLogin;
import com.example.talkappandroid.model.UserRegister;

public class UserRepository {

    private UserAPI userApi;
    private UserDao dao;

    public UserRepository() {
        AppDB db = AppDB.getUsersDBInstance();
        this.dao = db.userDao();
        this.userApi = UserAPI.getInstance(dao);
    }

    public void login(UserLogin loginUser, MutableLiveData<Boolean> isLoggedIn) {
        this.userApi.postLogin(loginUser, isLoggedIn);
    }

    public void register(UserItem registerUser, MutableLiveData<Boolean> isLoggedIn) {
        this.userApi.postRegister(registerUser, isLoggedIn);
    }
}
