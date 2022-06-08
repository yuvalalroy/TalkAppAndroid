package com.example.talkappandroid;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;

@Entity
public class ContactItem {

    @PrimaryKey(autoGenerate=true)
    private int _id;
    private String _name;
    private String _lastMessage;
    private String _lastDate;
    private int _accountPic;

    public ContactItem() {}

    public ContactItem(int id, String name, String lastMessage, String lastDate) {
        this._name = name;
        this._id = id;
        this._lastMessage = lastMessage;
        this._lastDate = lastDate;
        this._accountPic = R.drawable.ic_avatar;

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

    public int get_accountPic() {
        return _accountPic;
    }

    public void set_accountPic(int _accountPic) {
        this._accountPic = _accountPic;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
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
