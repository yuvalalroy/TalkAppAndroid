package com.example.talkappandroid.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class MessageContent {

    private String content;

    public MessageContent() {}

    public MessageContent(String content) {
        this.content = content;
    }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

}
