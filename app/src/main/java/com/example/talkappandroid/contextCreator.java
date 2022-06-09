package com.example.talkappandroid;

import android.app.Application;

public class contextCreator extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppDB.initDB(getApplicationContext());
    }
}
