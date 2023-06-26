package com.example.whatsup.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;

@Entity
@TypeConverters(Converters.class)
public class Message implements Serializable {
    @PrimaryKey
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
    @NonNull
    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", contact='" + sender.getUsername() + '\'' +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                '}';
    }
}
