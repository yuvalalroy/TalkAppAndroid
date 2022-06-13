package com.example.talkappandroid.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;
import java.time.format.DateTimeFormatter;

@Entity
public class MessageItem {

    @PrimaryKey(autoGenerate=true)
    private int id;
    private String content;
    private String created;
    private boolean sent;

    public MessageItem() {}

    public MessageItem(int id, String content, String time, boolean sent) {
        this.id = id;
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


    @Override
    public String toString() {
        return "MessageItem{" +
                "_id=" + id +
                ", _content='" + content + '\'' +
                ", _time='" + created + '\'' +
                '}';
    }

}
