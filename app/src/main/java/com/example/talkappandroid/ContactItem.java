package com.example.talkappandroid;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity
public class ContactItem {

    @PrimaryKey(autoGenerate=true)
    private int _id;
    private String _lastMessage;
    private String _lastDate;

    public ContactItem() {}

    public ContactItem(int _id, String _lastMessage, String _lastDate) {
        this._id = _id;
        this._lastMessage = _lastMessage;
        this._lastDate = _lastDate;
    }

    public int get_id() {
        return _id;
    }

    public String get_lastMessage() {
        return _lastMessage;
    }

    public String get_lastDate() {
        return _lastDate;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_lastMessage(String _lastMessage) {
        this._lastMessage = _lastMessage;
    }

    public void set_lastDate(String _lastDate) {
        this._lastDate = _lastDate;
    }

    @Override
    public String toString() {
        return "ContactItem{" +
                "_id=" + _id +
                ", _lastMessage='" + _lastMessage + '\'' +
                ", _lastDate='" + _lastDate + '\'' +
                '}';
    }
}
