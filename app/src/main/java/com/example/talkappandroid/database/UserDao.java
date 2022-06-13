package com.example.talkappandroid.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.talkappandroid.model.ContactItem;
import com.example.talkappandroid.model.UserItem;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM useritem")
    List<UserItem> index();

    @Query("SELECT * FROM useritem WHERE UserName = :name")
    UserItem get(String name);

    @Insert
    void insert(UserItem... userItems);

    @Update
    void update(UserItem... userItems);

    @Delete
    void delete(UserItem... userItems);

}
