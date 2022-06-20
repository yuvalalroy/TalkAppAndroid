package com.example.talkappandroid.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.MessageItem;

import java.util.List;

@Dao
public interface MessageItemDao {

    @Query("SELECT * FROM messageitem WHERE contactID=:contactID")
    LiveData<List<MessageItem>> getAllMessages(String contactID);

    @Query("SELECT * FROM messageitem WHERE id = :id")
    MessageItem get(int id);

    @Insert
    void insert(MessageItem... messageItems);

    @Insert
    void insertAll(List<MessageItem> messageItems);

    @Update
    void update(MessageItem... messageItems);

    @Delete
    void delete(MessageItem... messageItems);

    @Query("DELETE FROM messageitem WHERE contactID=:contactID")
    public void clear(String contactID);

}
