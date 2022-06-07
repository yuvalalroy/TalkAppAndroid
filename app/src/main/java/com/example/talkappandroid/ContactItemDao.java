package com.example.talkappandroid;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactItemDao {

    @Query("SELECT * FROM contactitem")
    List<ContactItem> index();

    @Query("SELECT * FROM contactitem WHERE _id = :id")
    ContactItem get(int id);

    @Insert
    void insert(ContactItem... posts);

    @Update
    void update(ContactItem... posts);

    @Delete
    void delete(ContactItem... posts);

}
