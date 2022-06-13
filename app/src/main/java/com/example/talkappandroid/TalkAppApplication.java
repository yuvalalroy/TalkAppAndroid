package com.example.talkappandroid;

import android.app.Application;
import android.content.Context;

import com.example.talkappandroid.database.AppDB;

public class TalkAppApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AppDB.initDB(context);
    }
}
