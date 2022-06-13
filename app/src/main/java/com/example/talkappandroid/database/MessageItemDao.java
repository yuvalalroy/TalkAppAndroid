package com.example.talkappandroid.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.talkappandroid.model.MessageItem;

import java.util.List;

@Dao
public interface MessageItemDao {

    @Query("SELECT * FROM messageitem")
    List<MessageItem> index();

    @Query("SELECT * FROM messageitem WHERE id = :id")
    MessageItem get(int id);

    @Insert
    void insert(MessageItem... messageItems);

    @Update
    void update(MessageItem... messageItems);

    @Delete
    void delete(MessageItem... messageItems);

}
