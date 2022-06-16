package com.example.talkappandroid.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.MessageItem;
import com.example.talkappandroid.model.UserItem;

@Database(entities = {ContactItem.class, MessageItem.class, UserItem.class}, version = 3)
public abstract class AppDB extends RoomDatabase {
    private static AppDB contactDB = null;
    private static AppDB messageDB = null;
    private static AppDB usersDB = null;

    public static AppDB getContactDBInstance() {
        return contactDB;
    }
    public static AppDB getMessageDBInstance() { return messageDB; }
    public static AppDB getUsersDBInstance() { return usersDB; }

    public static void initDB(Context context) {
        if (contactDB == null) {
            contactDB = Room.databaseBuilder(context, AppDB.class, "localDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        if (messageDB == null) {
            messageDB = Room.databaseBuilder(context, AppDB.class, "msgLocalDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        if (usersDB == null) {
            usersDB = Room.databaseBuilder(context, AppDB.class, "userLocalDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
    }

    public abstract ContactItemDao contactItemDao();
    public abstract MessageItemDao messageItemDao();
    public abstract UserDao userDao();
}
