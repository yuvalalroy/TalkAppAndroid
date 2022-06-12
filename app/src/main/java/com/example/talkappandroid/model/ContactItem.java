package com.example.talkappandroid.model;
import android.content.Intent;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.talkappandroid.R;
import com.example.talkappandroid.activites.ChatActivity;

@Entity
public class ContactItem {

    @PrimaryKey(autoGenerate=true)
    private int id;
    private String name;
    private String lastMessage;
    private String lastDate;
    private int accountPic;

    public ContactItem() {}

    public ContactItem(int id, String name, String lastMessage, String lastDate) {
        this.name = name;
        this.id = id;
        this.lastMessage = lastMessage;
        this.lastDate = lastDate;
        this.accountPic = R.drawable.ic_avatar;

    }

    public int getId() {
        return id;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public int getAccountPic() {
        return accountPic;
    }

    public void setAccountPic(int accountPic) {
        this.accountPic = accountPic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ContactItem{" +
                "_id=" + id +
                ", _lastMessage='" + lastMessage + '\'' +
                ", _lastDate='" + lastDate + '\'' +
                '}';
    }

}
