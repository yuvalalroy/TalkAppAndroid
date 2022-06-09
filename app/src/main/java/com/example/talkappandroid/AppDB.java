package com.example.talkappandroid;

import android.app.Activity;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContactItem.class}, version = 2)
public abstract class AppDB extends RoomDatabase {
    private static AppDB db = null;

    public static AppDB getInstance() {
        return db;
    }

    public static void initDB(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context, AppDB.class, "localDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    public abstract ContactItemDao contactItemDao();
}
