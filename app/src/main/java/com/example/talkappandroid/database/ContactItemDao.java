package com.example.talkappandroid.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.talkappandroid.model.ContactItem;

import java.util.List;

@Dao
public interface ContactItemDao {

    @Query("SELECT * FROM contactitem WHERE userName=:username")
    List<ContactItem> getAllContacts(String username);

    @Query("SELECT * FROM contactitem WHERE id=:id")
    ContactItem get(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ContactItem... contactItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ContactItem> contactItems);

    @Update
    void update(ContactItem... contactItems);

    @Delete
    void delete(ContactItem... contactItems);

    @Query("DELETE FROM contactitem")
    public void clear();

}
