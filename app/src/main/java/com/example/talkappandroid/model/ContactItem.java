package com.example.talkappandroid.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.talkappandroid.R;
import com.example.talkappandroid.database.UserTokenDB;

@Entity
public class ContactItem {


    @PrimaryKey(autoGenerate = true)
    private int identifier;
    private String id;
    private String name;
    private String last;
    private String lastdate;
    private String server;
    private String username;
    @Ignore
    private int accountPic;


    public ContactItem() {}

    public ContactItem(String id, String name, String lastMessage, String lastDate, String server) {
        this.name = name;
        this.id = id;
        this.last = lastMessage;
        this.lastdate = lastDate;
        this.server = server;
        this.accountPic = R.drawable.ic_avatar;
        this.username = UserTokenDB.getFromEditor(UserTokenDB.getToken()).getUserName();

    }

    public int getIdentifier() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServer() { return server; }

    public void setServer(String server) { this.server = server; }

    public String getId() {
        return id;
    }

    public String getLast() {
        return last;
    }

    public String getLastdate() {
        return lastdate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setLastdate(String lastdate) {
        this.lastdate = lastdate;
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
                "id=" + id +
                ",name=" + name +
                ",last=" + last +
                ",lastDate=" + lastdate  +
                ",server=" + server +
                '}';
    }

}
