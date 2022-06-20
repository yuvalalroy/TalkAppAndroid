package com.example.talkappandroid;

import android.app.Application;
import android.content.Context;

import com.example.talkappandroid.api.ContactAPI;
import com.example.talkappandroid.api.MessageAPI;
import com.example.talkappandroid.api.UserAPI;
import com.example.talkappandroid.database.AppDB;

public class TalkAppApplication extends Application {
    public static Context context;
    public static UserAPI usersApi;
    public static ContactAPI contactsApi;
    public static MessageAPI messageApi;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AppDB.initDB(context);
        usersApi = UserAPI.getInstance();
        contactsApi = ContactAPI.getInstance();
        messageApi = MessageAPI.getInstance();
    }
}
