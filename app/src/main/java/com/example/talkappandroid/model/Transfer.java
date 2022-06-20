package com.example.talkappandroid.model;

public class Transfer {

    private String from;
    private String to;
    private String content;

    public Transfer(String from, String to, String server) {
        this.from = from;
        this.to = to;
        this.content = server;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
