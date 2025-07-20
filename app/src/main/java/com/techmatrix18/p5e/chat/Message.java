package com.techmatrix18.p5e.chat;

public class Message {
    private String text;
    private boolean isFromUser;

    public Message(String text, boolean isFromUser) {
        this.text = text;
        this.isFromUser = isFromUser;
    }

    public String getText() { return text; }
    public boolean isFromUser() { return isFromUser; }
}

