package com.example.talkappandroid.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.talkappandroid.api.ContactAPI;
import com.example.talkappandroid.api.UserAPI;
import com.example.talkappandroid.database.AppDB;
import com.example.talkappandroid.database.ContactItemDao;
import com.example.talkappandroid.database.UserDao;
import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.UserItem;

import java.util.LinkedList;
import java.util.List;

public class UserRepository {

    private UserAPI api;
    private UserDao dao;
    private UserData userData;

    public UserRepository() {
        AppDB db = AppDB.getContactDBInstance();
        dao = db.userDao();
        api = new UserAPI(userData, dao);
    }

    class UserData extends MutableLiveData<UserItem> {
        public UserData() {
            super();
            setValue(null);
        }

        @Override
        public void onActive() {
            super.onActive();

            new Thread(() -> {
                userData.postValue(dao.get());
            }).start();

        }
    }

    public LiveData<List<ContactItem>> getAll() { return contactListData;}

    public void add(final UserItem userItem){
        //api.add(contactItem);
    }

    public void delete(final UserItem userItem){
        //api.delete(contactItem);
    }

    public void reload(){
        //api.get();
    }


}
