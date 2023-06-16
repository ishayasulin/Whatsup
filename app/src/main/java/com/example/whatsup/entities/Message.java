package com.example.whatsup.entities;


import java.io.Serializable;

public class Message implements Serializable {
    private int id;
    private String created;
    private Sender sender;
    private String content;


    public String getCreated() {
        return created;
    }

    public Message(int id, String created, Sender sender, String content) {
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public Sender getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }
}
