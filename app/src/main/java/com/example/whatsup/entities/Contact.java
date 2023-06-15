package com.example.whatsup.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {
    @PrimaryKey
    @NonNull private String id;
    private String displayName;
    private String lastMessage;
    private String lastDate;
    private int profilePic;

    public Contact(@NonNull String id, String displayName, String lastMessage, String lastDate, int profilePic) {
        this.id = id;
        this.displayName = displayName;
        this.lastMessage = lastMessage;
        this.lastDate = lastDate;
        this.profilePic = profilePic;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getProfilePic() {
        return profilePic;
    }
}