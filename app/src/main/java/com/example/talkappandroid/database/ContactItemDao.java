package com.example.talkappandroid.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.talkappandroid.model.ContactItem;

import java.util.List;

@Dao
public interface ContactItemDao {

    @Query("SELECT * FROM contactitem")
    List<ContactItem> index();

    @Query("SELECT * FROM contactitem WHERE id = :id")
    ContactItem get(int id);

    @Insert
    void insert(ContactItem... contactItems);

    @Update
    void update(ContactItem... contactItems);

    @Delete
    void delete(ContactItem... contactItems);

}
