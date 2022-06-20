package com.example.talkappandroid.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class MessageItem {

    @PrimaryKey(autoGenerate=true)
    private int id;
    private String content;
    private String created;
    private boolean sent;
    private String contactID;

    public MessageItem() {}

    public MessageItem(String content, String time, boolean sent) {
        this.content = content;
        this.created = time;
        this.sent = sent;
    }

    public int getId() {
        return id;
    }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getCreated() { return created; }

    public void setCreated(String created) { this.created = created; }

    public boolean getSent() { return sent; }

    public void setSent(boolean sent) { this.sent = sent; }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }
}
