package com.example.talkappandroid;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ContactItem.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract ContactItemDao contactItemDao();
}
