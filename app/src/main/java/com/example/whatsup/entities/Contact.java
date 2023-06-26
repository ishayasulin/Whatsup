package com.example.whatsup.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;

@Entity
@TypeConverters(Converters.class)
public class Contact implements Serializable {
    @PrimaryKey
    @NonNull
     private String id;
     private User user;
    private Message lastMessage;

    public Contact(String id, User user, Message lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Message getLastMessage() {
        return lastMessage;
    }


    @Override
    public String toString() {
        return "Contact{" +
                "id='" + id + '\'' +
                ", last='" + lastMessage + '\'' +
                '}';
    }
}
